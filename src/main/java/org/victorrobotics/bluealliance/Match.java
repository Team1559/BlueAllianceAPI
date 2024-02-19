package org.victorrobotics.bluealliance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;

import org.victorrobotics.bluealliance.Match.Alliance.Color;

public record Match(String key,
                    Level level,
                    int setNumber,
                    int matchNumber,
                    Alliance redAlliance,
                    Alliance blueAlliance,
                    Color winningAlliance,
                    String eventKey,
                    Date scheduledTime,
                    Date actualTime,
                    Date predictedTime,
                    Date resultPostTime,
                    List<Video> videos,
                    ScoreBreakdown redBreakdown,
                    ScoreBreakdown blueBreakdown) {
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

  public record Simple(String key,
                       Level level,
                       int setNumber,
                       int matchNumber,
                       Alliance redAlliance,
                       Alliance blueAlliance,
                       Color winner,
                       String eventKey,
                       Date scheduledTime,
                       Date predictedTime,
                       Date actualTime) {
    @JsonCreator
    public Simple(@JsonProperty("key") String key, @JsonProperty("comp_level") Level level,
                  @JsonProperty("set_number") int setNumber,
                  @JsonProperty("match_number") int matchNumber,
                  @JsonProperty("alliances") Map<String, Alliance> alliances,
                  @JsonProperty("winning_alliance") Color winner,
                  @JsonProperty("event_key") String eventKey,
                  @JsonProperty("time") Long scheduledTime,
                  @JsonProperty("predicted_time") Long predictedTime,
                  @JsonProperty("actual_time") Long actualTime) {
      this(key, level, setNumber, matchNumber, alliances.get("red"), alliances.get("blue"), winner,
           eventKey, dateFromSeconds(scheduledTime), dateFromSeconds(predictedTime),
           dateFromSeconds(actualTime));
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

  public record Alliance(@JsonProperty("score") int score,
                         @JsonProperty("team_keys") List<String> teamKeys,
                         @JsonProperty("surrogate_team_keys") List<String> surrogateTeamKeys,
                         @JsonProperty("dq_team_keys") List<String> disqualifiedTeamKeys) {
    public enum Color {
      RED("red"),
      BLUE("blue"),

      @JsonEnumDefaultValue
      NONE("");

      @JsonValue
      private final String id;

      Color(String id) {
        this.id = id;
      }
    }
  }

  public record Video(@JsonProperty("type") Type type,
                      @JsonProperty("key") String key) {
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
  }

  public record Zebra(String key,
                      List<Double> times,
                      List<Zebra.Team> redAlliance,
                      List<Zebra.Team> blueAlliance) {
    public record Team(@JsonProperty("team_key") String key,
                       @JsonProperty("xs") List<Double> xPositions,
                       @JsonProperty("ys") List<Double> yPositions) {}

    @JsonCreator
    public Zebra(@JsonProperty("key") String key, @JsonProperty("times") List<Double> times,
                 @JsonProperty("alliances") Map<String, List<Team>> alliances) {
      this(key, times, alliances.get("red"), alliances.get("blue"));
    }

    public static Endpoint<Zebra> endpointForMatch(String matchKey) {
      return Endpoint.forSingle("/match/" + matchKey + "/zebra_motionworks", Match.Zebra.class);
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

  @JsonCreator
  public Match(@JsonProperty("key") String key, @JsonProperty("comp_level") Level level,
               @JsonProperty("set_number") int setNumber,
               @JsonProperty("match_number") int matchNumber,
               @JsonProperty("alliances") Map<String, Alliance> alliances,
               @JsonProperty("winning_alliance") Color winningAlliance,
               @JsonProperty("event_key") String eventKey, @JsonProperty("time") Long scheduledTime,
               @JsonProperty("actual_time") Long actualTime,
               @JsonProperty("predicted_time") Long predictedTime,
               @JsonProperty("post_result_time") Long resultPostTime,
               @JsonProperty("videos") List<Video> videos,
               @JsonProperty("score_breakdown") Map<String, JsonNode> scoreBreakdown) {
    this(key, level, setNumber, matchNumber, alliances.get("red"), alliances.get("blue"),
         winningAlliance, eventKey, dateFromSeconds(scheduledTime), dateFromSeconds(actualTime),
         dateFromSeconds(predictedTime), dateFromSeconds(resultPostTime), videos,
         scoreBreakdown == null ? null : ScoreBreakdown.of(key, scoreBreakdown.get("red")),
         scoreBreakdown == null ? null : ScoreBreakdown.of(key, scoreBreakdown.get("blue")));
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

  private static Date dateFromSeconds(Long seconds) {
    return seconds == null ? null : new Date(seconds * 1000);
  }
}
