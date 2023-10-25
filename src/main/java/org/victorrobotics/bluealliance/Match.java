package org.victorrobotics.bluealliance;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class Match {
  public static final class Keys {
    private Keys() {}

    public static Endpoint<List<String>> endpointForEvent(String eventKey) {
      return Endpoint.forList("/event/" + eventKey + "/matches/keys", String.class);
    }

    public static Endpoint<List<String>> endpointForTeam(String teamKey, String eventKey) {
      return Endpoint.forList("/team/" + teamKey + "/event/" + eventKey + "/matches/keys",
                              String.class);
    }
  }

  public static final class Simple {
    @JsonProperty("key")
    private String key;

    @JsonProperty("comp_level")
    private Match.CompetitionLevel level;

    @JsonProperty("set_number")
    private int setNumber;

    @JsonProperty("match_number")
    private int matchNumber;

    // custom extraction
    private Match.Alliance redAlliance;
    private Match.Alliance blueAlliance;

    @JsonProperty("winning_alliance")
    private Match.AllianceColor winner;

    @JsonProperty("event_key")
    private String eventKey;

    @JsonProperty("time")
    private long scheduledTime;

    @JsonProperty("predicted_time")
    private long predictedTime;

    @JsonProperty("actual_time")
    private long actualTime;

    public Simple() {}

    @JsonGetter("alliances")
    private Map<String, Match.Alliance> serializeAlliances() {
      return Map.of("red", redAlliance, "blue", blueAlliance);
    }

    @JsonSetter("alliances")
    private void setAlliances(Map<String, Match.Alliance> alliances) {
      redAlliance = alliances.get("red");
      blueAlliance = alliances.get("blue");
    }

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

    @JsonIgnore
    public Match.Alliance getRedAlliance() {
      return redAlliance;
    }

    @JsonIgnore
    public Match.Alliance getBlueAlliance() {
      return blueAlliance;
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

    public long getPredictedTime() {
      return predictedTime;
    }

    public long getActualTime() {
      return actualTime;
    }

    @Override
    public int hashCode() {
      return Objects.hash(key, level, setNumber, matchNumber, redAlliance, blueAlliance, winner,
                          eventKey, scheduledTime, predictedTime, actualTime);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Simple)) return false;
      Simple other = (Simple) obj;
      return Objects.equals(key, other.key) && level == other.level && setNumber == other.setNumber
          && matchNumber == other.matchNumber && Objects.equals(redAlliance, other.redAlliance)
          && Objects.equals(blueAlliance, other.blueAlliance) && winner == other.winner
          && Objects.equals(eventKey, other.eventKey) && scheduledTime == other.scheduledTime
          && predictedTime == other.predictedTime && actualTime == other.actualTime;
    }

    public static Endpoint<Match.Simple> endpoint(String matchKey) {
      return Endpoint.forSingle("/match/" + matchKey + "/simple", Match.Simple.class);
    }

    public static Endpoint<List<Match.Simple>> endpointForEvent(String eventKey) {
      return Endpoint.forList("/event/" + eventKey + "/matches/simple", Match.Simple.class);
    }

    public static Endpoint<List<Match.Simple>> endpointForTeam(String teamKey, int year) {
      return Endpoint.forList("/team/" + teamKey + "/matches/" + year + "/simple",
                              Match.Simple.class);
    }

    public static Endpoint<List<Match.Simple>> endpointForTeamAtEvent(String teamKey,
                                                                      String eventKey) {
      return Endpoint.forList("/team/" + teamKey + "/event/" + eventKey + "/matches/simple",
                              Match.Simple.class);
    }
  }

  public static class Alliance {
    @JsonProperty("score")
    private int score;

    @JsonProperty("team_keys")
    private List<String> teamKeys;

    @JsonProperty("surrogate_team_keys")
    private List<String> surrogateTeamKeys;

    @JsonProperty("dq_team_keys")
    private List<String> disqualifiedTeamKeys;

    public Alliance() {}

    public int getScore() {
      return score;
    }

    public List<String> getTeamKeys() {
      return teamKeys;
    }

    public List<String> getSurrogateTeamKeys() {
      return surrogateTeamKeys;
    }

    public List<String> getDisqualifiedTeamKeys() {
      return disqualifiedTeamKeys;
    }

    @Override
    public int hashCode() {
      return Objects.hash(score, teamKeys, surrogateTeamKeys, disqualifiedTeamKeys);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Alliance)) return false;
      Alliance other = (Alliance) obj;
      return score == other.score && Objects.equals(teamKeys, other.teamKeys)
          && Objects.equals(surrogateTeamKeys, other.surrogateTeamKeys)
          && Objects.equals(disqualifiedTeamKeys, other.disqualifiedTeamKeys);
    }
  }

  public static final class Video {
    public enum Type {
      YOUTUBE("youtube"),
      BLUE_ALLIANCE("tba");

      @JsonValue
      private final String id;

      Type(String id) {
        this.id = id;
      }
    }

    @JsonProperty("type")
    private Match.Video.Type type;

    @JsonProperty("key")
    private String key;

    public Video() {}

    public Match.Video.Type getType() {
      return type;
    }

    public String getKey() {
      return key;
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, key);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Video)) return false;
      Video other = (Video) obj;
      return type == other.type && Objects.equals(key, other.key);
    }
  }

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

  // custom extraction
  private Match.Alliance redAlliance;
  private Match.Alliance blueAlliance;

  @JsonProperty("winning_alliance")
  private Match.AllianceColor winningAlliance;

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

  // TODO: score breakdowns

  @JsonProperty("videos")
  private List<Video> videos;

  public Match() {}

  @JsonGetter("alliances")
  private Map<String, Match.Alliance> serializeAlliances() {
    return Map.of("red", redAlliance, "blue", blueAlliance);
  }

  @JsonSetter("alliances")
  private void setAlliances(Map<String, Match.Alliance> alliances) {
    redAlliance = alliances.get("red");
    blueAlliance = alliances.get("blue");
  }

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

  @JsonIgnore
  public Match.Alliance getRedAlliance() {
    return redAlliance;
  }

  @JsonIgnore
  public Match.Alliance getBlueAlliance() {
    return blueAlliance;
  }

  public Match.AllianceColor getWinningAlliance() {
    return winningAlliance;
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

  public List<Video> getVideos() {
    return Collections.unmodifiableList(videos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, level, setNumber, matchNumber, redAlliance, blueAlliance,
                        winningAlliance, eventKey, scheduledTime, actualTime, predictedTime,
                        resultPostTime, videos);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Match)) return false;
    Match other = (Match) obj;
    return Objects.equals(key, other.key) && level == other.level && setNumber == other.setNumber
        && matchNumber == other.matchNumber && Objects.equals(redAlliance, other.redAlliance)
        && Objects.equals(blueAlliance, other.blueAlliance)
        && winningAlliance == other.winningAlliance && Objects.equals(eventKey, other.eventKey)
        && scheduledTime == other.scheduledTime && actualTime == other.actualTime
        && predictedTime == other.predictedTime && resultPostTime == other.resultPostTime
        && Objects.equals(videos, other.videos);
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

  public static void main(String... args) throws JsonProcessingException {
    System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter()
                                         .writeValueAsString(Match.Simple.endpointForTeamAtEvent("frc1559",
                                                                                                 "2023nyrr")
                                                                         .request()
                                                                         .join()));
  }

}
