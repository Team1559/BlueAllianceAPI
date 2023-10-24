package org.victorrobotics.bluealliance;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class Event {
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

  public enum PlayoffType {
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

    PlayoffType(int id, String value) {
      this.id = id;
      this.value = value;
    }
  }

  public static class Webcast {
    public enum Type {
      YOUTUBE("youtube"),
      TWITCH("twitch"),
      USTREAM("ustream"),
      IFRAME("iframe"),
      HTML5("html5"),
      RTMP("rtmp"),
      LIVESTREAM("livestream"),
      DIRECT_LINK("direct_link"),
      MMS("mms"),
      JUSTIN("justin"),
      STEM_TV("stemtv"),
      DACAST("dacast");

      @JsonValue
      private final String id;

      Type(String id) {
        this.id = id;
      }
    }

    @JsonProperty("type")
    private Type type;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date date;

    @JsonProperty("file")
    private String file;

    public Webcast() {}

    public Type getType() {
      return type;
    }

    public String getChannel() {
      return channel;
    }

    public Date getDate() {
      return date;
    }

    public String getFile() {
      return file;
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, channel, date, file);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Webcast)) return false;
      Webcast other = (Webcast) obj;
      return type == other.type && Objects.equals(channel, other.channel)
          && Objects.equals(date, other.date) && Objects.equals(file, other.file);
    }
  }

  @JsonProperty("key")
  private String key;

  @JsonProperty("name")
  private String name;

  @JsonProperty("event_code")
  private String code;

  @JsonProperty("event_type")
  private Type type;

  @JsonProperty("district")
  private DistrictList district;

  @JsonProperty("city")
  private String city;

  @JsonProperty("state_prov")
  private String stateProv;

  @JsonProperty("country")
  private String country;

  @JsonProperty("start_date")
  @JsonFormat(pattern = "yyyy-mm-dd")
  private Date startDate;

  @JsonProperty("end_date")
  @JsonFormat(pattern = "yyyy-mm-dd")
  private Date endDate;

  @JsonProperty("year")
  private int year;

  @JsonProperty("short_name")
  private String shortName;

  @JsonProperty("event_type_string")
  private String typeString;

  @JsonProperty("week")
  private Integer week;

  @JsonProperty("address")
  private String address;

  @JsonProperty("postal_code")
  private String postalCode;

  @JsonProperty("gmaps_place_id")
  private String gmapsPlaceID;

  @JsonProperty("gmaps_url")
  private String gmapsURL;

  @JsonProperty("lat")
  private double latitude;

  @JsonProperty("lng")
  private double longitude;

  @JsonProperty("location_name")
  private String locationName;

  @JsonProperty("timezone")
  private String timezone;

  @JsonProperty("website")
  private String website;

  @JsonProperty("first_event_id")
  private String firstID;

  @JsonProperty("first_event_code")
  private String firstCode;

  @JsonProperty("webcasts")
  private List<Webcast> webcasts;

  @JsonProperty("division_keys")
  private List<String> divisionKeys;

  @JsonProperty("parent_event_key")
  private String parentEventKey;

  @JsonProperty("playoff_type")
  private PlayoffType playoffType;

  public Event() {}

  public String getKey() {
    return key;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public Type getType() {
    return type;
  }

  public DistrictList getDistrict() {
    return district;
  }

  public String getCity() {
    return city;
  }

  public String getStateProv() {
    return stateProv;
  }

  public String getCountry() {
    return country;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public int getYear() {
    return year;
  }

  public String getShortName() {
    return shortName;
  }

  public String getTypeString() {
    return typeString;
  }

  public Integer getWeek() {
    return week;
  }

  public String getAddress() {
    return address;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getGmapsPlaceID() {
    return gmapsPlaceID;
  }

  public String getGmapsURL() {
    return gmapsURL;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getLocationName() {
    return locationName;
  }

  public String getTimezone() {
    return timezone;
  }

  public String getWebsite() {
    return website;
  }

  public String getFirstID() {
    return firstID;
  }

  public String getFirstCode() {
    return firstCode;
  }

  public List<Webcast> getWebcasts() {
    return Collections.unmodifiableList(webcasts);
  }

  public List<String> getDivisionKeys() {
    return Collections.unmodifiableList(divisionKeys);
  }

  public String getParentEventKey() {
    return parentEventKey;
  }

  public PlayoffType getPlayoffType() {
    return playoffType;
  }

  @JsonGetter("playoff_type_string")
  private String getPlayoffTypeString() {
    return playoffType.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, name, type, district, city, stateProv, country, startDate, endDate,
                        year, week, locationName, timezone, website, divisionKeys, parentEventKey,
                        playoffType);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Event)) return false;
    Event other = (Event) obj;
    return Objects.equals(key, other.key) && Objects.equals(name, other.name) && type == other.type
        && Objects.equals(district, other.district) && Objects.equals(city, other.city)
        && Objects.equals(stateProv, other.stateProv) && Objects.equals(country, other.country)
        && Objects.equals(startDate, other.startDate) && Objects.equals(endDate, other.endDate)
        && year == other.year && Objects.equals(week, other.week)
        && Objects.equals(locationName, other.locationName)
        && Objects.equals(timezone, other.timezone) && Objects.equals(website, other.website)
        && Objects.equals(divisionKeys, other.divisionKeys)
        && Objects.equals(parentEventKey, other.parentEventKey) && playoffType == other.playoffType;
  }

  public static Endpoint<Event> endpoint(String eventKey) {
    return Endpoint.forSingle("/event/" + eventKey, Event.class);
  }

  public static Endpoint<List<Event>> endpointForYear(int year) {
    return Endpoint.forList("/events/" + year, Event.class);
  }
}