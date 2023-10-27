package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public final class ScoreBreakdowns {
  private ScoreBreakdowns() {}

  public static final class ChargedUp2023 implements Match.ScoreBreakdown {
    public static final class Community {
      public final List<NodeState> bottom;
      public final List<NodeState> mid;
      public final List<NodeState> top;

      @JsonCreator
      private Community(@JsonProperty("B") List<NodeState> bottom,
                        @JsonProperty("M") List<NodeState> mid,
                        @JsonProperty("T") List<NodeState> top) {
        this.bottom = bottom == null ? null : List.copyOf(bottom);
        this.mid = mid == null ? null : List.copyOf(mid);
        this.top = top == null ? null : List.copyOf(top);
      }

      @Override
      public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Community [bottom=")
               .append(bottom)
               .append(", mid=")
               .append(mid)
               .append(", top=")
               .append(top)
               .append("]");
        return builder.toString();
      }

      @Override
      public int hashCode() {
        return Objects.hash(bottom, mid, top);
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Community)) return false;
        Community other = (Community) obj;
        return Objects.equals(bottom, other.bottom) && Objects.equals(mid, other.mid)
            && Objects.equals(top, other.top);
      }
    }

    public static final class Link {
      public final NodeRow       row;
      public final List<Integer> nodes;

      @JsonCreator
      private Link(@JsonProperty("row") NodeRow row, @JsonProperty("nodes") List<Integer> nodes) {
        this.row = row;
        this.nodes = nodes == null ? null : List.copyOf(nodes);
      }

      @Override
      public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Link [row=")
               .append(row)
               .append(", nodes=")
               .append(nodes)
               .append("]");
        return builder.toString();
      }

      @Override
      public int hashCode() {
        return Objects.hash(row, nodes);
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Link)) return false;
        Link other = (Link) obj;
        return row == other.row && Objects.equals(nodes, other.nodes);
      }
    }

    public enum NodeState {
      NONE("None"),
      CONE("Cone"),
      CUBE("Cube");

      @JsonValue
      private final String id;

      NodeState(String id) {
        this.id = id;
      }
    }

    public enum NodeRow {
      BOTTOM("Bottom"),
      MID("Mid"),
      TOP("Top");

      @JsonValue
      private final String id;

      NodeRow(String id) {
        this.id = id;
      }
    }

    public enum EndgameState {
      NONE("None"),
      DOCKED("Docked"),
      PARKED("Park");

      @JsonValue
      private final String id;

      EndgameState(String id) {
        this.id = id;
      }
    }

    public final boolean   autoMobility1;
    public final boolean   autoMobility2;
    public final boolean   autoMobility3;
    public final Community autoCommunity;
    public final int       autoPieceCount;

    public final int     autoChargeStationRobot;
    public final boolean isAutoChargeStationLevel;
    public final boolean isAutoChargeStationDocked;

    public final Community  teleopCommunity;
    public final int        teleopPieceCount;
    public final int        superchargedNodeCount;
    public final List<Link> links;

    public final EndgameState endgameRobot1;
    public final EndgameState endgameRobot2;
    public final EndgameState endgameRobot3;
    public final boolean      isEndgameChargeStationEngaged;

    public final int autoMobilityPoints;
    public final int autoPiecePoints;
    public final int autoChargeStationPoints;
    public final int totalAutoPoints;
    public final int teleopPiecePoints;
    public final int endgameChargeStationPoints;
    public final int totalTeleopPoints;
    public final int linkPoints;
    public final int endgameParkPoints;
    public final int foulPoints;
    public final int adjustmentPoints;
    public final int totalMatchPoints;
    public final int totalChargeStationPoints;

    public final int foulCount;
    public final int techFoulCount;

    public final int     coopPieceCount;
    public final boolean isCoopCriteriaMet;
    public final boolean activationBonus;
    public final boolean sustainabilityBonus;
    public final int     rankingPoints;

    @JsonCreator
    private ChargedUp2023(@JsonProperty("activationBonus") boolean activationBonus,
                          @JsonProperty("adjustPoints") int adjustmentPoints,
                          @JsonProperty("autoBridgeState") String autoChargeStationState,
                          @JsonProperty("autoChargeStationPoints") int autoChargeStationPoints,
                          @JsonProperty("autoChargeStationRobot1") String autoChargeStation1,
                          @JsonProperty("autoChargeStationRobot2") String autoChargeStation2,
                          @JsonProperty("autoChargeStationRobot3") String autoChargeStation3,
                          @JsonProperty("autoCommunity") Community autoCommunity,
                          @JsonProperty("autoDocked") boolean autoDocked,
                          @JsonProperty("autoGamePieceCount") int autoPieceCount,
                          @JsonProperty("autoGamePiecePoints") int autoPiecePoints,
                          @JsonProperty("autoMobilityPoints") int autoMobilityPoints,
                          @JsonProperty("autoPoints") int totalAutoPoints,
                          @JsonProperty("coopertitionCriteriaMet") boolean isCoopCriteriaMet,
                          @JsonProperty("coopPieceCount") int coopPieceCount,
                          @JsonProperty("endgameBridgeState") String endgameBridgeState,
                          @JsonProperty("endGameChargeStationPoints") int endgameChargeStationPoints,
                          @JsonProperty("endGameChargeStationRobot1") EndgameState endgameRobot1,
                          @JsonProperty("endGameChargeStationRobot2") EndgameState endgameRobot2,
                          @JsonProperty("endGameChargeStationRobot3") EndgameState endgameRobot3,
                          @JsonProperty("endGameParkPoints") int endgameParkPoints,
                          @JsonProperty("extraGamePieceCount") int superchargedNodeCount,
                          @JsonProperty("foulCount") int foulCount,
                          @JsonProperty("foulPoints") int foulPoints,
                          @JsonProperty("linkPoints") int linkPoints,
                          @JsonProperty("links") List<Link> links,
                          @JsonProperty("mobilityRobot1") String autoMobility1,
                          @JsonProperty("mobilityRobot2") String autoMobility2,
                          @JsonProperty("mobilityRobot3") String autoMobility3,
                          @JsonProperty("rp") int rankingPoints,
                          @JsonProperty("sustainabilityBonusAchieved") boolean sustainabilityBonus,
                          @JsonProperty("techFoulCount") int techFoulCount,
                          @JsonProperty("teleopCommunity") Community teleopCommunity,
                          @JsonProperty("teleopGamePieceCount") int teleopPieceCount,
                          @JsonProperty("teleopGamePiecePoints") int teleopPiecePoints,
                          @JsonProperty("totalChargeStationPoints") int totalChargeStationPoints,
                          @JsonProperty("teleopPoints") int totalTeleopPoints,
                          @JsonProperty("totalPoints") int totalMatchPoints) {
      this.autoMobility1 = "Yes".equals(autoMobility1);
      this.autoMobility2 = "Yes".equals(autoMobility2);
      this.autoMobility3 = "Yes".equals(autoMobility3);
      this.autoCommunity = autoCommunity;
      this.autoPieceCount = autoPieceCount;

      this.isAutoChargeStationLevel = "Level".equals(autoChargeStationState);
      this.isAutoChargeStationDocked = autoDocked;

      this.teleopCommunity = teleopCommunity;
      this.teleopPieceCount = teleopPieceCount;
      this.superchargedNodeCount = superchargedNodeCount;
      this.links = links == null ? null : List.copyOf(links);

      this.endgameRobot1 = endgameRobot1;
      this.endgameRobot2 = endgameRobot2;
      this.endgameRobot3 = endgameRobot3;
      this.isEndgameChargeStationEngaged = "Level".equals(endgameBridgeState);

      this.autoMobilityPoints = autoMobilityPoints;
      this.autoPiecePoints = autoPiecePoints;
      this.autoChargeStationPoints = autoChargeStationPoints;
      this.totalAutoPoints = totalAutoPoints;
      this.teleopPiecePoints = teleopPiecePoints;
      this.endgameChargeStationPoints = endgameChargeStationPoints;
      this.totalTeleopPoints = totalTeleopPoints;
      this.linkPoints = linkPoints;
      this.endgameParkPoints = endgameParkPoints;
      this.foulPoints = foulPoints;
      this.adjustmentPoints = adjustmentPoints;
      this.totalMatchPoints = totalMatchPoints;
      this.totalChargeStationPoints = totalChargeStationPoints;

      this.foulCount = foulCount;
      this.techFoulCount = techFoulCount;

      this.coopPieceCount = coopPieceCount;
      this.isCoopCriteriaMet = isCoopCriteriaMet;
      this.activationBonus = activationBonus;
      this.sustainabilityBonus = sustainabilityBonus;
      this.rankingPoints = rankingPoints;

      if (EndgameState.DOCKED.id.equals(autoChargeStation1)) {
        this.autoChargeStationRobot = 1;
      } else if (EndgameState.DOCKED.id.equals(autoChargeStation2)) {
        this.autoChargeStationRobot = 2;
      } else if (EndgameState.DOCKED.id.equals(autoChargeStation3)) {
        this.autoChargeStationRobot = 3;
      } else {
        this.autoChargeStationRobot = 0;
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("ChargedUp2023 [autoMobility1=")
             .append(autoMobility1)
             .append(", autoMobility2=")
             .append(autoMobility2)
             .append(", autoMobility3=")
             .append(autoMobility3)
             .append(", autoCommunity=")
             .append(autoCommunity)
             .append(", autoPieceCount=")
             .append(autoPieceCount)
             .append(", autoChargeStationRobot=")
             .append(autoChargeStationRobot)
             .append(", isAutoChargeStationLevel=")
             .append(isAutoChargeStationLevel)
             .append(", isAutoChargeStationDocked=")
             .append(isAutoChargeStationDocked)
             .append(", teleopCommunity=")
             .append(teleopCommunity)
             .append(", teleopPieceCount=")
             .append(teleopPieceCount)
             .append(", superchargedNodeCount=")
             .append(superchargedNodeCount)
             .append(", links=")
             .append(links)
             .append(", endgameRobot1=")
             .append(endgameRobot1)
             .append(", endgameRobot2=")
             .append(endgameRobot2)
             .append(", endgameRobot3=")
             .append(endgameRobot3)
             .append(", isEndgameChargeStationEngaged=")
             .append(isEndgameChargeStationEngaged)
             .append(", autoMobilityPoints=")
             .append(autoMobilityPoints)
             .append(", autoPiecePoints=")
             .append(autoPiecePoints)
             .append(", autoChargeStationPoints=")
             .append(autoChargeStationPoints)
             .append(", totalAutoPoints=")
             .append(totalAutoPoints)
             .append(", teleopPiecePoints=")
             .append(teleopPiecePoints)
             .append(", endgameChargeStationPoints=")
             .append(endgameChargeStationPoints)
             .append(", totalTeleopPoints=")
             .append(totalTeleopPoints)
             .append(", linkPoints=")
             .append(linkPoints)
             .append(", endgameParkPoints=")
             .append(endgameParkPoints)
             .append(", foulPoints=")
             .append(foulPoints)
             .append(", adjustmentPoints=")
             .append(adjustmentPoints)
             .append(", totalMatchPoints=")
             .append(totalMatchPoints)
             .append(", totalChargeStationPoints=")
             .append(totalChargeStationPoints)
             .append(", foulCount=")
             .append(foulCount)
             .append(", techFoulCount=")
             .append(techFoulCount)
             .append(", coopPieceCount=")
             .append(coopPieceCount)
             .append(", isCoopCriteriaMet=")
             .append(isCoopCriteriaMet)
             .append(", activationBonus=")
             .append(activationBonus)
             .append(", sustainabilityBonus=")
             .append(sustainabilityBonus)
             .append(", rankingPoints=")
             .append(rankingPoints)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(autoMobility1, autoMobility2, autoMobility3, autoCommunity,
                          autoPieceCount, autoChargeStationRobot, isAutoChargeStationLevel,
                          isAutoChargeStationDocked, teleopCommunity, teleopPieceCount,
                          superchargedNodeCount, links, endgameRobot1, endgameRobot2, endgameRobot3,
                          isEndgameChargeStationEngaged, autoMobilityPoints, autoPiecePoints,
                          autoChargeStationPoints, totalAutoPoints, teleopPiecePoints,
                          endgameChargeStationPoints, totalTeleopPoints, linkPoints,
                          endgameParkPoints, foulPoints, adjustmentPoints, totalMatchPoints,
                          totalChargeStationPoints, foulCount, techFoulCount, coopPieceCount,
                          isCoopCriteriaMet, activationBonus, sustainabilityBonus, rankingPoints);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof ChargedUp2023)) return false;
      ChargedUp2023 other = (ChargedUp2023) obj;
      return autoMobility1 == other.autoMobility1 && autoMobility2 == other.autoMobility2
          && autoMobility3 == other.autoMobility3
          && Objects.equals(autoCommunity, other.autoCommunity)
          && autoPieceCount == other.autoPieceCount
          && autoChargeStationRobot == other.autoChargeStationRobot
          && isAutoChargeStationLevel == other.isAutoChargeStationLevel
          && isAutoChargeStationDocked == other.isAutoChargeStationDocked
          && Objects.equals(teleopCommunity, other.teleopCommunity)
          && teleopPieceCount == other.teleopPieceCount
          && superchargedNodeCount == other.superchargedNodeCount
          && Objects.equals(links, other.links) && endgameRobot1 == other.endgameRobot1
          && endgameRobot2 == other.endgameRobot2 && endgameRobot3 == other.endgameRobot3
          && isEndgameChargeStationEngaged == other.isEndgameChargeStationEngaged
          && autoMobilityPoints == other.autoMobilityPoints
          && autoPiecePoints == other.autoPiecePoints
          && autoChargeStationPoints == other.autoChargeStationPoints
          && totalAutoPoints == other.totalAutoPoints
          && teleopPiecePoints == other.teleopPiecePoints
          && endgameChargeStationPoints == other.endgameChargeStationPoints
          && totalTeleopPoints == other.totalTeleopPoints && linkPoints == other.linkPoints
          && endgameParkPoints == other.endgameParkPoints && foulPoints == other.foulPoints
          && adjustmentPoints == other.adjustmentPoints
          && totalMatchPoints == other.totalMatchPoints
          && totalChargeStationPoints == other.totalChargeStationPoints
          && foulCount == other.foulCount && techFoulCount == other.techFoulCount
          && coopPieceCount == other.coopPieceCount && isCoopCriteriaMet == other.isCoopCriteriaMet
          && activationBonus == other.activationBonus
          && sustainabilityBonus == other.sustainabilityBonus
          && rankingPoints == other.rankingPoints;
    }
  }
}
