package org.victorrobotics.bluealliance;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface ScoreBreakdown {
  static ScoreBreakdown of(String matchKey, JsonNode json) {
    if (matchKey == null || matchKey.length() < 4) {
      return null;
    }

    int year;
    try {
      year = Integer.parseInt(matchKey.substring(0, 4));
    } catch (NumberFormatException e) {
      return null;
    }

    Class<? extends ScoreBreakdown> type = switch (year) {
      case 2023 -> ChargedUp2023.class;
      case 2024 -> Crescendo2024.class;
      default -> null;
    };

    if (type == null) {
      return null;
    }

    try {
      return Endpoint.JSON_OBJECT_MAPPER.treeToValue(json, type);
    } catch (JsonProcessingException e) {
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

  public static record Crescendo2024(List<Boolean> autoLines,
                                     int autoSpeakerNoteCount,
                                     int autoAmpNoteCount,
                                     int autoLeavePoints,
                                     int autoSpeakerNotePoints,
                                     int autoAmpNotePoints,
                                     int autoTotalNotePoints,
                                     int autoPoints,
                                     int teleopAmpNoteCount,
                                     int teleopSpeakerNoteCount,
                                     int teleopSpeakerNoteAmplifiedCount,
                                     int teleopAmpNotePoints,
                                     int teleopSpeakerNotePoints,
                                     int teleopSpeakerNoteAmplifiedPoints,
                                     int teleopTotalNotePoints,
                                     int teleopPoints,
                                     boolean trapCenterStage,
                                     boolean trapStageLeft,
                                     boolean trapStageRight,
                                     boolean micCenterStage,
                                     boolean micStageLeft,
                                     boolean micStageRight,
                                     List<EndgameState> endGameRobots,
                                     int endGameOnStagePoints,
                                     int endGameHarmonyPoints,
                                     int endGameParkPoints,
                                     int endGameSpotLightBonusPoints,
                                     int endGameTotalStagePoints,
                                     int endGameNoteInTrapPoints,
                                     int ensembleBonusOnStageRobotsThreshold,
                                     int ensembleBonusStagePointsThreshold,
                                     boolean ensembleBonusAchieved,
                                     int melodyBonusThreshold,
                                     int melodyBonusThresholdCoop,
                                     int melodyBonusThresholdNonCoop,
                                     boolean melodyBonusAchieved,
                                     boolean coopNotePlayed,
                                     boolean coopertitionBonusAchieved,
                                     boolean coopertitionCriteriaMet,
                                     boolean g206Penalty,
                                     boolean g408Penalty,
                                     boolean g424Penalty,
                                     int foulCount,
                                     int techFoulCount,
                                     int foulPoints,
                                     int adjustPoints,
                                     int totalPoints,
                                     int rankingPoints)
      implements ScoreBreakdown {
    public enum EndgameState {
      NONE("None"),
      PARKED("Parked"),
      ONSTAGE("Onstage"),
      HARMONY("Harmony");

      @JsonValue
      private final String id;

      EndgameState(String value) {
        this.id = value;
      }
    }

    @JsonCreator
    public Crescendo2024(@JsonProperty("adjustPoints") int adjustPoints,
                         @JsonProperty("autoAmpNoteCount") int autoAmpNoteCount,
                         @JsonProperty("autoAmpNotePoints") int autoAmpNotePoints,
                         @JsonProperty("autoLeavePoints") int autoLeavePoints,
                         @JsonProperty("autoLineRobot1") String autoLineRobot1,
                         @JsonProperty("autoLineRobot2") String autoLineRobot2,
                         @JsonProperty("autoLineRobot3") String autoLineRobot3,
                         @JsonProperty("autoPoints") int autoPoints,
                         @JsonProperty("autoSpeakerNoteCount") int autoSpeakerNoteCount,
                         @JsonProperty("autoSpeakerNotePoints") int autoSpeakerNotePoints,
                         @JsonProperty("autoTotalNotePoints") int autoTotalNotePoints,
                         @JsonProperty("coopNotePlayed") boolean coopNotePlayed,
                         @JsonProperty("coopertitionBonusAchieved") boolean coopertitionBonusAchieved,
                         @JsonProperty("coopertitionCriteriaMet") boolean coopertitionCriteriaMet,
                         @JsonProperty("endGameHarmonyPoints") int endGameHarmonyPoints,
                         @JsonProperty("endGameNoteInTrapPoints") int endGameNoteInTrapPoints,
                         @JsonProperty("endGameOnStagePoints") int endGameOnStagePoints,
                         @JsonProperty("endGameParkPoints") int endGameParkPoints,
                         @JsonProperty("endGameRobot1") EndgameState endGameRobot1,
                         @JsonProperty("endGameRobot2") EndgameState endGameRobot2,
                         @JsonProperty("endGameRobot3") EndgameState endGameRobot3,
                         @JsonProperty("endGameSpotLightBonusPoints") int endGameSpotLightBonusPoints,
                         @JsonProperty("endGameTotalStagePoints") int endGameTotalStagePoints,
                         @JsonProperty("ensembleBonusAchieved") boolean ensembleBonusAchieved,
                         @JsonProperty("ensembleBonusOnStageRobotsThreshold") int ensembleBonusOnStageRobotsThreshold,
                         @JsonProperty("ensembleBonusStagePointsThreshold") int ensembleBonusStagePointsThreshold,
                         @JsonProperty("foulCount") int foulCount,
                         @JsonProperty("foulPoints") int foulPoints,
                         @JsonProperty("g206Penalty") boolean g206Penalty,
                         @JsonProperty("g408Penalty") boolean g408Penalty,
                         @JsonProperty("g424Penalty") boolean g424Penalty,
                         @JsonProperty("melodyBonusAchieved") boolean melodyBonusAchieved,
                         @JsonProperty("melodyBonusThreshold") int melodyBonusThreshold,
                         @JsonProperty("melodyBonusThresholdCoop") int melodyBonusThresholdCoop,
                         @JsonProperty("melodyBonusThresholdNonCoop") int melodyBonusThresholdNonCoop,
                         @JsonProperty("micCenterStage") boolean micCenterStage,
                         @JsonProperty("micStageLeft") boolean micStageLeft,
                         @JsonProperty("micStageRight") boolean micStageRight,
                         @JsonProperty("rp") int rp,
                         @JsonProperty("techFoulCount") int techFoulCount,
                         @JsonProperty("teleopAmpNoteCount") int teleopAmpNoteCount,
                         @JsonProperty("teleopAmpNotePoints") int teleopAmpNotePoints,
                         @JsonProperty("teleopPoints") int teleopPoints,
                         @JsonProperty("teleopSpeakerNoteAmplifiedCount") int teleopSpeakerNoteAmplifiedCount,
                         @JsonProperty("teleopSpeakerNoteAmplifiedPoints") int teleopSpeakerNoteAmplifiedPoints,
                         @JsonProperty("teleopSpeakerNoteCount") int teleopSpeakerNoteCount,
                         @JsonProperty("teleopSpeakerNotePoints") int teleopSpeakerNotePoints,
                         @JsonProperty("teleopTotalNotePoints") int teleopTotalNotePoints,
                         @JsonProperty("totalPoints") int totalPoints,
                         @JsonProperty("trapCenterStage") boolean trapCenterStage,
                         @JsonProperty("trapStageLeft") boolean trapStageLeft,
                         @JsonProperty("trapStageRight") boolean trapStageRight) {
      this(List.of(booleanFromYesNo(autoLineRobot1), booleanFromYesNo(autoLineRobot2),
                   booleanFromYesNo(autoLineRobot3)),
           autoSpeakerNoteCount, autoAmpNoteCount, autoLeavePoints, autoSpeakerNotePoints,
           autoAmpNotePoints, autoTotalNotePoints, autoPoints, teleopAmpNoteCount,
           teleopSpeakerNoteCount, teleopSpeakerNoteAmplifiedCount, teleopAmpNotePoints,
           teleopSpeakerNotePoints, teleopSpeakerNoteAmplifiedPoints, teleopTotalNotePoints,
           teleopPoints, trapCenterStage, trapStageLeft, trapStageRight, micCenterStage,
           micStageLeft, micStageRight, List.of(endGameRobot1, endGameRobot2, endGameRobot3),
           endGameOnStagePoints, endGameHarmonyPoints, endGameParkPoints,
           endGameSpotLightBonusPoints, endGameTotalStagePoints, endGameNoteInTrapPoints,
           ensembleBonusOnStageRobotsThreshold, ensembleBonusStagePointsThreshold,
           ensembleBonusAchieved, melodyBonusThreshold, melodyBonusThresholdCoop,
           melodyBonusThresholdNonCoop, melodyBonusAchieved, coopNotePlayed,
           coopertitionBonusAchieved, coopertitionCriteriaMet, g206Penalty, g408Penalty,
           g424Penalty, foulCount, techFoulCount, foulPoints, adjustPoints, totalPoints, rp);
    }
  }

  private static boolean booleanFromYesNo(String yesNo) {
    return "Yes".equalsIgnoreCase(yesNo);
  }
}
