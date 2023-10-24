package org.victorrobotics.bluealliance;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

// Timestamps are in unix epoch seconds
public record Match(@JsonProperty("key") String key,
                    @JsonProperty("comp_level") Match.CompetitionLevel level,
                    @JsonProperty("set_number") int setNumber,
                    @JsonProperty("match_number") int matchNumber,
                    @JsonProperty("winning_alliance") Match.AllianceColor winner,
                    @JsonProperty("event_key") String eventKey,
                    @JsonProperty("time") long scheduledTime,
                    @JsonProperty("actual_time") long actualTime,
                    @JsonProperty("predicted_time") long predictedTime,
                    @JsonProperty("post_result_time") long resultPostTime) {
  public enum CompetitionLevel {
    QUALIFICATION("qm"),
    QUARTERFINAL("qf"),
    SEMIFINAL("sf"),
    FINAL("f"),
    EF_UNKNOWN("ef");

    @JsonValue
    private final String id;

    CompetitionLevel(String id) {
      this.id = id;
    }
  }

  public enum AllianceColor {
    RED("red"),
    BLUE("blue");

    @JsonValue
    private final String id;

    AllianceColor(String id) {
      this.id = id;
    }
  }

  public static Endpoint<Match> endpoint(String matchKey) {
    return Endpoint.forSingle("/match/" + matchKey, Match.class);
  }

  public static Endpoint<List<Match>> endpointForEvent(String eventKey) {
    return Endpoint.forList("/event/" + eventKey + "/matches", Match.class);
  }

  public static Endpoint<List<Match>> endpointForTeam(String teamKey, int year) {
    return Endpoint.forList("/team/" + teamKey + "/matches/" + year, Match.class);
  }

  public static Endpoint<List<Match>> endpointForTeamAtEvent(String teamKey, String eventKey) {
    return Endpoint.forList("/team/" + teamKey + "/event/" + eventKey + "/matches", Match.class);
  }
}
