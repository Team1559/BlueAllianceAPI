package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

// Timestamps are in unix epoch seconds
public final class Match {
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

  @JsonProperty("key")
  private String key;

  @JsonProperty("comp_level")
  private Match.CompetitionLevel level;

  @JsonProperty("set_number")
  private int setNumber;

  @JsonProperty("match_number")
  private int matchNumber;

  @JsonProperty("winning_alliance")
  private Match.AllianceColor winner;

  @JsonProperty("event_key")
  private String eventKey;

  @JsonProperty("time")
  private long scheduledTime;

  @JsonProperty("actual_time")
  private long actualTime;

  @JsonProperty("predicted_time")
  private long predictedTime;

  @JsonProperty("post_result_time")
  private long resultPostTime;

  public Match() {}

  public String getKey() {
    return key;
  }

  public Match.CompetitionLevel getLevel() {
    return level;
  }

  public int getSetNumber() {
    return setNumber;
  }

  public int getMatchNumber() {
    return matchNumber;
  }

  public Match.AllianceColor getWinner() {
    return winner;
  }

  public String getEventKey() {
    return eventKey;
  }

  public long getScheduledTime() {
    return scheduledTime;
  }

  public long getActualTime() {
    return actualTime;
  }

  public long getPredictedTime() {
    return predictedTime;
  }

  public long getResultPostTime() {
    return resultPostTime;
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, level, setNumber, matchNumber, winner, eventKey, scheduledTime,
                        actualTime, predictedTime, resultPostTime);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Match)) return false;
    Match other = (Match) obj;
    return Objects.equals(key, other.key) && level == other.level && setNumber == other.setNumber
        && matchNumber == other.matchNumber && winner == other.winner
        && Objects.equals(eventKey, other.eventKey) && scheduledTime == other.scheduledTime
        && actualTime == other.actualTime && predictedTime == other.predictedTime
        && resultPostTime == other.resultPostTime;
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
