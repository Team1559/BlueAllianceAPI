package org.victorrobotics.bluealliance;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Team(@JsonProperty("key") String id,
                   @JsonProperty("team_number") int number,
                   @JsonProperty("nickname") String name,
                   @JsonProperty("name") String fullName,
                   @JsonProperty("school_name") String school,
                   @JsonProperty("city") String city,
                   @JsonProperty("state_prov") String province,
                   @JsonProperty("country") String country,
                   @JsonProperty("postal_code") String postalCode,
                   @JsonProperty("website") String website,
                   @JsonProperty("rookie_year") int rookieYear) {
  public record Robot(@JsonProperty("year") int year,
                      @JsonProperty("robot_name") String robotName,
                      @JsonProperty("key") String robotKey,
                      @JsonProperty("team_key") String teamKey) {

    public static Endpoint<List<Robot>> endpointForTeam(String teamKey) {
      return Endpoint.forList("/team/" + teamKey + "/robots", Robot.class);
    }
  }

  public static Endpoint<Team> endpoint(String teamKey) {
    return Endpoint.forSingle("/team/" + teamKey, Team.class);
  }

  public static Endpoint<List<Team>> endpointForPage(int page) {
    return Endpoint.forList("/teams/" + page, Team.class);
  }

  public static Endpoint<List<Team>> endpointForYear(int year, int page) {
    return Endpoint.forList("/teams/" + year + "/" + page, Team.class);
  }
}
