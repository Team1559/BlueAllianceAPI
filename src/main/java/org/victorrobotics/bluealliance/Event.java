package org.victorrobotics.bluealliance;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public record Event(@JsonProperty("key") String key,
                    @JsonProperty("name") String name,
                    @JsonProperty("event_type") Event.Type type,
                    @JsonProperty("district") DistrictList district,
                    @JsonProperty("city") String city,
                    @JsonProperty("state_prov") String stateProv,
                    @JsonProperty("country") String country,
                    @JsonProperty("start_date") @JsonFormat(pattern = "yyyy-mm-dd") Date startDate,
                    @JsonProperty("end_date") @JsonFormat(pattern = "yyyy-mm-dd") Date endDate,
                    @JsonProperty("year") int year,
                    @JsonProperty("week") Integer week,
                    @JsonProperty("location_name") String locationName,
                    @JsonProperty("timezone") String timezone,
                    @JsonProperty("website") String website,
                    @JsonProperty("division_keys") List<String> divisionKeys,
                    @JsonProperty("parent_event_key") String parentEventKey,
                    @JsonProperty("playoff_type") Event.Playoff playoffType) {
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
    UNLABLED(-1);

    @JsonValue
    private final int code;

    Type(int code) {
      this.code = code;
    }
  }

  public enum Playoff {
    ELIM_8(0, "Elimination Bracket (8 Alliances)"),
    ELIM_16(1, "Elimination Bracket (16 Alliances)"),
    ELIM_4(2, "Elimination Bracket (4 Alliances)"),

    AVG_SCORE_8(3, "Average Score (8 Alliances)"),
    ROUND_ROBIN_6(4, "Round Robin (6 Alliances)"),
    DOUBLE_ELIM_8(5, "Double Elimination Bracket (8 Alliances)"),

    BEST_5_FINALS(6, "Best of 5 Finals"),
    BEST_3_FINALS(7, "Best of 3 Finals"),

    CUSTOM(8, "Custom"),

    ELIM_2(9, "Elimination Bracket (2 Alliances)"),
    DOUBLE_ELIM_8_NEW(10, "Double Elimination Bracket (8 Alliances)"),
    DOUBLE_ELIM_4(11, "Double Elimination Bracket (4 Alliances)");

    @JsonValue
    public final int id;

    public final String value;

    Playoff(int id, String value) {
      this.id = id;
      this.value = value;
    }
  }

  public static Endpoint<Event> endpoint(String eventKey) {
    return Endpoint.forSingle("/event/" + eventKey, Event.class);
  }

  public static Endpoint<List<Event>> endpointForYear(int year) {
    return Endpoint.forList("/events/" + year, Event.class);
  }
}
