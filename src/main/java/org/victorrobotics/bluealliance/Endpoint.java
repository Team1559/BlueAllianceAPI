package org.victorrobotics.bluealliance;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public final class Endpoint<T> implements Supplier<T> {
  public enum Type {
    SINGLE,
    ARRAY,
    LIST,
    MAP;

    public ObjectReader readerFor(Class<?> type) {
      switch (this) {
        case SINGLE:
          return JSON_OBJECT_MAPPER.readerFor(type);
        case ARRAY:
          return JSON_OBJECT_MAPPER.readerForArrayOf(type);
        case LIST:
          return JSON_OBJECT_MAPPER.readerForListOf(type);
        case MAP:
          return JSON_OBJECT_MAPPER.readerForMapOf(type);
        default:
          return null;
      }
    }
  }

  private static final Map<String, WeakReference<Endpoint<?>>> ENDPOINTS = new HashMap<>();

  private static final String TBA_API_URL = "https://www.thebluealliance.com/api/v3";
  private static final String TBA_API_KEY = System.getenv("TBA_API_KEY");

  private static final HttpClient   HTTP_CLIENT        = HttpClient.newHttpClient();
  private static final ObjectMapper JSON_OBJECT_MAPPER =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  private final HttpRequest.Builder requestBuilder;
  private final ObjectReader        jsonReader;

  private T      value;
  private String eTag;
  private long   minRefreshTime;

  private Endpoint(String endpoint, Class<?> clazz, Type type) {
    this(endpoint, type.readerFor(clazz));
  }

  private Endpoint(String endpoint, ObjectReader reader) {
    this.jsonReader = reader;
    requestBuilder = HttpRequest.newBuilder()
                                .uri(URI.create(TBA_API_URL + endpoint))
                                .GET()
                                .setHeader("X-TBA-Auth-Key", TBA_API_KEY)
                                .timeout(Duration.of(10, ChronoUnit.SECONDS));
    minRefreshTime = System.currentTimeMillis();
  }

  public T get() {
    return value;
  }

  public CompletableFuture<Boolean> refresh() {
    if (eTag != null) {
      requestBuilder.setHeader("If-None-Match", eTag);
    }

    if (System.currentTimeMillis() < minRefreshTime) {
      return CompletableFuture.completedFuture(false);
    }

    return HTTP_CLIENT.sendAsync(requestBuilder.build(), BodyHandlers.ofInputStream())
                      .thenApply(this::handleResponse);
  }

  private boolean handleResponse(HttpResponse<InputStream> response) {
    int statusCode = response.statusCode();
    if (statusCode != 200 && statusCode != 304) return false;

    HttpHeaders headers = response.headers();
    headers.firstValue("etag")
           .ifPresent(str -> eTag = str);
    headers.firstValue("cache-control")
           .ifPresent(str -> {
             int beginIndex = str.indexOf("max-age=");
             if (beginIndex == -1) return;
             beginIndex += 8;
             int endIndex = beginIndex;
             int len = str.length();
             char c;
             while (endIndex < len && (c = str.charAt(endIndex)) >= '0' && c <= '9') {
               endIndex++;
             }
             int maxAge = Integer.parseInt(str.substring(beginIndex, endIndex));
             minRefreshTime = System.currentTimeMillis() + maxAge * 1000;
           });

    if (statusCode != 200) return false;

    T result;
    try (InputStream body = response.body()) {
      result = jsonReader.readValue(body);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    if (result != null && !result.equals(value)) {
      value = result;
      return true;
    }
    return false;
  }

  @SuppressWarnings("unchecked")
  static <T> Endpoint<T> forSingle(String endpoint, Class<T> clazz) {
    WeakReference<Endpoint<?>> value = ENDPOINTS.get(endpoint);
    if (value == null || value.refersTo(null)) {
      value = new WeakReference<>(new Endpoint<>(endpoint, JSON_OBJECT_MAPPER.readerFor(clazz)));
      ENDPOINTS.put(endpoint, value);
    }
    return (Endpoint<T>) value.get();
  }

  @SuppressWarnings("unchecked")
  static <T> Endpoint<List<T>> forList(String endpoint, Class<T> clazz) {
    WeakReference<Endpoint<?>> value = ENDPOINTS.get(endpoint);
    if (value == null || value.refersTo(null)) {
      value =
          new WeakReference<>(new Endpoint<>(endpoint, JSON_OBJECT_MAPPER.readerForListOf(clazz)));
      ENDPOINTS.put(endpoint, value);
    }
    return (Endpoint<List<T>>) value.get();
  }
}
