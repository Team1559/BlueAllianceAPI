package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public final class Award {
  public static final class Recipient {
    public final String teamKey;
    public final String awardee;

    @JsonCreator
    Recipient(@JsonProperty("team_key") String teamKey, @JsonProperty("awardee") String awardee) {
      this.teamKey = teamKey;
      this.awardee = awardee;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Recipient [teamKey=")
             .append(teamKey)
             .append(", awardee=")
             .append(awardee)
             .append("]");
      return builder.toString();
    }

    @Override
    public int hashCode() {
      return Objects.hash(teamKey, awardee);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Recipient)) return false;
      Recipient other = (Recipient) obj;
      return Objects.equals(teamKey, other.teamKey) && Objects.equals(awardee, other.awardee);
    }
  }

  public enum Type {
    CHAIRMANS(0),
    WINNER(1),
    FINALIST(2),

    WOODIE_FLOWERS(3),
    DEANS_LIST(4),
    VOLUNTEER(5),
    FOUNDERS(6),
    BART_KAMEN_MEMORIAL(7),
    MAKE_IT_LOUD(8),

    ENGINEERING_INSPIRATION(9),
    ROOKIE_ALL_STAR(10),
    GRACIOUS_PROFESSIONALISM(11),
    COOPERTITION(12),
    JUDGES(13),
    HIGHEST_ROOKIE_SEED(14),
    ROOKIE_INSPIRATION(15),
    INDUSTRIAL_DESIGN(16),
    QUALITY(17),
    SAFETY(18),
    SPORTSMANSHIP(19),
    CREATIVITY(20),
    ENGINEERING_EXCELLENCE(21),
    ENTREPRENEURSHIP(22),
    EXCELLENCE_IN_DESIGN(23),
    EXCELLENCE_IN_DESIGN_CAD(24),
    EXCELLENCE_IN_DESIGN_ANIMATION(25),
    DRIVING_TOMORROWS_TECHNOLOGY(26),
    IMAGERY(27),
    MEDIA_AND_TECHNOLOGY(28),
    INNOVATION_IN_CONTROL(29),
    SPIRIT(30),
    WEBSITE(31),
    VISUALIZATION(32),
    AUTODESK_INVENTOR(33),
    FUTURE_INNOVATOR(34),
    RECOGNITION_OF_EXTRAORDINARY_SERVICE(35),
    OUTSTANDING_CART(36),
    WSU_AIM_HIGHER(37),
    LEADERSHIP_IN_CONTROL(38),
    NUM_1_SEED(39),
    INCREDIBLE_PLAY(40),
    PEOPLES_CHOICE_ANIMATION(41),
    VISUALIZATION_RISING_STAR(42),
    BEST_OFFENSIVE_ROUND(43),
    BEST_PLAY_OF_THE_DAY(44),
    FEATHERWEIGHT_IN_THE_FINALS(45),
    MOST_PHOTOGENIC(46),
    OUTSTANDING_DEFENSE(47),
    POWER_TO_SIMPLIFY(48),
    AGAINST_ALL_ODDS(49),
    RISING_STAR(50),
    CHAIRMANS_HONORABLE_MENTION(51),
    CONTENT_COMMUNICATION_HONORABLE_MENTION(52),
    TECHNICAL_EXECUTION_HONORABLE_MENTION(53),
    REALIZATION(54),
    REALIZATION_HONORABLE_MENTION(55),
    DESIGN_YOUR_FUTURE(56),
    DESIGN_YOUR_FUTURE_HONORABLE_MENTION(57),
    SPECIAL_RECOGNITION_CHARACTER_ANIMATION(58),
    HIGH_SCORE(59),
    TEACHER_PIONEER(60),
    BEST_CRAFTSMANSHIP(61),
    BEST_DEFENSIVE_MATCH(62),
    PLAY_OF_THE_DAY(63),
    PROGRAMMING(64),
    PROFESSIONALISM(65),
    GOLDEN_CORNDOG(66),
    MOST_IMPROVED_TEAM(67),
    WILDCARD(68),
    CHAIRMANS_FINALIST(69),
    OTHER(70),
    AUTONOMOUS(71),
    INNOVATION_CHALLENGE_SEMI_FINALIST(72),
    ROOKIE_GAME_CHANGER(73),
    SKILLS_COMPETITION_WINNER(74),
    SKILLS_COMPETITION_FINALIST(75),
    ROOKIE_DESIGN(76),
    ENGINEERING_DESIGN(77),
    DESIGNERS(78),
    CONCEPT(79),
    GAME_DESIGN_CHALLENGE_WINNER(80),
    GAME_DESIGN_CHALLENGE_FINALIST(81),

    @JsonEnumDefaultValue
    CUSTOM(-1);

    @JsonValue
    private final int id;

    Type(int id) {
      this.id = id;
    }
  }

  public final String          name;
  public final Type            type;
  public final String          eventKey;
  public final List<Recipient> recipients;
  public final int             year;

  @JsonCreator
  Award(@JsonProperty("name") String name, @JsonProperty("award_type") Type type,
        @JsonProperty("event_key") String eventKey,
        @JsonProperty("recipient_list") List<Recipient> recipients,
        @JsonProperty("year") int year) {
    this.name = name;
    this.type = type;
    this.eventKey = eventKey;
    this.recipients = recipients == null ? null : List.copyOf(recipients);
    this.year = year;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Award [name=")
           .append(name)
           .append(", type=")
           .append(type)
           .append(", eventKey=")
           .append(eventKey)
           .append(", recipients=")
           .append(recipients)
           .append(", year=")
           .append(year)
           .append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, eventKey, recipients, year);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Award)) return false;
    Award other = (Award) obj;
    return Objects.equals(name, other.name) && type == other.type
        && Objects.equals(eventKey, other.eventKey) && Objects.equals(recipients, other.recipients)
        && year == other.year;
  }

  public static Endpoint<List<Award>> endpointForEvent(String eventKey) {
    return Endpoint.forList("/event/" + eventKey + "/awards", Award.class);
  }

  public static Endpoint<List<Award>> endpointForTeam(String teamKey) {
    return Endpoint.forList("/team/" + teamKey + "/awards", Award.class);
  }

  public static Endpoint<List<Award>> endpointForTeam(String teamKey, int year) {
    return Endpoint.forList("/team/" + teamKey + "/awards/" + year, Award.class);
  }

  public static Endpoint<List<Award>> endpointForTeam(String teamKey, String eventKey) {
    return Endpoint.forList("/team/" + teamKey + "/event/" + eventKey + "/awards", Award.class);
  }
}
