package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public final class Event {
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

  public static final class Simple {
    public final String   key;
    public final String   name;
    public final String   code;
    public final Type     type;
    public final District district;
    public final String   city;
    public final String   stateProv;
    public final String   country;
    public final Date     startDate;
    public final Date     endDate;
    public final int      year;

    @JsonCreator
    Simple(@JsonProperty("key") String key, @JsonProperty("name") String name,
           @JsonProperty("event_code") String code, @JsonProperty("event_type") Type type,
           @JsonProperty("district") District district, @JsonProperty("city") String city,
           @JsonProperty("state_prov") String stateProv, @JsonProperty("country") String country,
           @JsonProperty("start_date") Date startDate, @JsonProperty("end_date") Date endDate,
           @JsonProperty("year") int year) {
      this.key = key;
      this.name = name;
      this.code = code;
      this.type = type;
      this.district = district;
      this.city = city;
      this.stateProv = stateProv;
      this.country = country;
      this.startDate = startDate;
      this.endDate = endDate;
      this.year = year;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Simple [key=")
             .append(key)
             .append(", name=")
             .append(name)
             .append(", code=")
             .append(code)
             .append(", type=")
             .append(type)
             .append(", district=")
             .append(district)
             .append(", city=")
             .append(city)
             .append(", stateProv=")
             .append(stateProv)
             .append(", country=")
             .append(country)
             .append(", startDate=")
             .append(startDate)
             .append(", endDate=")
             .append(endDate)
             .append(", year=")
             .append(year)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(key, name, code, type, district, city, stateProv, country, startDate,
                          endDate, year);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Simple)) return false;
      Simple other = (Simple) obj;
      return Objects.equals(key, other.key) && Objects.equals(name, other.name)
          && Objects.equals(code, other.code) && type == other.type
          && Objects.equals(district, other.district) && Objects.equals(city, other.city)
          && Objects.equals(stateProv, other.stateProv) && Objects.equals(country, other.country)
          && Objects.equals(startDate, other.startDate) && Objects.equals(endDate, other.endDate)
          && year == other.year;
    }

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

  public static final class PlayoffAlliance {
    public static final class Backup {
      public final String teamIn;
      public final String teamOut;

      @JsonCreator
      Backup(@JsonProperty("in") String teamIn, @JsonProperty("out") String teamOut) {
        this.teamIn = teamIn;
        this.teamOut = teamOut;
      }

      @Override
      public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Backup [teamIn=")
               .append(teamIn)
               .append(", teamOut=")
               .append(teamOut)
               .append("]");
        return builder.toString();
      }

      @Override
      public int hashCode() {
        return Objects.hash(teamIn, teamOut);
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Backup)) return false;
        Backup other = (Backup) obj;
        return Objects.equals(teamIn, other.teamIn) && Objects.equals(teamOut, other.teamOut);
      }
    }

    public static final class Status {
      public final double        playoffAverage;
      public final String        level;
      public final WinLossRecord totalRecord;
      public final WinLossRecord currentRecord;
      public final String        statusStr;

      @JsonCreator
      Status(@JsonProperty("playoff_average") double playoffAverage,
             @JsonProperty("level") String level, @JsonProperty("record") WinLossRecord totalRecord,
             @JsonProperty("current_level_record") WinLossRecord currentRecord,
             @JsonProperty("status") String statusStr) {
        this.playoffAverage = playoffAverage;
        this.level = level;
        this.totalRecord = totalRecord;
        this.currentRecord = currentRecord;
        this.statusStr = statusStr;
      }

      @Override
      public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Status [playoffAverage=")
               .append(playoffAverage)
               .append(", level=")
               .append(level)
               .append(", totalRecord=")
               .append(totalRecord)
               .append(", currentRecord=")
               .append(currentRecord)
               .append(", statusStr=")
               .append(statusStr)
               .append("]");
        return builder.toString();
      }

      @Override
      public int hashCode() {
        return Objects.hash(playoffAverage, level, totalRecord, currentRecord, statusStr);
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Status)) return false;
        Status other = (Status) obj;
        return Double.doubleToLongBits(playoffAverage)
            == Double.doubleToLongBits(other.playoffAverage) && Objects.equals(level, other.level)
            && Objects.equals(totalRecord, other.totalRecord)
            && Objects.equals(currentRecord, other.currentRecord)
            && Objects.equals(statusStr, other.statusStr);
      }
    }

    public final String       name;
    public final Backup       backup;
    public final List<String> declinedTeams;
    public final List<String> teams;
    public final Status       status;

    @JsonCreator
    PlayoffAlliance(@JsonProperty("name") String name, @JsonProperty("backup") Backup backup,
                    @JsonProperty("declines") List<String> declinedTeams,
                    @JsonProperty("picks") List<String> teams,
                    @JsonProperty("status") Status status) {
      this.name = name;
      this.backup = backup;
      this.declinedTeams = declinedTeams == null ? null : List.copyOf(declinedTeams);
      this.teams = teams == null ? null : List.copyOf(teams);
      this.status = status;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("PlayoffAlliance [name=")
             .append(name)
             .append(", backup=")
             .append(backup)
             .append(", declinedTeams=")
             .append(declinedTeams)
             .append(", teams=")
             .append(teams)
             .append(", status=")
             .append(status)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, backup, declinedTeams, teams, status);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof PlayoffAlliance)) return false;
      PlayoffAlliance other = (PlayoffAlliance) obj;
      return Objects.equals(name, other.name) && Objects.equals(backup, other.backup)
          && Objects.equals(declinedTeams, other.declinedTeams)
          && Objects.equals(teams, other.teams) && Objects.equals(status, other.status);
    }

    public static Endpoint<List<PlayoffAlliance>> endpointForKey(String eventKey) {
      return Endpoint.forList("/event/" + eventKey + "/alliance", PlayoffAlliance.class);
    }
  }

  public static final class WinLossRecord {
    public final int losses;
    public final int wins;
    public final int ties;

    @JsonCreator
    WinLossRecord(@JsonProperty("losses") int losses, @JsonProperty("wins") int wins,
                  @JsonProperty("ties") int ties) {
      this.losses = losses;
      this.wins = wins;
      this.ties = ties;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("WinLossRecord [losses=")
             .append(losses)
             .append(", wins=")
             .append(wins)
             .append(", ties=")
             .append(ties)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(losses, wins, ties);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof WinLossRecord)) return false;
      WinLossRecord other = (WinLossRecord) obj;
      return losses == other.losses && wins == other.wins && ties == other.ties;
    }
  }

  public static final class Webcast {
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

    public final Type   type;
    public final String channel;
    public final Date   date;
    public final String file;

    @JsonCreator
    Webcast(@JsonProperty("type") Type type, @JsonProperty("channel") String channel,
            @JsonProperty("date") Date date, @JsonProperty("file") String file) {
      this.type = type;
      this.channel = channel;
      this.date = date;
      this.file = file;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Webcast [type=")
             .append(type)
             .append(", channel=")
             .append(channel)
             .append(", date=")
             .append(date)
             .append(", file=")
             .append(file)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, channel, date, file);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Webcast)) return false;
      Webcast other = (Webcast) obj;
      return type == other.type && Objects.equals(channel, other.channel)
          && Objects.equals(date, other.date) && Objects.equals(file, other.file);
    }
  }

  public static final class DistrictPoints {
    public static final class Data {
      public final int totalPoints;
      public final int alliancePoints;
      public final int eliminationPoints;
      public final int awardPoints;
      public final int qualificationPoints;

      @JsonCreator
      Data(@JsonProperty("total") int totalPoints,
           @JsonProperty("alliance_points") int alliancePoints,
           @JsonProperty("elim_points") int eliminationPoints,
           @JsonProperty("award_points") int awardPoints,
           @JsonProperty("qual_points") int qualificationPoints) {
        this.totalPoints = totalPoints;
        this.alliancePoints = alliancePoints;
        this.eliminationPoints = eliminationPoints;
        this.awardPoints = awardPoints;
        this.qualificationPoints = qualificationPoints;
      }

      @Override
      public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Points [totalPoints=")
               .append(totalPoints)
               .append(", alliancePoints=")
               .append(alliancePoints)
               .append(", eliminationPoints=")
               .append(eliminationPoints)
               .append(", awardPoints=")
               .append(awardPoints)
               .append(", qualificationPoints=")
               .append(qualificationPoints)
               .append("]");
        return builder.toString();
      }

      @Override
      public int hashCode() {
        return Objects.hash(totalPoints, alliancePoints, eliminationPoints, awardPoints,
                            qualificationPoints);
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Data)) return false;
        Data other = (Data) obj;
        return totalPoints == other.totalPoints && alliancePoints == other.alliancePoints
            && eliminationPoints == other.eliminationPoints && awardPoints == other.awardPoints
            && qualificationPoints == other.qualificationPoints;
      }
    }

    public static final class TieBreaker {
      public final List<Integer> highestQualScores;
      public final int           qualificationWins;

      @JsonCreator
      TieBreaker(@JsonProperty("highest_qual_scores") List<Integer> highestQualScores,
                 @JsonProperty("qual_wins") int qualificationWins) {
        this.highestQualScores = highestQualScores == null ? null : List.copyOf(highestQualScores);
        this.qualificationWins = qualificationWins;
      }

      @Override
      public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TieBreaker [highestQualScores=")
               .append(highestQualScores)
               .append(", qualificationWins=")
               .append(qualificationWins)
               .append("]");
        return builder.toString();
      }

      @Override
      public int hashCode() {
        return Objects.hash(highestQualScores, qualificationWins);
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TieBreaker)) return false;
        TieBreaker other = (TieBreaker) obj;
        return Objects.equals(highestQualScores, other.highestQualScores)
            && qualificationWins == other.qualificationWins;
      }
    }

    public final Map<Integer, Data>       points;
    public final Map<Integer, TieBreaker> tiebreakers;

    @JsonCreator
    DistrictPoints(@JsonProperty("points") Map<Integer, Data> points,
                   @JsonProperty("tiebreakers") Map<Integer, TieBreaker> tiebreakers) {
      this.points = points == null ? null : Map.copyOf(points);
      this.tiebreakers = tiebreakers == null ? null : Map.copyOf(tiebreakers);
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("DistrictPoints [points=")
             .append(points)
             .append(", tiebreakers=")
             .append(tiebreakers)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(points, tiebreakers);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof DistrictPoints)) return false;
      DistrictPoints other = (DistrictPoints) obj;
      return Objects.equals(points, other.points) && Objects.equals(tiebreakers, other.tiebreakers);
    }

    public static Endpoint<DistrictPoints> endpointForKey(String eventKey) {
      return Endpoint.forSingle("/event/" + eventKey + "/district_points", DistrictPoints.class);
    }
  }

  public static final class Rankings {
    public static final class Team {
      public final int           matchesPlayed;
      public final Integer       averageMatchScore;
      public final List<Integer> extraStats;
      public final List<Integer> sortOrders;
      public final WinLossRecord winLossRecord;
      public final int           rank;
      public final int           disqualificationCount;
      public final String        teamKey;

      @JsonCreator
      Team(@JsonProperty("matches_played") int matchesPlayed,
           @JsonProperty("qual_average") Integer averageMatchScore,
           @JsonProperty("extra_stats") List<Integer> extraStats,
           @JsonProperty("sort_orders") List<Integer> sortOrders,
           @JsonProperty("record") WinLossRecord winLossRecord, @JsonProperty("rank") int rank,
           @JsonProperty("dq") int disqualificationCount,
           @JsonProperty("team_key") String teamKey) {
        this.matchesPlayed = matchesPlayed;
        this.averageMatchScore = averageMatchScore;
        this.extraStats = extraStats == null ? null : List.copyOf(extraStats);
        this.sortOrders = sortOrders == null ? null : List.copyOf(sortOrders);
        this.winLossRecord = winLossRecord;
        this.rank = rank;
        this.disqualificationCount = disqualificationCount;
        this.teamKey = teamKey;
      }

      @Override
      public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Team [matchesPlayed=")
               .append(matchesPlayed)
               .append(", averageMatchScore=")
               .append(averageMatchScore)
               .append(", extraStats=")
               .append(extraStats)
               .append(", sortOrders=")
               .append(sortOrders)
               .append(", winLossRecord=")
               .append(winLossRecord)
               .append(", rank=")
               .append(rank)
               .append(", disqualificationCount=")
               .append(disqualificationCount)
               .append(", teamKey=")
               .append(teamKey)
               .append("]");
        return builder.toString();
      }

      @Override
      public int hashCode() {
        return Objects.hash(matchesPlayed, averageMatchScore, extraStats, sortOrders, winLossRecord,
                            rank, disqualificationCount, teamKey);
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Team)) return false;
        Team other = (Team) obj;
        return matchesPlayed == other.matchesPlayed
            && Objects.equals(averageMatchScore, other.averageMatchScore)
            && Objects.equals(extraStats, other.extraStats)
            && Objects.equals(sortOrders, other.sortOrders)
            && Objects.equals(winLossRecord, other.winLossRecord) && rank == other.rank
            && disqualificationCount == other.disqualificationCount
            && Objects.equals(teamKey, other.teamKey);
      }
    }

    public static final class DataInfo {
      public final int    precision;
      public final String name;

      @JsonCreator
      DataInfo(@JsonProperty("precision") int precision, @JsonProperty("name") String name) {
        this.precision = precision;
        this.name = name;
      }

      @Override
      public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DataInfo [precision=")
               .append(precision)
               .append(", name=")
               .append(name)
               .append("]");
        return builder.toString();
      }

      @Override
      public int hashCode() {
        return Objects.hash(precision, name);
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DataInfo)) return false;
        DataInfo other = (DataInfo) obj;
        return precision == other.precision && Objects.equals(name, other.name);
      }
    }

    public final List<Event.Rankings.Team> rankings;
    public final List<DataInfo>            extraStats;
    public final List<DataInfo>            sortOrder;

    @JsonCreator
    Rankings(@JsonProperty("rankings") List<Event.Rankings.Team> rankings,
             @JsonProperty("extra_stats_info") List<DataInfo> extraStats,
             @JsonProperty("sort_order_info") List<DataInfo> sortOrder) {
      this.rankings = rankings == null ? null : List.copyOf(rankings);
      this.extraStats = extraStats == null ? null : List.copyOf(extraStats);
      this.sortOrder = extraStats == null ? null : List.copyOf(sortOrder);
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Rankings [rankings=")
             .append(rankings)
             .append(", extraStats=")
             .append(extraStats)
             .append(", sortOrder=")
             .append(sortOrder)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(rankings, extraStats, sortOrder);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Rankings)) return false;
      Rankings other = (Rankings) obj;
      return Objects.equals(rankings, other.rankings)
          && Objects.equals(extraStats, other.extraStats)
          && Objects.equals(sortOrder, other.sortOrder);
    }

    public static Endpoint<List<Event.Rankings>> endpointForEvent(String eventKey) {
      return Endpoint.forList("/event/" + eventKey + "/rankings", Event.Rankings.class);
    }
  }

  public static final class OPRs {
    public final Map<String, Double> offensivePowerRatings;
    public final Map<String, Double> defensivePowerRatings;
    public final Map<String, Double> contributionsToWinMargin;

    @JsonCreator
    OPRs(@JsonProperty("oprs") Map<String, Double> oprs,
         @JsonProperty("dprs") Map<String, Double> dprs,
         @JsonProperty("ccwms") Map<String, Double> ccwms) {
      this.offensivePowerRatings = oprs == null ? null : Map.copyOf(oprs);
      this.defensivePowerRatings = dprs == null ? null : Map.copyOf(dprs);
      this.contributionsToWinMargin = ccwms == null ? null : Map.copyOf(ccwms);
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("OPRs [offensivePowerRatings=")
             .append(offensivePowerRatings)
             .append(", defensivePowerRatings=")
             .append(defensivePowerRatings)
             .append(", contributionsToWinMargin=")
             .append(contributionsToWinMargin)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(offensivePowerRatings, defensivePowerRatings, contributionsToWinMargin);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof OPRs)) return false;
      OPRs other = (OPRs) obj;
      return Objects.equals(offensivePowerRatings, other.offensivePowerRatings)
          && Objects.equals(defensivePowerRatings, other.defensivePowerRatings)
          && Objects.equals(contributionsToWinMargin, other.contributionsToWinMargin);
    }

    public static Endpoint<Event.OPRs> endpointForEvent(String eventKey) {
      return Endpoint.forSingle("/event/" + eventKey + "/oprs", Event.OPRs.class);
    }
  }

  public static final class Date implements Comparable<Date> {
    public final int    year;
    public final int    month;
    public final int    day;
    public final String formatted;

    @JsonCreator
    Date(String str) {
      // pattern: yyyy-MM-dd
      year = Integer.parseInt(str.substring(0, 4));
      month = Integer.parseInt(str.substring(5, 7));
      day = Integer.parseInt(str.substring(8));
      formatted = str;
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

    @JsonValue
    @Override
    public String toString() {
      return formatted;
    }

    @Override
    public int hashCode() {
      return Objects.hash(year, month, day);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Date)) return false;
      Date other = (Date) obj;
      return year == other.year && month == other.month && day == other.day;
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

  public final String        key;
  public final String        name;
  public final String        code;
  public final Type          type;
  public final District      district;
  public final String        city;
  public final String        stateProv;
  public final String        country;
  public final Date          startDate;
  public final Date          endDate;
  public final int           year;
  public final String        shortName;
  public final String        typeString;
  public final Integer       week;
  public final String        address;
  public final String        postalCode;
  public final String        gmapsPlaceID;
  public final String        gmapsURL;
  public final double        latitude;
  public final double        longitude;
  public final String        locationName;
  public final String        timezone;
  public final String        website;
  public final String        firstID;
  public final String        firstCode;
  public final List<Webcast> webcasts;
  public final List<String>  divisionKeys;
  public final String        parentEventKey;
  public final PlayoffType   playoffType;
  public final String        playoffTypeDescription;

  @JsonCreator
  Event(@JsonProperty("key") String key, @JsonProperty("name") String name,
        @JsonProperty("event_code") String code, @JsonProperty("event_type") Type type,
        @JsonProperty("district") District district, @JsonProperty("city") String city,
        @JsonProperty("state_prov") String stateProv, @JsonProperty("country") String country,
        @JsonProperty("start_date") Date startDate, @JsonProperty("end_date") Date endDate,
        @JsonProperty("year") int year, @JsonProperty("short_name") String shortName,
        @JsonProperty("event_type_string") String typeString, @JsonProperty("week") Integer week,
        @JsonProperty("address") String address, @JsonProperty("postal_code") String postalCode,
        @JsonProperty("gmaps_place_id") String gmapsPlaceID,
        @JsonProperty("gmaps_url") String gmapsURL, @JsonProperty("lat") double latitude,
        @JsonProperty("lng") double longitude, @JsonProperty("location_name") String locationName,
        @JsonProperty("timezone") String timezone, @JsonProperty("website") String website,
        @JsonProperty("first_event_id") String firstID,
        @JsonProperty("first_event_code") String firstCode,
        @JsonProperty("webcasts") List<Webcast> webcasts,
        @JsonProperty("division_keys") List<String> divisionKeys,
        @JsonProperty("parent_event_key") String parentEventKey,
        @JsonProperty("playoff_type") PlayoffType playoffType,
        @JsonProperty("playoff_type_string") String playoffTypeDescription) {
    this.key = key;
    this.name = name;
    this.code = code;
    this.type = type;
    this.district = district;
    this.city = city;
    this.stateProv = stateProv;
    this.country = country;
    this.startDate = startDate;
    this.endDate = endDate;
    this.year = year;
    this.shortName = shortName;
    this.typeString = typeString;
    this.week = week;
    this.address = address;
    this.postalCode = postalCode;
    this.gmapsPlaceID = gmapsPlaceID;
    this.gmapsURL = gmapsURL;
    this.latitude = latitude;
    this.longitude = longitude;
    this.locationName = locationName;
    this.timezone = timezone;
    this.website = website;
    this.firstID = firstID;
    this.firstCode = firstCode;
    this.webcasts = webcasts == null ? null : List.copyOf(webcasts);
    this.divisionKeys = divisionKeys == null ? null : List.copyOf(divisionKeys);
    this.parentEventKey = parentEventKey;
    this.playoffType = playoffType;
    this.playoffTypeDescription = playoffTypeDescription;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Event [key=")
           .append(key)
           .append(", name=")
           .append(name)
           .append(", code=")
           .append(code)
           .append(", type=")
           .append(type)
           .append(", district=")
           .append(district)
           .append(", city=")
           .append(city)
           .append(", stateProv=")
           .append(stateProv)
           .append(", country=")
           .append(country)
           .append(", startDate=")
           .append(startDate)
           .append(", endDate=")
           .append(endDate)
           .append(", year=")
           .append(year)
           .append(", shortName=")
           .append(shortName)
           .append(", typeString=")
           .append(typeString)
           .append(", week=")
           .append(week)
           .append(", address=")
           .append(address)
           .append(", postalCode=")
           .append(postalCode)
           .append(", gmapsPlaceID=")
           .append(gmapsPlaceID)
           .append(", gmapsURL=")
           .append(gmapsURL)
           .append(", latitude=")
           .append(latitude)
           .append(", longitude=")
           .append(longitude)
           .append(", locationName=")
           .append(locationName)
           .append(", timezone=")
           .append(timezone)
           .append(", website=")
           .append(website)
           .append(", firstID=")
           .append(firstID)
           .append(", firstCode=")
           .append(firstCode)
           .append(", webcasts=")
           .append(webcasts)
           .append(", divisionKeys=")
           .append(divisionKeys)
           .append(", parentEventKey=")
           .append(parentEventKey)
           .append(", playoffType=")
           .append(playoffType)
           .append(", playoffTypeDescription=")
           .append(playoffTypeDescription)
           .append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, name, code, type, district, city, stateProv, country, startDate,
                        endDate, year, shortName, typeString, week, address, postalCode,
                        gmapsPlaceID, gmapsURL, latitude, longitude, locationName, timezone,
                        website, firstID, firstCode, webcasts, divisionKeys, parentEventKey,
                        playoffType, playoffTypeDescription);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Event)) return false;
    Event other = (Event) obj;
    return Objects.equals(key, other.key) && Objects.equals(name, other.name)
        && Objects.equals(code, other.code) && type == other.type
        && Objects.equals(district, other.district) && Objects.equals(city, other.city)
        && Objects.equals(stateProv, other.stateProv) && Objects.equals(country, other.country)
        && Objects.equals(startDate, other.startDate) && Objects.equals(endDate, other.endDate)
        && year == other.year && Objects.equals(shortName, other.shortName)
        && Objects.equals(typeString, other.typeString) && Objects.equals(week, other.week)
        && Objects.equals(address, other.address) && Objects.equals(postalCode, other.postalCode)
        && Objects.equals(gmapsPlaceID, other.gmapsPlaceID)
        && Objects.equals(gmapsURL, other.gmapsURL)
        && Double.doubleToLongBits(latitude) == Double.doubleToLongBits(other.latitude)
        && Double.doubleToLongBits(longitude) == Double.doubleToLongBits(other.longitude)
        && Objects.equals(locationName, other.locationName)
        && Objects.equals(timezone, other.timezone) && Objects.equals(website, other.website)
        && Objects.equals(firstID, other.firstID) && Objects.equals(firstCode, other.firstCode)
        && Objects.equals(webcasts, other.webcasts)
        && Objects.equals(divisionKeys, other.divisionKeys)
        && Objects.equals(parentEventKey, other.parentEventKey) && playoffType == other.playoffType
        && Objects.equals(playoffTypeDescription, other.playoffTypeDescription);
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
