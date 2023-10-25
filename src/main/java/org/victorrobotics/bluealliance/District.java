package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class District {
  public static final class Ranking {
    public static final class EventPoints {
      @JsonProperty("district_cmp")
      private boolean isDistrictCampionship;

      @JsonProperty("total")
      private int totalPoints;

      @JsonProperty("alliance_points")
      private int alliancePoints;

      @JsonProperty("elim_points")
      private int eliminationPoints;

      @JsonProperty("award_points")
      private int awardPoints;

      @JsonProperty("event_key")
      private String eventKey;

      @JsonProperty("qual_points")
      private int qualificationPoints;

      public EventPoints() {}

      @JsonIgnore
      public boolean isDistrictCampionship() {
        return isDistrictCampionship;
      }

      public int getTotalPoints() {
        return totalPoints;
      }

      public int getAlliancePoints() {
        return alliancePoints;
      }

      public int getEliminationPoints() {
        return eliminationPoints;
      }

      public int getAwardPoints() {
        return awardPoints;
      }

      public String getEventKey() {
        return eventKey;
      }

      public int getQualificationPoints() {
        return qualificationPoints;
      }

      @Override
      public int hashCode() {
        return Objects.hash(isDistrictCampionship, totalPoints, alliancePoints, eliminationPoints,
                            awardPoints, eventKey, qualificationPoints);
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EventPoints)) return false;
        EventPoints other = (EventPoints) obj;
        return isDistrictCampionship == other.isDistrictCampionship
            && totalPoints == other.totalPoints && alliancePoints == other.alliancePoints
            && eliminationPoints == other.eliminationPoints && awardPoints == other.awardPoints
            && Objects.equals(eventKey, other.eventKey)
            && qualificationPoints == other.qualificationPoints;
      }
    }

    @JsonProperty("team_key")
    private String teamKey;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("rookie_bonus")
    private int rookieBonus;

    @JsonProperty("point_total")
    private int pointTotal;

    @JsonProperty("event_points")
    private List<District.Ranking.EventPoints> eventPoints;

    public Ranking() {}

    public String getTeamKey() {
      return teamKey;
    }

    public int getRank() {
      return rank;
    }

    public int getRookieBonus() {
      return rookieBonus;
    }

    public int getPointTotal() {
      return pointTotal;
    }

    public List<District.Ranking.EventPoints> getEventPoints() {
      return eventPoints;
    }

    @Override
    public int hashCode() {
      return Objects.hash(teamKey, rank, rookieBonus, pointTotal, eventPoints);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Ranking)) return false;
      Ranking other = (Ranking) obj;
      return Objects.equals(teamKey, other.teamKey) && rank == other.rank
          && rookieBonus == other.rookieBonus && pointTotal == other.pointTotal
          && Objects.equals(eventPoints, other.eventPoints);
    }

    public static Endpoint<List<District.Ranking>> endpoint(String districtKey) {
      return Endpoint.forList("/district/" + districtKey + "/rankings", District.Ranking.class);
    }
  }

  @JsonProperty("abbrevitation")
  private String abbrevitation;

  @JsonProperty("display_name")
  private String displayName;

  @JsonProperty("key")
  private String key;

  @JsonProperty("year")
  private int year;

  public District() {}

  public String getAbbrevitation() {
    return abbrevitation;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getKey() {
    return key;
  }

  public int getYear() {
    return year;
  }

  @Override
  public int hashCode() {
    return Objects.hash(abbrevitation, displayName, key, year);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof District)) return false;
    District other = (District) obj;
    return Objects.equals(abbrevitation, other.abbrevitation)
        && Objects.equals(displayName, other.displayName) && Objects.equals(key, other.key)
        && year == other.year;
  }

  public static Endpoint<List<District>> endpointForYear(int year) {
    return Endpoint.forList("/districts/" + year, District.class);
  }

  public static Endpoint<List<District>> endpointForTeam(String teamKey) {
    return Endpoint.forList("/team/" + teamKey + "/districts", District.class);
  }
}
