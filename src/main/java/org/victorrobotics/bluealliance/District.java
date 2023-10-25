package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class District {
  @JsonProperty("abbrevitation")
  private String abbrevitation;

  @JsonProperty("display_name")
  private String displayName;

  @JsonProperty("key")
  private String key;

  @JsonProperty("year")
  private int year;

  public District() {}

  public String getAbbrevitation() {
    return abbrevitation;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getKey() {
    return key;
  }

  public int getYear() {
    return year;
  }

  @Override
  public int hashCode() {
    return Objects.hash(abbrevitation, displayName, key, year);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof District)) return false;
    District other = (District) obj;
    return Objects.equals(abbrevitation, other.abbrevitation)
        && Objects.equals(displayName, other.displayName) && Objects.equals(key, other.key)
        && year == other.year;
  }

  public static Endpoint<List<District>> endpointForYear(int year) {
    return Endpoint.forList("/districts/" + year, District.class);
  }

  public static Endpoint<List<District>> endpointForTeam(String teamKey) {
    return Endpoint.forList("/team/" + teamKey + "/districts", District.class);
  }
}
