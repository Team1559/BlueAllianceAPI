package org.victorrobotics.tba;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TBA_Endpoint<T> implements Supplier<T> {
  protected static final String TBA_API_URL = "https://www.thebluealliance.com/api/v3";

  private static final String       TBA_API_KEY        = System.getenv("TBA_API_KEY");
  private static final ObjectMapper JSON_OBJECT_MAPPER =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  private static final HttpClient   HTTP_CLIENT        = HttpClient.newHttpClient();

  private final HttpRequest.Builder requestBuilder;
  private final Class<T>            type;

  private T      value;
  private String eTag;
  private long   minRefreshTime;

  public TBA_Endpoint(URI uri, Class<T> type) {
    this.type = type;
    requestBuilder = HttpRequest.newBuilder()
                                .uri(uri)
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
      result = JSON_OBJECT_MAPPER.readValue(body, type);
    } catch (IOException e) {
      return false;
    }

    if (result != null && !result.equals(value)) {
      value = result;
      return true;
    }
    return false;
  }
}
