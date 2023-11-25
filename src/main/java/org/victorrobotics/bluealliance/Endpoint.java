package org.victorrobotics.bluealliance;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

  private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

  static final ObjectMapper JSON_OBJECT_MAPPER =
      new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
                        .disable(DeserializationFeature.WRAP_EXCEPTIONS);

  private static ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

  private final Request.Builder requestBuilder;
  private final ObjectReader    jsonReader;

  @SuppressWarnings("java:S3077")
  private volatile T value;

  private Endpoint(String endpoint, ObjectReader reader) {
    jsonReader = reader;
    requestBuilder = new Request.Builder().url(TBA_API_URL + endpoint)
                                          .get()
                                          .header("X-TBA-Auth-Key", TBA_API_KEY)
                                          .cacheControl(CacheControl.FORCE_NETWORK);
  }

  public T get() {
    return value;
  }

  public T refresh() {
    try {
      Call call = HTTP_CLIENT.newCall(requestBuilder.build());
      Response response = call.execute();
      update(response);
    } catch (IOException e) {}

    return get();
  }

  public CompletableFuture<T> refreshAsync() {
    return CompletableFuture.supplyAsync(requestBuilder::build, EXECUTOR)
                            .thenApply(HTTP_CLIENT::newCall)
                            .thenApply(call -> {
                              try {
                                return call.execute();
                              } catch (IOException e) {
                                throw new CompletionException(e);
                              }
                            })
                            .thenAccept(this::update)
                            .thenApply(x -> get());
  }

  private void update(Response response) {
    int statusCode = response.code();
    if (statusCode != 200 && statusCode != 304) return;

    String etag = response.headers()
                          .get("etag");
    if (etag != null) {
      requestBuilder.header("If-None-Match", etag);
    }

    if (statusCode != 200) return;

    try (InputStream body = response.body()
                                    .byteStream()) {
      T result = jsonReader.readValue(body);
      if (result != null && !result.equals(value)) {
        value = result;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
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

  public static void setExecutor(ExecutorService executor) {
    Objects.requireNonNull(executor);
    EXECUTOR.shutdown();
    EXECUTOR = executor;
  }

  public static void shutdown() {
    EXECUTOR.shutdown();
  }
}
