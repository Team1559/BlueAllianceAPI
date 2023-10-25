package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class APIStatus {
  public static class AppVersion {
    @JsonProperty("min_app_version")
    private int minimum;

    @JsonProperty("latest_app_version")
    private int latest;

    public AppVersion() {}

    public int getMinimum() {
      return minimum;
    }

    public int getLatest() {
      return latest;
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

  @JsonProperty("current_season")
  private int currentSeason;

  @JsonProperty("max_season")
  private int maxSeason;

  @JsonProperty("is_datafeed_down")
  private boolean isDatafeedDown;

  @JsonProperty("down_events")
  private List<String> downEvents;

  @JsonProperty("ios")
  private AppVersion iosVersion;

  @JsonProperty("android")
  private AppVersion androidVersion;

  public APIStatus() {}

  public int getCurrentSeason() {
    return currentSeason;
  }

  public int getMaxSeason() {
    return maxSeason;
  }

  @JsonIgnore // grabbed by default during serialization
  public boolean isDatafeedDown() {
    return isDatafeedDown;
  }

  public List<String> getDownEvents() {
    return downEvents;
  }

  public AppVersion getIosVersion() {
    return iosVersion;
  }

  public AppVersion getAndroidVersion() {
    return androidVersion;
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
    if (!(obj instanceof APIStatus)) return false;
    APIStatus other = (APIStatus) obj;
    return currentSeason == other.currentSeason && maxSeason == other.maxSeason
        && isDatafeedDown == other.isDatafeedDown && Objects.equals(downEvents, other.downEvents)
        && Objects.equals(iosVersion, other.iosVersion)
        && Objects.equals(androidVersion, other.androidVersion);
  }

  public static Endpoint<APIStatus> endpoint() {
    return Endpoint.forSingle("/status", APIStatus.class);
  }
}
