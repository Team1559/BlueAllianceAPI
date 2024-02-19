package org.victorrobotics.bluealliance;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiStatus(@JsonProperty("current_season") int currentSeason,
                        @JsonProperty("max_season") int maxSeason,
                        @JsonProperty("is_datafeed_down") boolean isDatafeedDown,
                        @JsonProperty("down_events") List<String> downEvents,
                        @JsonProperty("ios") AppVersion iosVersion,
                        @JsonProperty("android") AppVersion androidVersion) {
  public record AppVersion(@JsonProperty("min_app_version") int minimum,
                           @JsonProperty("latest_app_version") int latest) {}

  public static Endpoint<ApiStatus> endpoint() {
    return Endpoint.forSingle("/status", ApiStatus.class);
  }
}
