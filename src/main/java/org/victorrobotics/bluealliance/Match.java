package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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
    public final long     scheduledTime;
    public final long     predictedTime;
    public final long     actualTime;

    @JsonCreator
    Simple(@JsonProperty("key") String key, @JsonProperty("comp_level") Level level,
                   @JsonProperty("set_number") int setNumber,
                   @JsonProperty("match_number") int matchNumber,
                   @JsonProperty("alliances") Map<String, Alliance> alliances,
                   @JsonProperty("winning_alliance") Color winner,
                   @JsonProperty("event_key") String eventKey,
                   @JsonProperty("time") long scheduledTime,
                   @JsonProperty("predicted_time") long predictedTime,
                   @JsonProperty("actual_time") long actualTime) {
      this.key = key;
      this.level = level;
      this.setNumber = setNumber;
      this.matchNumber = matchNumber;
      this.redAlliance = alliances.get("red");
      this.blueAlliance = alliances.get("blue");
      this.winner = winner;
      this.eventKey = eventKey;
      this.scheduledTime = scheduledTime;
      this.predictedTime = predictedTime;
      this.actualTime = actualTime;
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
    Alliance(@JsonProperty("score") int score,
                     @JsonProperty("team_keys") List<String> teamKeys,
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

  public final String      key;
  public final Level       level;
  public final int         setNumber;
  public final int         matchNumber;
  public final Alliance    redAlliance;
  public final Alliance    blueAlliance;
  public final Color       winningAlliance;
  public final String      eventKey;
  public final long        scheduledTime;
  public final long        actualTime;
  public final long        predictedTime;
  public final long        resultPostTime;
  public final List<Video> videos;
  // TODO: score breakdowns

  @JsonCreator
  Match(@JsonProperty("key") String key, @JsonProperty("comp_level") Level level,
                @JsonProperty("set_number") int setNumber,
                @JsonProperty("match_number") int matchNumber,
                @JsonProperty("alliances") Map<String, Alliance> alliances,
                @JsonProperty("winning_alliance") Color winningAlliance,
                @JsonProperty("event_key") String eventKey,
                @JsonProperty("time") long scheduledTime,
                @JsonProperty("actual_time") long actualTime,
                @JsonProperty("predicted_time") long predictedTime,
                @JsonProperty("post_result_time") long resultPostTime,
                @JsonProperty("videos") List<Video> videos) {
    this.key = key;
    this.level = level;
    this.setNumber = setNumber;
    this.matchNumber = matchNumber;
    this.redAlliance = alliances.get("red");
    this.blueAlliance = alliances.get("blue");
    this.winningAlliance = winningAlliance;
    this.eventKey = eventKey;
    this.scheduledTime = scheduledTime;
    this.actualTime = actualTime;
    this.predictedTime = predictedTime;
    this.resultPostTime = resultPostTime;
    this.videos = videos == null ? null : List.copyOf(videos);
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
           .append("]");
    return builder.toString();
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
}
