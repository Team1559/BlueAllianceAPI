package org.victorrobotics.bluealliance;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.victorrobotics.bluealliance.Match.Alliance.Color;

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

    public static Endpoint<List<String>> endpointForTimeseries(String eventKey) {
      return Endpoint.forList("/event/" + eventKey + "/matches/timeseries", String.class);
    }
  }

  public static final class Simple {
    public final String   key;
    public final Level    level;
    public final int      setNumber;
    public final int      matchNumber;
    public final Alliance redAlliance;
    public final Alliance blueAlliance;
    public final Color    winner;
    public final String   eventKey;
    public final Date     scheduledTime;
    public final Date     predictedTime;
    public final Date     actualTime;

    @JsonCreator
    Simple(@JsonProperty("key") String key, @JsonProperty("comp_level") Level level,
           @JsonProperty("set_number") int setNumber, @JsonProperty("match_number") int matchNumber,
           @JsonProperty("alliances") Map<String, Alliance> alliances,
           @JsonProperty("winning_alliance") Color winner,
           @JsonProperty("event_key") String eventKey, @JsonProperty("time") Long scheduledTime,
           @JsonProperty("predicted_time") Long predictedTime,
           @JsonProperty("actual_time") Long actualTime) {
      this.key = key;
      this.level = level;
      this.setNumber = setNumber;
      this.matchNumber = matchNumber;
      this.redAlliance = alliances.get("red");
      this.blueAlliance = alliances.get("blue");
      this.winner = winner;
      this.eventKey = eventKey;
      this.scheduledTime = scheduledTime == null ? null : new Date(scheduledTime * 1000);
      this.predictedTime = predictedTime == null ? null : new Date(predictedTime * 1000);
      this.actualTime = actualTime == null ? null : new Date(actualTime * 1000);
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Simple [key=")
             .append(key)
             .append(", level=")
             .append(level)
             .append(", setNumber=")
             .append(setNumber)
             .append(", matchNumber=")
             .append(matchNumber)
             .append(", redAlliance=")
             .append(redAlliance)
             .append(", blueAlliance=")
             .append(blueAlliance)
             .append(", winner=")
             .append(winner)
             .append(", eventKey=")
             .append(eventKey)
             .append(", scheduledTime=")
             .append(scheduledTime)
             .append(", predictedTime=")
             .append(predictedTime)
             .append(", actualTime=")
             .append(actualTime)
             .append("]");
      return builder.toString();
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
          && Objects.equals(eventKey, other.eventKey)
          && Objects.equals(scheduledTime, other.scheduledTime)
          && Objects.equals(predictedTime, other.predictedTime)
          && Objects.equals(actualTime, other.actualTime);
    }

    public static Endpoint<Match.Simple> endpointForKey(String matchKey) {
      return Endpoint.forSingle("/match/" + matchKey + "/simple", Match.Simple.class);
    }

    public static Endpoint<List<Match.Simple>> endpointForEvent(String eventKey) {
      return Endpoint.forList("/event/" + eventKey + "/matches/simple", Match.Simple.class);
    }

    public static Endpoint<List<Match.Simple>> endpointForTeam(String teamKey, int year) {
      return Endpoint.forList("/team/" + teamKey + "/matches/" + year + "/simple",
                              Match.Simple.class);
    }

    public static Endpoint<List<Match.Simple>> endpointForTeam(String teamKey, String eventKey) {
      return Endpoint.forList("/team/" + teamKey + "/event/" + eventKey + "/matches/simple",
                              Match.Simple.class);
    }
  }

  public static final class Alliance {
    public enum Color {
      RED("red"),
      BLUE("blue"),

      @JsonEnumDefaultValue
      UNKNOWN("???");

      @JsonValue
      private final String id;

      Color(String id) {
        this.id = id;
      }
    }

    public final int          score;
    public final List<String> teamKeys;
    public final List<String> surrogateTeamKeys;
    public final List<String> disqualifiedTeamKeys;

    @JsonCreator
    Alliance(@JsonProperty("score") int score, @JsonProperty("team_keys") List<String> teamKeys,
             @JsonProperty("surrogate_team_keys") List<String> surrogateTeamKeys,
             @JsonProperty("dq_team_keys") List<String> disqualifiedTeamKeys) {
      this.score = score;
      this.teamKeys = teamKeys == null ? null : List.copyOf(teamKeys);
      this.surrogateTeamKeys = surrogateTeamKeys == null ? null : List.copyOf(surrogateTeamKeys);
      this.disqualifiedTeamKeys =
          disqualifiedTeamKeys == null ? null : List.copyOf(disqualifiedTeamKeys);
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Alliance [score=")
             .append(score)
             .append(", teamKeys=")
             .append(teamKeys)
             .append(", surrogateTeamKeys=")
             .append(surrogateTeamKeys)
             .append(", disqualifiedTeamKeys=")
             .append(disqualifiedTeamKeys)
             .append("]");
      return builder.toString();
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
      BLUE_ALLIANCE("tba"),

      @JsonEnumDefaultValue
      UNKNOWN("???");

      @JsonValue
      private final String id;

      Type(String id) {
        this.id = id;
      }
    }

    public final Type   type;
    public final String key;

    @JsonCreator
    Video(@JsonProperty("type") Type type, @JsonProperty("key") String key) {
      this.type = type;
      this.key = key;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Video [type=")
             .append(type)
             .append(", key=")
             .append(key)
             .append("]");
      return builder.toString();
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

  public interface ScoreBreakdown {
    @SuppressWarnings("java:S1301") // replace switch with if
    static ScoreBreakdown of(String matchKey, JsonNode json) {
      try {
        int year = Integer.parseInt(matchKey.substring(0, 4));
        switch (year) {
          case 2023:
            return Endpoint.JSON_OBJECT_MAPPER.treeToValue(json,
                                                           ScoreBreakdowns.ChargedUp2023.class);
          default:
            return null;
        }
      } catch (NumberFormatException | IndexOutOfBoundsException | JsonProcessingException e) {
        return null;
      }
    }
  }

  public enum Level {
    QUALIFICATION("qm"),
    QUARTERFINAL("qf"),
    SEMIFINAL("sf"),
    FINAL("f"),
    EF_UNKNOWN("ef"),

    @JsonEnumDefaultValue
    UNKNOWN("???");

    @JsonValue
    private final String id;

    Level(String id) {
      this.id = id;
    }
  }

  public final String         key;
  public final Level          level;
  public final int            setNumber;
  public final int            matchNumber;
  public final Alliance       redAlliance;
  public final Alliance       blueAlliance;
  public final Color          winningAlliance;
  public final String         eventKey;
  public final Date           scheduledTime;
  public final Date           actualTime;
  public final Date           predictedTime;
  public final Date           resultPostTime;
  public final List<Video>    videos;
  public final ScoreBreakdown redScore;
  public final ScoreBreakdown blueScore;

  @JsonCreator
  Match(@JsonProperty("key") String key, @JsonProperty("comp_level") Level level,
        @JsonProperty("set_number") int setNumber, @JsonProperty("match_number") int matchNumber,
        @JsonProperty("alliances") Map<String, Alliance> alliances,
        @JsonProperty("winning_alliance") Color winningAlliance,
        @JsonProperty("event_key") String eventKey, @JsonProperty("time") Long scheduledTime,
        @JsonProperty("actual_time") Long actualTime,
        @JsonProperty("predicted_time") Long predictedTime,
        @JsonProperty("post_result_time") Long resultPostTime,
        @JsonProperty("score_breakdown") Map<String, JsonNode> scoreBreakdown,
        @JsonProperty("videos") List<Video> videos) {
    this.key = key;
    this.level = level;
    this.setNumber = setNumber;
    this.matchNumber = matchNumber;
    this.redAlliance = alliances.get("red");
    this.blueAlliance = alliances.get("blue");
    this.winningAlliance = winningAlliance;
    this.eventKey = eventKey;
    this.scheduledTime = scheduledTime == null ? null : new Date(scheduledTime * 1000);
    this.actualTime = actualTime == null ? null : new Date(actualTime * 1000);
    this.predictedTime = predictedTime == null ? null : new Date(predictedTime * 1000);
    this.resultPostTime = resultPostTime == null ? null : new Date(resultPostTime * 1000);
    this.videos = videos == null ? null : List.copyOf(videos);

    if (scoreBreakdown != null && scoreBreakdown.containsKey("red")
        && scoreBreakdown.containsKey("blue")) {
      redScore = ScoreBreakdown.of(key, scoreBreakdown.get("red"));
      blueScore = ScoreBreakdown.of(key, scoreBreakdown.get("blue"));
    } else {
      redScore = null;
      blueScore = null;
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Match [key=")
           .append(key)
           .append(", level=")
           .append(level)
           .append(", setNumber=")
           .append(setNumber)
           .append(", matchNumber=")
           .append(matchNumber)
           .append(", redAlliance=")
           .append(redAlliance)
           .append(", blueAlliance=")
           .append(blueAlliance)
           .append(", winningAlliance=")
           .append(winningAlliance)
           .append(", eventKey=")
           .append(eventKey)
           .append(", scheduledTime=")
           .append(scheduledTime)
           .append(", actualTime=")
           .append(actualTime)
           .append(", predictedTime=")
           .append(predictedTime)
           .append(", resultPostTime=")
           .append(resultPostTime)
           .append(", videos=")
           .append(videos)
           .append(", redScore=")
           .append(redScore)
           .append(", blueScore=")
           .append(blueScore)
           .append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, level, setNumber, matchNumber, redAlliance, blueAlliance,
                        winningAlliance, eventKey, scheduledTime, actualTime, predictedTime,
                        resultPostTime, videos, redScore, blueScore);
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
        && Objects.equals(scheduledTime, other.scheduledTime)
        && Objects.equals(actualTime, other.actualTime)
        && Objects.equals(predictedTime, other.predictedTime)
        && Objects.equals(resultPostTime, other.resultPostTime)
        && Objects.equals(videos, other.videos) && Objects.equals(redScore, other.redScore)
        && Objects.equals(blueScore, other.blueScore);
  }

  public static Endpoint<Match> endpointForKey(String matchKey) {
    return Endpoint.forSingle("/match/" + matchKey, Match.class);
  }

  public static Endpoint<List<Match>> endpointForEvent(String eventKey) {
    return Endpoint.forList("/event/" + eventKey + "/matches", Match.class);
  }

  public static Endpoint<List<Match>> endpointForTeam(String teamKey, int year) {
    return Endpoint.forList("/team/" + teamKey + "/matches/" + year, Match.class);
  }

  public static Endpoint<List<Match>> endpointForTeam(String teamKey, String eventKey) {
    return Endpoint.forList("/team/" + teamKey + "/event/" + eventKey + "/matches", Match.class);
  }
}
