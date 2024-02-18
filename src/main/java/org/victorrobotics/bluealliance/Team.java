package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.victorrobotics.bluealliance.Event.PlayoffAlliance.Status;
import org.victorrobotics.bluealliance.Event.Rankings.DataInfo;

public record Team(@JsonProperty("key") String key,
                   @JsonProperty("team_number") int number,
                   @JsonProperty("nickname") String name,
                   @JsonProperty("name") String fullName,
                   @JsonProperty("school_name") String school,
                   @JsonProperty("city") String city,
                   @JsonProperty("state_prov") String province,
                   @JsonProperty("country") String country,
                   @JsonProperty("postal_code") String postalCode,
                   @JsonProperty("website") String website,
                   @JsonProperty("rookie_year") int rookieYear) {
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

  public static record Simple(@JsonProperty("key") String key,
                              @JsonProperty("team_number") int number,
                              @JsonProperty("nickname") String name,
                              @JsonProperty("name") String fullName,
                              @JsonProperty("city") String city,
                              @JsonProperty("state_prov") String province,
                              @JsonProperty("country") String country) {

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

  public static record Robot(@JsonProperty("year") int year,
                             @JsonProperty("robot_name") String name,
                             @JsonProperty("key") String key,
                             @JsonProperty("team_key") String teamKey) {
    public static Endpoint<List<Robot>> endpointForTeam(String teamKey) {
      return Endpoint.forList("/team/" + teamKey + "/robots", Robot.class);
    }
  }

  public static record EventStatus(@JsonProperty("qual") Rank qualificationRank,
                                   @JsonProperty("alliance") Alliance alliance,
                                   @JsonProperty("playoff") Status playoff,
                                   @JsonProperty("alliance_status_str") String allianceStatusHTML,
                                   @JsonProperty("playoff_status_str") String playoffStatusHTML,
                                   @JsonProperty("overall_status_str") String overallStatusHTML,
                                   @JsonProperty("next_match_key") String nextMatchKey,
                                   @JsonProperty("last_match_key") String lastMatchKey) {
    public static record Rank(@JsonProperty("num_teams") int teamCount,
                              @JsonProperty("ranking") Event.Rankings.Team ranking,
                              @JsonProperty("sort_order_info") List<DataInfo> sortOrderInfo,
                              @JsonProperty("status") String status) {}

    public static record Alliance(@JsonProperty("name") String name,
                                  @JsonProperty("number") int num,
                                  @JsonProperty("backup") Backup backup,
                                  @JsonProperty("pick") int pick) {
      public static record Backup(@JsonProperty("out") String out,
                                  @JsonProperty("in") String in) {}
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
