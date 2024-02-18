package org.victorrobotics.bluealliance;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record District(@JsonProperty("abbrevitation") String abbreviation,
                       @JsonProperty("display_name") String displayName,
                       @JsonProperty("key") String key,
                       @JsonProperty("year") int year) {
  public static record Ranking(@JsonProperty("team_key") String teamKey,
                               @JsonProperty("rank") int rank,
                               @JsonProperty("rookie_bonus") int rookieBonus,
                               @JsonProperty("point_total") int pointTotal,
                               @JsonProperty("event_points") List<EventPoints> eventPoints) {
    public static record EventPoints(@JsonProperty("district_cmp") boolean isDistrictChampionship,
                                     @JsonProperty("total") int totalPoints,
                                     @JsonProperty("alliance_points") int alliancePoints,
                                     @JsonProperty("elim_points") int eliminationPoints,
                                     @JsonProperty("award_points") int awardPoints,
                                     @JsonProperty("event_key") String eventKey,
                                     @JsonProperty("qual_points") int qualificationPoints) {}

    public static Endpoint<List<District.Ranking>> endpointForDistrict(String districtKey) {
      return Endpoint.forList("/district/" + districtKey + "/rankings", District.Ranking.class);
    }
  }

  public static Endpoint<List<District>> endpointForYear(int year) {
    return Endpoint.forList("/districts/" + year, District.class);
  }

  public static Endpoint<List<District>> endpointForTeam(String teamKey) {
    return Endpoint.forList("/team/" + teamKey + "/districts", District.class);
  }
}
