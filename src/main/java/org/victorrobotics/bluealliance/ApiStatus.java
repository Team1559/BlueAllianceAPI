package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ApiStatus {
  public static final class AppVersion {
    public final int minimum;
    public final int latest;

    @JsonCreator
    AppVersion(@JsonProperty("min_app_version") int minimum,
               @JsonProperty("latest_app_version") int latest) {
      this.minimum = minimum;
      this.latest = latest;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("AppVersion [minimum=")
             .append(minimum)
             .append(", latest=")
             .append(latest)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(minimum, latest);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof AppVersion)) return false;
      AppVersion other = (AppVersion) obj;
      return minimum == other.minimum && latest == other.latest;
    }
  }

  public final int          currentSeason;
  public final int          maxSeason;
  public final boolean      isDatafeedDown;
  public final List<String> downEvents;
  public final AppVersion   iosVersion;
  public final AppVersion   androidVersion;

  @JsonCreator
  ApiStatus(@JsonProperty("current_season") int currentSeason,
            @JsonProperty("max_season") int maxSeason,
            @JsonProperty("is_datafeed_down") boolean isDatafeedDown,
            @JsonProperty("down_events") List<String> downEvents,
            @JsonProperty("ios") AppVersion iosVersion,
            @JsonProperty("android") AppVersion androidVersion) {
    this.currentSeason = currentSeason;
    this.maxSeason = maxSeason;
    this.isDatafeedDown = isDatafeedDown;
    this.downEvents = downEvents == null ? null : List.copyOf(downEvents);
    this.iosVersion = iosVersion;
    this.androidVersion = androidVersion;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("APIStatus [currentSeason=")
           .append(currentSeason)
           .append(", maxSeason=")
           .append(maxSeason)
           .append(", isDatafeedDown=")
           .append(isDatafeedDown)
           .append(", downEvents=")
           .append(downEvents)
           .append(", iosVersion=")
           .append(iosVersion)
           .append(", androidVersion=")
           .append(androidVersion)
           .append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentSeason, maxSeason, isDatafeedDown, downEvents, iosVersion,
                        androidVersion);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof ApiStatus)) return false;
    ApiStatus other = (ApiStatus) obj;
    return currentSeason == other.currentSeason && maxSeason == other.maxSeason
        && isDatafeedDown == other.isDatafeedDown && Objects.equals(downEvents, other.downEvents)
        && Objects.equals(iosVersion, other.iosVersion)
        && Objects.equals(androidVersion, other.androidVersion);
  }

  public static Endpoint<ApiStatus> endpoint() {
    return Endpoint.forSingle("/status", ApiStatus.class);
  }
}
