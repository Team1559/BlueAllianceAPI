package org.victorrobotics.bluealliance;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface ScoreBreakdown {
  @SuppressWarnings("java:S1301") // replace switch with if
  static ScoreBreakdown of(String matchKey, JsonNode json) {
    try {
      int year = Integer.parseInt(matchKey.substring(0, 4));
      switch (year) {
        case 2023:
          return Endpoint.JSON_OBJECT_MAPPER.treeToValue(json, ChargedUp2023.class);
        default:
          return null;
      }
    } catch (NumberFormatException | IndexOutOfBoundsException | JsonProcessingException e) {
      return null;
    }
  }

  public static record ChargedUp2023(List<Boolean> autoMobility,
                                     Community autoCommunity,
                                     int autoPieceCount,
                                     boolean isAutoChargeStationLevel,
                                     boolean isAutoChargeStationDocked,
                                     Community teleopCommunity,
                                     int teleopPieceCount,
                                     int superchargedNodeCount,
                                     List<Link> links,
                                     List<EndgameState> endgameRobots,
                                     boolean isEndgameChargeStationEngaged,
                                     int autoMobilityPoints,
                                     int autoPiecePoints,
                                     int autoChargeStationPoints,
                                     int totalAutoPoints,
                                     int teleopPiecePoints,
                                     int endgameChargeStationPoints,
                                     int totalTeleopPoints,
                                     int linkPoints,
                                     int endgameParkPoints,
                                     int foulPoints,
                                     int adjustmentPoints,
                                     int totalMatchPoints,
                                     int totalChargeStationPoints,
                                     int foulCount,
                                     int techFoulCount,
                                     int coopPieceCount,
                                     boolean isCoopCriteriaMet,
                                     boolean activationBonus,
                                     boolean sustainabilityBonus,
                                     int rankingPoints)
      implements ScoreBreakdown {
    public static record Community(@JsonProperty("B") List<NodeState> bottom,
                                   @JsonProperty("M") List<NodeState> mid,
                                   @JsonProperty("T") List<NodeState> top) {}

    public static record Link(@JsonProperty("row") NodeRow row,
                              @JsonProperty("nodes") List<Integer> nodes) {}

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

    @JsonCreator
    public ChargedUp2023(@JsonProperty("activationBonus") boolean activationBonus,
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
      this(List.of(booleanFromYesNo(autoMobility1), booleanFromYesNo(autoMobility2),
                   booleanFromYesNo(autoMobility3)),
           autoCommunity, autoPieceCount, isLevel(autoChargeStationState), autoDocked,
           teleopCommunity, teleopPieceCount, superchargedNodeCount, links,
           List.of(endgameRobot1, endgameRobot2, endgameRobot3), isLevel(endgameBridgeState),
           autoMobilityPoints, autoPiecePoints, autoChargeStationPoints, totalAutoPoints,
           teleopPiecePoints, endgameChargeStationPoints, totalTeleopPoints, linkPoints,
           endgameParkPoints, foulPoints, adjustmentPoints, totalMatchPoints,
           totalChargeStationPoints, foulCount, techFoulCount, coopPieceCount, isCoopCriteriaMet,
           activationBonus, sustainabilityBonus, rankingPoints);
    }

    private static boolean isLevel(String bridgeState) {
      return "Level".equals(bridgeState);
    }
  }

  private static boolean booleanFromYesNo(String yesNo) {
    return "Yes".equalsIgnoreCase(yesNo);
  }
}
