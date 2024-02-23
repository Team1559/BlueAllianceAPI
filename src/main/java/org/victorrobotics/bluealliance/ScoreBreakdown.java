package org.victorrobotics.bluealliance;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public sealed interface ScoreBreakdown {
  public record UnknownScoreBreakdown() implements ScoreBreakdown {}

  static ScoreBreakdown of(String matchKey, JsonNode json) {
    if (json == null || matchKey == null || matchKey.length() < 4) {
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
      default -> UnknownScoreBreakdown.class;
    };

    try {
      return Endpoint.JSON_OBJECT_MAPPER.treeToValue(json, type);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public record ChargedUp2023(List<Boolean> autoMobilityRobots,
                              Community autoCommunity,
                              int autoGamePieceCount,
                              List<Boolean> autoChargeStationRobots,
                              boolean autoChargeStationLevel,
                              boolean autoDocked,
                              int autoMobilityPoints,
                              int autoGamePiecePoints,
                              int autoChargeStationPoints,
                              int autoPoints,
                              Community teleopCommunity,
                              int teleopGamePieceCount,
                              int teleopSuperchargedGamePieceCount,
                              int teleopGamePiecePoints,
                              int teleopPoints,
                              List<EndgameState> endGameChargeStationRobots,
                              boolean endGameChargeStationLevel,
                              int endGameParkPoints,
                              int endGameChargeStationPoints,
                              List<Link> links,
                              int linkPoints,
                              boolean sustainabilityBonusAchieved,
                              boolean activationBonus,
                              int totalChargeStationPoints,
                              int coopPieceCount,
                              boolean coopertitionCriteriaMet,
                              int foulCount,
                              int techFoulCount,
                              int foulPoints,
                              int adjustPoints,
                              int totalPoints,
                              int rankingPoints)
      implements ScoreBreakdown {
    public record Community(@JsonProperty("B") List<NodeState> bottom,
                            @JsonProperty("M") List<NodeState> mid,
                            @JsonProperty("T") List<NodeState> top) {}

    public record Link(@JsonProperty("row") NodeRow row,
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
                         @JsonProperty("adjustPoints") int adjustPoints,
                         @JsonProperty("autoBridgeState") String autoBridgeState,
                         @JsonProperty("autoChargeStationPoints") int autoChargeStationPoints,
                         @JsonProperty("autoChargeStationRobot1") String autoChargeStationRobot1,
                         @JsonProperty("autoChargeStationRobot2") String autoChargeStationRobot2,
                         @JsonProperty("autoChargeStationRobot3") String autoChargeStationRobot3,
                         @JsonProperty("autoCommunity") Community autoCommunity,
                         @JsonProperty("autoDocked") boolean autoDocked,
                         @JsonProperty("autoGamePieceCount") int autoGamePieceCount,
                         @JsonProperty("autoGamePiecePoints") int autoGamePiecePoints,
                         @JsonProperty("autoMobilityPoints") int autoMobilityPoints,
                         @JsonProperty("autoPoints") int autoPoints,
                         @JsonProperty("coopertitionCriteriaMet") boolean coopertitionCriteriaMet,
                         @JsonProperty("coopPieceCount") int coopPieceCount,
                         @JsonProperty("endgameBridgeState") String endgameBridgeState,
                         @JsonProperty("endGameChargeStationPoints") int endGameChargeStationPoints,
                         @JsonProperty("endGameChargeStationRobot1") EndgameState endGameChargeStationRobot1,
                         @JsonProperty("endGameChargeStationRobot2") EndgameState endGameChargeStationRobot2,
                         @JsonProperty("endGameChargeStationRobot3") EndgameState endGameChargeStationRobot3,
                         @JsonProperty("endGameParkPoints") int endGameParkPoints,
                         @JsonProperty("extraGamePieceCount") int extraGamePieceCount,
                         @JsonProperty("foulCount") int foulCount,
                         @JsonProperty("foulPoints") int foulPoints,
                         @JsonProperty("linkPoints") int linkPoints,
                         @JsonProperty("links") List<Link> links,
                         @JsonProperty("mobilityRobot1") String mobilityRobot1,
                         @JsonProperty("mobilityRobot2") String mobilityRobot2,
                         @JsonProperty("mobilityRobot3") String mobilityRobot3,
                         @JsonProperty("rp") int rp,
                         @JsonProperty("sustainabilityBonusAchieved") boolean sustainabilityBonusAchieved,
                         @JsonProperty("techFoulCount") int techFoulCount,
                         @JsonProperty("teleopCommunity") Community teleopCommunity,
                         @JsonProperty("teleopGamePieceCount") int teleopGamePieceCount,
                         @JsonProperty("teleopGamePiecePoints") int teleopGamePiecePoints,
                         @JsonProperty("totalChargeStationPoints") int totalChargeStationPoints,
                         @JsonProperty("teleopPoints") int teleopPoints,
                         @JsonProperty("totalPoints") int totalPoints) {
      this(Arrays.asList(booleanFromYesNo(mobilityRobot1), booleanFromYesNo(mobilityRobot2),
                         booleanFromYesNo(mobilityRobot3)),
           autoCommunity, autoGamePieceCount,
           Arrays.asList(booleanFromYesNo(autoChargeStationRobot1),
                         booleanFromYesNo(autoChargeStationRobot2),
                         booleanFromYesNo(autoChargeStationRobot3)),
           isBridgeLevel(autoBridgeState), autoDocked, autoMobilityPoints, autoGamePiecePoints,
           autoChargeStationPoints, autoPoints, teleopCommunity, teleopGamePieceCount,
           extraGamePieceCount, teleopGamePiecePoints, teleopPoints,
           Arrays.asList(endGameChargeStationRobot1, endGameChargeStationRobot2,
                         endGameChargeStationRobot3),
           isBridgeLevel(endgameBridgeState), endGameParkPoints, endGameChargeStationPoints, links,
           linkPoints, sustainabilityBonusAchieved, activationBonus, totalChargeStationPoints,
           coopPieceCount, coopertitionCriteriaMet, foulCount, techFoulCount, foulPoints,
           adjustPoints, totalPoints, rp);
    }

    private static boolean isBridgeLevel(String bridgeState) {
      return "Level".equals(bridgeState);
    }
  }

  public record Crescendo2024(List<Boolean> autoLines,
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
      STAGE_LEFT("StageLeft"),
      STAGE_RIGHT("StageRight"),
      STAGE_CENTER("CenterStage");

      @JsonValue
      private final String id;

      EndgameState(String value) {
        this.id = value;
      }

      public boolean isOnstage() {
        return ordinal() >= 2;
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
      this(Arrays.asList(booleanFromYesNo(autoLineRobot1), booleanFromYesNo(autoLineRobot2),
                         booleanFromYesNo(autoLineRobot3)),
           autoSpeakerNoteCount, autoAmpNoteCount, autoLeavePoints, autoSpeakerNotePoints,
           autoAmpNotePoints, autoTotalNotePoints, autoPoints, teleopAmpNoteCount,
           teleopSpeakerNoteCount, teleopSpeakerNoteAmplifiedCount, teleopAmpNotePoints,
           teleopSpeakerNotePoints, teleopSpeakerNoteAmplifiedPoints, teleopTotalNotePoints,
           teleopPoints, trapCenterStage, trapStageLeft, trapStageRight, micCenterStage,
           micStageLeft, micStageRight, Arrays.asList(endGameRobot1, endGameRobot2, endGameRobot3),
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
