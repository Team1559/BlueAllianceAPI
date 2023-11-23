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

  private static final HttpClient HTTP_CLIENT        = HttpClient.newHttpClient();
  static final ObjectMapper       JSON_OBJECT_MAPPER =
      new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
                        .disable(DeserializationFeature.WRAP_EXCEPTIONS);

  private final HttpRequest.Builder requestBuilder;
  private final ObjectReader        jsonReader;

  private T value;

  private Endpoint(String endpoint, ObjectReader reader) {
    this.jsonReader = reader;
    requestBuilder = HttpRequest.newBuilder()
                                .uri(URI.create(TBA_API_URL + endpoint))
                                .GET()
                                .setHeader("X-TBA-Auth-Key", TBA_API_KEY);
  }

  public T get() {
    return value;
  }

  public CompletableFuture<T> startRefresh() {
    return HTTP_CLIENT.sendAsync(requestBuilder.build(), BodyHandlers.ofInputStream())
                      .thenApply(this::handleResponse);
  }

  public Endpoint<T> refresh() {
    startRefresh().join();
    return this;
  }

  private T handleResponse(HttpResponse<InputStream> response) {
    int statusCode = response.statusCode();
    if (statusCode != 200 && statusCode != 304) return value;

    HttpHeaders headers = response.headers();
    headers.firstValue("etag")
           .ifPresent(eTag -> requestBuilder.setHeader("If-None-Match", eTag));

    if (statusCode != 200) return value;

    try (InputStream body = response.body()) {
      T result = jsonReader.readValue(body);
      if (result != null && !result.equals(value)) {
        value = result;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return value;
  }

  @SuppressWarnings("unchecked")
  static <T> Endpoint<T> forSingle(String endpoint, Class<T> clazz) {
    WeakReference<Endpoint<?>> value = ENDPOINTS.get(endpoint);
    if (value == null || value.get() == null) {
      value = new WeakReference<>(new Endpoint<>(endpoint, JSON_OBJECT_MAPPER.readerFor(clazz)));
      ENDPOINTS.put(endpoint, value);
    }
    return (Endpoint<T>) value.get();
  }

  @SuppressWarnings("unchecked")
  static <T> Endpoint<List<T>> forList(String endpoint, Class<T> clazz) {
    WeakReference<Endpoint<?>> value = ENDPOINTS.get(endpoint);
    if (value == null || value.get() == null) {
      value =
          new WeakReference<>(new Endpoint<>(endpoint, JSON_OBJECT_MAPPER.readerForListOf(clazz)));
      ENDPOINTS.put(endpoint, value);
    }
    return (Endpoint<List<T>>) value.get();
  }

  @SuppressWarnings("unchecked")
  static <T> Endpoint<Map<String, T>> forMap(String endpoint, Class<T> clazz) {
    WeakReference<Endpoint<?>> value = ENDPOINTS.get(endpoint);
    if (value == null || value.get() == null) {
      value =
          new WeakReference<>(new Endpoint<>(endpoint, JSON_OBJECT_MAPPER.readerForMapOf(clazz)));
      ENDPOINTS.put(endpoint, value);
    }
    return (Endpoint<Map<String, T>>) value.get();
  }
}
