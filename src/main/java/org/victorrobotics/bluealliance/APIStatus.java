package org.victorrobotics.bluealliance;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public record APIStatus(@JsonProperty("current_season") int currentSeason,
                        @JsonProperty("max_season") int maxSeason,
                        @JsonProperty("is_datafeed_down") boolean isDatafeedDown,
                        @JsonProperty("down_events") Set<String> downEvents,
                        @JsonProperty("ios") AppVersion iosVersion,
                        @JsonProperty("android") AppVersion androidVersion) {
  public record AppVersion(@JsonProperty("min_app_version") int minimum,
                                  @JsonProperty("latest_app_version") int latest) {}

  public static Endpoint<APIStatus> endpoint() {
    return Endpoint.forSingle("/status", APIStatus.class);
  }
}
