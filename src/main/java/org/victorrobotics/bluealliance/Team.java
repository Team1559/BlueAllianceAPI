package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.victorrobotics.bluealliance.Event.PlayoffAlliance.Status;
import org.victorrobotics.bluealliance.Event.Rankings.DataInfo;

public final class Team {
  public static final class Keys {
    private Keys() {}

    public static Endpoint<List<String>> endpointForPage(int page) {
      return Endpoint.forList("/teams/" + page + "/keys", String.class);
    }

    public static Endpoint<List<String>> endpointForYear(int year, int page) {
      return Endpoint.forList("/teams/" + year + "/" + page + "/keys", String.class);
    }

    public static Endpoint<List<String>> endpointForEvent(String eventKey) {
      return Endpoint.forList("/event/" + eventKey + "/teams/keys", String.class);
    }

    public static Endpoint<List<String>> endpointForDistrict(String districtKey) {
      return Endpoint.forList("/district/" + districtKey + "/teams/keys", String.class);
    }
  }

  public static final class YearsParticipated {
    private YearsParticipated() {}

    public static Endpoint<List<Integer>> endpointForKey(String teamKey) {
      return Endpoint.forList("/team/" + teamKey + "/years_participated", Integer.class);
    }
  }

  public static final class Simple {
    public final String key;
    public final int    number;
    public final String name;
    public final String fullName;
    public final String city;
    public final String province;
    public final String country;

    @JsonCreator
    Simple(@JsonProperty("key") String key, @JsonProperty("team_number") int number,
           @JsonProperty("nickname") String name, @JsonProperty("name") String fullName,
           @JsonProperty("city") String city, @JsonProperty("state_prov") String province,
           @JsonProperty("country") String country) {
      this.key = key;
      this.number = number;
      this.name = name;
      this.fullName = fullName;
      this.city = city;
      this.province = province;
      this.country = country;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Simple [key=")
             .append(key)
             .append(", number=")
             .append(number)
             .append(", name=")
             .append(name)
             .append(", fullName=")
             .append(fullName)
             .append(", city=")
             .append(city)
             .append(", province=")
             .append(province)
             .append(", country=")
             .append(country)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(key, number, name, fullName, city, province, country);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Simple)) return false;
      Simple other = (Simple) obj;
      return Objects.equals(key, other.key) && number == other.number
          && Objects.equals(name, other.name) && Objects.equals(fullName, other.fullName)
          && Objects.equals(city, other.city) && Objects.equals(province, other.province)
          && Objects.equals(country, other.country);
    }

    public static Endpoint<Team.Simple> endpointForKey(String teamKey) {
      return Endpoint.forSingle("/team/" + teamKey + "/simple", Team.Simple.class);
    }

    public static Endpoint<List<Team.Simple>> endpointForPage(int page) {
      return Endpoint.forList("/teams/" + page + "/simple", Team.Simple.class);
    }

    public static Endpoint<List<Team.Simple>> endpointForYear(int year, int page) {
      return Endpoint.forList("/teams/" + year + "/" + page + "/simple", Team.Simple.class);
    }

    public static Endpoint<List<Team.Simple>> endpointForEvent(String eventKey) {
      return Endpoint.forList("/event/" + eventKey + "/teams/simple", Team.Simple.class);
    }

    public static Endpoint<List<Team.Simple>> endpointForDistrict(String districtKey) {
      return Endpoint.forList("/district/" + districtKey + "/teams/simple", Team.Simple.class);
    }
  }

  public static final class Robot {
    public final int    year;
    public final String name;
    public final String key;
    public final String teamKey;

    @JsonCreator
    Robot(@JsonProperty("year") int year, @JsonProperty("robot_name") String name,
          @JsonProperty("key") String key, @JsonProperty("team_key") String teamKey) {
      this.year = year;
      this.name = name;
      this.key = key;
      this.teamKey = teamKey;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Robot [year=")
             .append(year)
             .append(", name=")
             .append(name)
             .append(", key=")
             .append(key)
             .append(", teamKey=")
             .append(teamKey)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(year, name, key, teamKey);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Robot)) return false;
      Robot other = (Robot) obj;
      return year == other.year && Objects.equals(name, other.name)
          && Objects.equals(key, other.key) && Objects.equals(teamKey, other.teamKey);
    }

    public static Endpoint<List<Robot>> endpointForTeam(String teamKey) {
      return Endpoint.forList("/team/" + teamKey + "/robots", Robot.class);
    }
  }

  public static final class EventStatus {
    public static final class Rank {
      public final int                           teamCount;
      public final Event.Rankings.Team           ranking;
      public final List<Event.Rankings.DataInfo> sortOrderInfo;
      public final String                        status;

      @JsonCreator
      Rank(@JsonProperty("num_teams") int teamCount,
           @JsonProperty("ranking") Event.Rankings.Team ranking,
           @JsonProperty("sort_order_info") List<DataInfo> sortOrderInfo,
           @JsonProperty("status") String status) {
        this.teamCount = teamCount;
        this.ranking = ranking;
        this.sortOrderInfo = sortOrderInfo;
        this.status = status;
      }

      @Override
      public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Rank [teamCount=")
               .append(teamCount)
               .append(", ranking=")
               .append(ranking)
               .append(", sortOrderInfo=")
               .append(sortOrderInfo)
               .append(", status=")
               .append(status)
               .append("]");
        return builder.toString();
      }

      @Override
      public int hashCode() {
        return Objects.hash(teamCount, ranking, sortOrderInfo, status);
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Rank)) return false;
        Rank other = (Rank) obj;
        return teamCount == other.teamCount && Objects.equals(ranking, other.ranking)
            && Objects.equals(sortOrderInfo, other.sortOrderInfo)
            && Objects.equals(status, other.status);
      }
    }

    public static final class Alliance {
      public static final class Backup {
        public final String out;
        public final String in;

        @JsonCreator
        Backup(@JsonProperty("out") String out, @JsonProperty("in") String in) {
          this.out = out;
          this.in = in;
        }

        @Override
        public String toString() {
          StringBuilder builder = new StringBuilder();
          builder.append("Backup [out=")
                 .append(out)
                 .append(", in=")
                 .append(in)
                 .append("]");
          return builder.toString();
        }

        @Override
        public int hashCode() {
          return Objects.hash(out, in);
        }

        @Override
        public boolean equals(Object obj) {
          if (this == obj) return true;
          if (!(obj instanceof Backup)) return false;
          Backup other = (Backup) obj;
          return Objects.equals(out, other.out) && Objects.equals(in, other.in);
        }
      }

      public final String name;
      public final int    num;
      public final Backup backup;
      public final int    pick;

      @JsonCreator
      Alliance(@JsonProperty("name") String name, @JsonProperty("number") int num,
               @JsonProperty("backup") Backup backup, @JsonProperty("pick") int pick) {
        this.name = name;
        this.num = num;
        this.backup = backup;
        this.pick = pick;
      }

      @Override
      public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Alliance [name=")
               .append(name)
               .append(", num=")
               .append(num)
               .append(", backup=")
               .append(backup)
               .append(", pick=")
               .append(pick)
               .append("]");
        return builder.toString();
      }

      @Override
      public int hashCode() {
        return Objects.hash(name, num, backup, pick);
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Alliance)) return false;
        Alliance other = (Alliance) obj;
        return Objects.equals(name, other.name) && num == other.num
            && Objects.equals(backup, other.backup) && pick == other.pick;
      }
    }

    public final Event.PlayoffAlliance.Status playoff;

    public final Rank     qualificationRank;
    public final Alliance alliance;
    public final String   allianceStatusHTML;
    public final String   playoffStatusHTML;
    public final String   overallStatusHTML;
    public final String   nextMatchKey;
    public final String   lastMatchKey;

    @JsonCreator
    EventStatus(@JsonProperty("qual") Rank qualificationRank,
                @JsonProperty("alliance") Alliance alliance,
                @JsonProperty("playoff") Status playoff,
                @JsonProperty("alliance_status_str") String allianceStatusHTML,
                @JsonProperty("playoff_status_str") String playoffStatusHTML,
                @JsonProperty("overall_status_str") String overallStatusHTML,
                @JsonProperty("next_match_key") String nextMatchKey,
                @JsonProperty("last_match_key") String lastMatchKey) {
      this.qualificationRank = qualificationRank;
      this.alliance = alliance;
      this.playoff = playoff;
      this.allianceStatusHTML = allianceStatusHTML;
      this.playoffStatusHTML = playoffStatusHTML;
      this.overallStatusHTML = overallStatusHTML;
      this.nextMatchKey = nextMatchKey;
      this.lastMatchKey = lastMatchKey;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EventStatus [qualificationRank=")
             .append(qualificationRank)
             .append(", alliance=")
             .append(alliance)
             .append(", playoff=")
             .append(playoff)
             .append(", allianceStatusHTML=")
             .append(allianceStatusHTML)
             .append(", playoffStatusHTML=")
             .append(playoffStatusHTML)
             .append(", overallStatusHTML=")
             .append(overallStatusHTML)
             .append(", nextMatchKey=")
             .append(nextMatchKey)
             .append(", lastMatchKey=")
             .append(lastMatchKey)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(qualificationRank, alliance, playoff, allianceStatusHTML,
                          playoffStatusHTML, overallStatusHTML, nextMatchKey, lastMatchKey);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof EventStatus)) return false;
      EventStatus other = (EventStatus) obj;
      return Objects.equals(qualificationRank, other.qualificationRank)
          && Objects.equals(alliance, other.alliance) && Objects.equals(playoff, other.playoff)
          && Objects.equals(allianceStatusHTML, other.allianceStatusHTML)
          && Objects.equals(playoffStatusHTML, other.playoffStatusHTML)
          && Objects.equals(overallStatusHTML, other.overallStatusHTML)
          && Objects.equals(nextMatchKey, other.nextMatchKey)
          && Objects.equals(lastMatchKey, other.lastMatchKey);
    }

    public static Endpoint<Map<String, EventStatus>> endpointForEvent(String eventKey) {
      return Endpoint.forMap("/event/" + eventKey + "/teams/statuses", EventStatus.class);
    }

    public static Endpoint<Map<String, EventStatus>> endpointForTeam(String teamKey, int year) {
      return Endpoint.forMap("/team/" + teamKey + "/events/" + year + "/statuses",
                             EventStatus.class);
    }

    public static Endpoint<EventStatus> endpointForTeam(String teamKey, String eventKey) {
      return Endpoint.forSingle("/team/" + teamKey + "/event/" + eventKey + "/status",
                                EventStatus.class);
    }
  }

  public final String key;
  public final int    number;
  public final String name;
  public final String fullName;
  public final String school;
  public final String city;
  public final String province;
  public final String country;
  public final String postalCode;
  public final String website;
  public final int    rookieYear;

  @JsonCreator
  Team(@JsonProperty("key") String key, @JsonProperty("team_number") int number,
       @JsonProperty("nickname") String name, @JsonProperty("name") String fullName,
       @JsonProperty("school_name") String school, @JsonProperty("city") String city,
       @JsonProperty("state_prov") String province, @JsonProperty("country") String country,
       @JsonProperty("postal_code") String postalCode, @JsonProperty("website") String website,
       @JsonProperty("rookie_year") int rookieYear) {
    this.key = key;
    this.number = number;
    this.name = name;
    this.fullName = fullName;
    this.school = school;
    this.city = city;
    this.province = province;
    this.country = country;
    this.postalCode = postalCode;
    this.website = website;
    this.rookieYear = rookieYear;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Team [key=")
           .append(key)
           .append(", number=")
           .append(number)
           .append(", name=")
           .append(name)
           .append(", fullName=")
           .append(fullName)
           .append(", school=")
           .append(school)
           .append(", city=")
           .append(city)
           .append(", province=")
           .append(province)
           .append(", country=")
           .append(country)
           .append(", postalCode=")
           .append(postalCode)
           .append(", website=")
           .append(website)
           .append(", rookieYear=")
           .append(rookieYear)
           .append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, number, name, fullName, school, city, province, country, postalCode,
                        website, rookieYear);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Team)) return false;
    Team other = (Team) obj;
    return Objects.equals(key, other.key) && number == other.number
        && Objects.equals(name, other.name) && Objects.equals(fullName, other.fullName)
        && Objects.equals(school, other.school) && Objects.equals(city, other.city)
        && Objects.equals(province, other.province) && Objects.equals(country, other.country)
        && Objects.equals(postalCode, other.postalCode) && Objects.equals(website, other.website)
        && rookieYear == other.rookieYear;
  }

  public static Endpoint<Team> endpointForKey(String teamKey) {
    return Endpoint.forSingle("/team/" + teamKey, Team.class);
  }

  public static Endpoint<List<Team>> endpointForPage(int page) {
    return Endpoint.forList("/teams/" + page, Team.class);
  }

  public static Endpoint<List<Team>> endpointForYear(int year, int page) {
    return Endpoint.forList("/teams/" + year + "/" + page, Team.class);
  }

  public static Endpoint<List<Team>> endpointForEvent(String eventKey) {
    return Endpoint.forList("/event/" + eventKey + "/teams", Team.class);
  }

  public static Endpoint<List<Team>> endpointForDistrict(String districtKey) {
    return Endpoint.forList("/district/" + districtKey + "/teams", Team.class);
  }
}
