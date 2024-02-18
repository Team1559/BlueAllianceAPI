package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public record Event(@JsonProperty("key") String key,
                    @JsonProperty("name") String name,
                    @JsonProperty("event_code") String code,
                    @JsonProperty("event_type") Type type,
                    @JsonProperty("district") District district,
                    @JsonProperty("city") String city,
                    @JsonProperty("state_prov") String stateProv,
                    @JsonProperty("country") String country,
                    @JsonProperty("start_date") Date startDate,
                    @JsonProperty("end_date") Date endDate,
                    @JsonProperty("year") int year,
                    @JsonProperty("short_name") String shortName,
                    @JsonProperty("event_type_string") String typeString,
                    @JsonProperty("week") Integer week,
                    @JsonProperty("address") String address,
                    @JsonProperty("postal_code") String postalCode,
                    @JsonProperty("gmaps_place_id") String gmapsPlaceID,
                    @JsonProperty("gmaps_url") String gmapsURL,
                    @JsonProperty("lat") double latitude,
                    @JsonProperty("lng") double longitude,
                    @JsonProperty("location_name") String locationName,
                    @JsonProperty("timezone") String timezone,
                    @JsonProperty("website") String website,
                    @JsonProperty("first_event_id") String firstID,
                    @JsonProperty("first_event_code") String firstCode,
                    @JsonProperty("webcasts") List<Webcast> webcasts,
                    @JsonProperty("division_keys") List<String> divisionKeys,
                    @JsonProperty("parent_event_key") String parentEventKey,
                    @JsonProperty("playoff_type") PlayoffType playoffType,
                    @JsonProperty("playoff_type_string") String playoffTypeDescription) {
  public static final class Keys {
    private Keys() {}

    public static Endpoint<List<String>> endpointForYear(int year) {
      return Endpoint.forList("/events/" + year + "/keys", String.class);
    }

    public static Endpoint<List<String>> endpointForTeam(String teamKey) {
      return Endpoint.forList("/team/" + teamKey + "/events/keys", String.class);
    }

    public static Endpoint<List<String>> endpointForTeam(String teamKey, int year) {
      return Endpoint.forList("/team/" + teamKey + "/events/" + year + "/keys", String.class);
    }

    public static Endpoint<List<String>> endpointForDistrict(String districtKey) {
      return Endpoint.forList("/district/" + districtKey + "/events/keys", String.class);
    }
  }

  public static record Simple(@JsonProperty("key") String key,
                              @JsonProperty("name") String name,
                              @JsonProperty("event_code") String code,
                              @JsonProperty("event_type") Type type,
                              @JsonProperty("district") District district,
                              @JsonProperty("city") String city,
                              @JsonProperty("state_prov") String stateProv,
                              @JsonProperty("country") String country,
                              @JsonProperty("start_date") Date startDate,
                              @JsonProperty("end_date") Date endDate,
                              @JsonProperty("year") int year) {

    public static Endpoint<Event.Simple> endpointForKey(String eventKey) {
      return Endpoint.forSingle("/event/" + eventKey + "/simple", Event.Simple.class);
    }

    public static Endpoint<List<Event.Simple>> endpointForYear(int year) {
      return Endpoint.forList("/events/" + year + "/simple", Event.Simple.class);
    }

    public static Endpoint<List<Event.Simple>> endpointForTeam(String teamKey) {
      return Endpoint.forList("/team/" + teamKey + "/events/simple", Event.Simple.class);
    }

    public static Endpoint<List<Event.Simple>> endpointForTeam(String teamKey, int year) {
      return Endpoint.forList("/team/" + teamKey + "/events/" + year + "/simple",
                              Event.Simple.class);
    }

    public static Endpoint<List<Event.Simple>> endpointForDistrict(String districtKey) {
      return Endpoint.forList("/district/" + districtKey + "/events/simple", Event.Simple.class);
    }
  }

  public static record PlayoffAlliance(@JsonProperty("name") String name,
                                       @JsonProperty("backup") Backup backup,
                                       @JsonProperty("declines") List<String> declinedTeams,
                                       @JsonProperty("picks") List<String> teams,
                                       @JsonProperty("status") Status status) {
    public static record Backup(@JsonProperty("in") String teamIn,
                                @JsonProperty("out") String teamOut) {}

    public static record Status(@JsonProperty("playoff_average") double playoffAverage,
                                @JsonProperty("level") String level,
                                @JsonProperty("record") WinLossRecord totalRecord,
                                @JsonProperty("current_level_record") WinLossRecord currentRecord,
                                @JsonProperty("status") String statusStr) {}

    public static Endpoint<List<PlayoffAlliance>> endpointForKey(String eventKey) {
      return Endpoint.forList("/event/" + eventKey + "/alliance", PlayoffAlliance.class);
    }
  }

  public static record WinLossRecord(@JsonProperty("losses") int losses,
                                     @JsonProperty("wins") int wins,
                                     @JsonProperty("ties") int ties) {}

  public static record Webcast(@JsonProperty("type") Type type,
                               @JsonProperty("channel") String channel,
                               @JsonProperty("date") Date date,
                               @JsonProperty("file") String file) {
    public enum Type {
      YOUTUBE("youtube"),
      TWITCH("twitch"),
      USTREAM("ustream"),
      IFRAME("iframe"),
      HTML5("html5"),
      RTMP("rtmp"),
      LIVESTREAM("livestream"),
      DIRECT_LINK("direct_link"),
      MMS("mms"),
      JUSTIN("justin"),
      STEM_TV("stemtv"),
      DACAST("dacast"),

      @JsonEnumDefaultValue
      UNKNOWN("???");

      @JsonValue
      private final String id;

      Type(String id) {
        this.id = id;
      }
    }
  }

  public static record DistrictPoints(@JsonProperty("points") Map<Integer, Data> points,
                                      @JsonProperty("tiebreakers") Map<Integer, TieBreaker> tiebreakers) {
    public static record Data(@JsonProperty("total") int totalPoints,
                              @JsonProperty("alliance_points") int alliancePoints,
                              @JsonProperty("elim_points") int eliminationPoints,
                              @JsonProperty("award_points") int awardPoints,
                              @JsonProperty("qual_points") int qualificationPoints) {}

    public static record TieBreaker(@JsonProperty("highest_qual_scores") List<Integer> highestQualScores,
                                    @JsonProperty("qual_wins") int qualificationWins) {}

    public static Endpoint<DistrictPoints> endpointForKey(String eventKey) {
      return Endpoint.forSingle("/event/" + eventKey + "/district_points", DistrictPoints.class);
    }
  }

  public static record Rankings(@JsonProperty("rankings") List<Event.Rankings.Team> rankings,
                                @JsonProperty("extra_stats_info") List<DataInfo> extraStats,
                                @JsonProperty("sort_order_info") List<DataInfo> sortOrder) {
    public static record Team(@JsonProperty("matches_played") int matchesPlayed,
                              @JsonProperty("qual_average") Integer averageMatchScore,
                              @JsonProperty("extra_stats") List<Integer> extraStats,
                              @JsonProperty("sort_orders") List<Integer> sortOrders,
                              @JsonProperty("record") WinLossRecord winLossRecord,
                              @JsonProperty("rank") int rank,
                              @JsonProperty("dq") int disqualificationCount,
                              @JsonProperty("team_key") String teamKey) {}

    public static record DataInfo(@JsonProperty("precision") int precision,
                                  @JsonProperty("name") String name) {}

    public static Endpoint<Event.Rankings> endpointForEvent(String eventKey) {
      return Endpoint.forSingle("/event/" + eventKey + "/rankings", Event.Rankings.class);
    }
  }

  public static record OPRs(@JsonProperty("oprs") Map<String, Double> oprs,
                            @JsonProperty("dprs") Map<String, Double> dprs,
                            @JsonProperty("ccwms") Map<String, Double> ccwms) {
    public static Endpoint<Event.OPRs> endpointForEvent(String eventKey) {
      return Endpoint.forSingle("/event/" + eventKey + "/oprs", Event.OPRs.class);
    }
  }

  public static record Date(int year,
                            int month,
                            int day)
      implements Comparable<Date> {

    @JsonCreator
    public Date(String formatted) {
      // pattern: yyyy-MM-dd
      this(Integer.parseInt(formatted.substring(0, 4)), Integer.parseInt(formatted.substring(5, 7)),
           Integer.parseInt(formatted.substring(8)));
    }

    @Override
    public int compareTo(Date other) {
      if (year != other.year) {
        return Integer.compare(year, other.year);
      }

      if (month != other.month) {
        return Integer.compare(month, other.month);
      }

      return Integer.compare(day, other.day);
    }

    @Override
    @JsonValue
    public String toString() {
      return String.format("%04d-%02d-%02d", year, month, day);
    }
  }

  public enum Type {
    REGIONAL(0),
    DISTRICT(1),
    DISTRICT_CMP(2),
    CMP_DIVISION(3),
    CMP_FINALS(4),
    DISTRICT_CMP_DIVISION(5),
    FOC(6),
    REMOTE(7),

    OFFSEASON(99),
    PRESEASON(100),

    @JsonEnumDefaultValue
    UNLABELED(-1);

    @JsonValue
    private final int code;

    Type(int code) {
      this.code = code;
    }
  }

  public enum PlayoffType {
    ELIM_8(0, "Elimination Bracket (8 Alliances)"),
    ELIM_16(1, "Elimination Bracket (16 Alliances)"),
    ELIM_4(2, "Elimination Bracket (4 Alliances)"),

    AVG_SCORE_8(3, "Average Score (8 Alliances)"),
    ROUND_ROBIN_6(4, "Round Robin (6 Alliances)"),
    DOUBLE_ELIM_8(5, "Double Elimination Bracket (8 Alliances)"),

    BEST_5_FINALS(6, "Best of 5 Finals"),
    BEST_3_FINALS(7, "Best of 3 Finals"),

    @JsonEnumDefaultValue
    CUSTOM(8, "Custom"),

    ELIM_2(9, "Elimination Bracket (2 Alliances)"),
    DOUBLE_ELIM_8_NEW(10, "Double Elimination Bracket (8 Alliances)"),
    DOUBLE_ELIM_4(11, "Double Elimination Bracket (4 Alliances)");

    @JsonValue
    public final int id;

    public final String description;

    PlayoffType(int id, String description) {
      this.id = id;
      this.description = description;
    }
  }

  public static Endpoint<Event> endpointForKey(String eventKey) {
    return Endpoint.forSingle("/event/" + eventKey, Event.class);
  }

  public static Endpoint<List<Event>> endpointForYear(int year) {
    return Endpoint.forList("/events/" + year, Event.class);
  }

  public static Endpoint<List<Event>> endpointForTeam(String teamKey) {
    return Endpoint.forList("/team/" + teamKey + "/events/simple", Event.class);
  }

  public static Endpoint<List<Event>> endpointForTeam(String teamKey, int year) {
    return Endpoint.forList("/team/" + teamKey + "/events/" + year + "/simple", Event.class);
  }

  public static Endpoint<List<Event>> endpointForDistrict(String districtKey) {
    return Endpoint.forList("/district/" + districtKey + "/events/simple", Event.class);
  }
}
