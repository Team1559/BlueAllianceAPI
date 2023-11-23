package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class District {
  public static final class Ranking {
    public static final class EventPoints {
      public final boolean isDistrictCampionship;
      public final int     totalPoints;
      public final int     alliancePoints;
      public final int     eliminationPoints;
      public final int     awardPoints;
      public final String  eventKey;
      public final int     qualificationPoints;

      @JsonCreator
      EventPoints(@JsonProperty("district_cmp") boolean isDistrictCampionship,
                  @JsonProperty("total") int totalPoints,
                  @JsonProperty("alliance_points") int alliancePoints,
                  @JsonProperty("elim_points") int eliminationPoints,
                  @JsonProperty("award_points") int awardPoints,
                  @JsonProperty("event_key") String eventKey,
                  @JsonProperty("qual_points") int qualificationPoints) {
        this.isDistrictCampionship = isDistrictCampionship;
        this.totalPoints = totalPoints;
        this.alliancePoints = alliancePoints;
        this.eliminationPoints = eliminationPoints;
        this.awardPoints = awardPoints;
        this.eventKey = eventKey;
        this.qualificationPoints = qualificationPoints;
      }

      @Override
      public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EventPoints [isDistrictCampionship=")
               .append(isDistrictCampionship)
               .append(", totalPoints=")
               .append(totalPoints)
               .append(", alliancePoints=")
               .append(alliancePoints)
               .append(", eliminationPoints=")
               .append(eliminationPoints)
               .append(", awardPoints=")
               .append(awardPoints)
               .append(", eventKey=")
               .append(eventKey)
               .append(", qualificationPoints=")
               .append(qualificationPoints)
               .append("]");
        return builder.toString();
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

    public final String            teamKey;
    public final int               rank;
    public final int               rookieBonus;
    public final int               pointTotal;
    public final List<EventPoints> eventPoints;

    @JsonCreator
    Ranking(@JsonProperty("team_key") String teamKey, @JsonProperty("rank") int rank,
            @JsonProperty("rookie_bonus") int rookieBonus,
            @JsonProperty("point_total") int pointTotal,
            @JsonProperty("event_points") List<EventPoints> eventPoints) {
      this.teamKey = teamKey;
      this.rank = rank;
      this.rookieBonus = rookieBonus;
      this.pointTotal = pointTotal;
      this.eventPoints = eventPoints == null ? null : List.copyOf(eventPoints);
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Ranking [teamKey=")
             .append(teamKey)
             .append(", rank=")
             .append(rank)
             .append(", rookieBonus=")
             .append(rookieBonus)
             .append(", pointTotal=")
             .append(pointTotal)
             .append(", eventPoints=")
             .append(eventPoints)
             .append("]");
      return builder.toString();
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

    public static Endpoint<List<District.Ranking>> endpointForKey(String districtKey) {
      return Endpoint.forList("/district/" + districtKey + "/rankings", District.Ranking.class);
    }
  }

  public final String abbreviation;
  public final String displayName;
  public final String key;
  public final int    year;

  @JsonCreator
  District(@JsonProperty("abbrevitation") String abbreviation,
           @JsonProperty("display_name") String displayName, @JsonProperty("key") String key,
           @JsonProperty("year") int year) {
    this.abbreviation = abbreviation;
    this.displayName = displayName;
    this.key = key;
    this.year = year;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("District [abbreviation=")
           .append(abbreviation)
           .append(", displayName=")
           .append(displayName)
           .append(", key=")
           .append(key)
           .append(", year=")
           .append(year)
           .append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(abbreviation, displayName, key, year);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof District)) return false;
    District other = (District) obj;
    return Objects.equals(abbreviation, other.abbreviation)
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
