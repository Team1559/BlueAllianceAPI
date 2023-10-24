package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Team {
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

    public static Endpoint<List<Integer>> endpoint(String teamKey) {
      return Endpoint.forList("/team/" + teamKey + "/years_participated", Integer.class);
    }
  }

  public static final class Simple {
    @JsonProperty("key")
    private String key;

    @JsonProperty("team_number")
    private int number;

    @JsonProperty("nickname")
    private String name;

    @JsonProperty("name")
    private String fullName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state_prov")
    private String province;

    @JsonProperty("country")
    private String country;

    public Simple() {}

    public String getKey() {
      return key;
    }

    public int getNumber() {
      return number;
    }

    public String getName() {
      return name;
    }

    public String getFullName() {
      return fullName;
    }

    public String getCity() {
      return city;
    }

    public String getProvince() {
      return province;
    }

    public String getCountry() {
      return country;
    }

    @Override
    public int hashCode() {
      return Objects.hash(key, number, name, fullName, city, province, country);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Simple)) return false;
      Simple other = (Simple) obj;
      return Objects.equals(key, other.key) && number == other.number
          && Objects.equals(name, other.name) && Objects.equals(fullName, other.fullName)
          && Objects.equals(city, other.city) && Objects.equals(province, other.province)
          && Objects.equals(country, other.country);
    }

    public static Endpoint<Team.Simple> endpoint(String teamKey) {
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

  public static final class Robot {
    @JsonProperty("year")
    private int year;

    @JsonProperty("robot_name")
    private String name;

    @JsonProperty("key")
    private String key;

    @JsonProperty("team_key")
    private String teamKey;

    public Robot() {}

    public int getYear() {
      return year;
    }

    public String getName() {
      return name;
    }

    public String getKey() {
      return key;
    }

    public String getTeamKey() {
      return teamKey;
    }

    @Override
    public int hashCode() {
      return Objects.hash(year, name, key, teamKey);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Robot)) return false;
      Robot other = (Robot) obj;
      return year == other.year && Objects.equals(name, other.name)
          && Objects.equals(key, other.key) && Objects.equals(teamKey, other.teamKey);
    }

    public static Endpoint<List<Robot>> endpointForTeam(String teamKey) {
      return Endpoint.forList("/team/" + teamKey + "/robots", Robot.class);
    }
  }

  @JsonProperty("key")
  private String key;

  @JsonProperty("team_number")
  private int number;

  @JsonProperty("nickname")
  private String name;

  @JsonProperty("name")
  private String fullName;

  @JsonProperty("school_name")
  private String school;

  @JsonProperty("city")
  private String city;

  @JsonProperty("state_prov")
  private String province;

  @JsonProperty("country")
  private String country;

  @JsonProperty("postal_code")
  private String postalCode;

  @JsonProperty("website")
  private String website;

  @JsonProperty("rookie_year")
  private int rookieYear;

  public Team() {}

  public String getKey() {
    return key;
  }

  public int getNumber() {
    return number;
  }

  public String getName() {
    return name;
  }

  public String getFullName() {
    return fullName;
  }

  public String getSchool() {
    return school;
  }

  public String getCity() {
    return city;
  }

  public String getProvince() {
    return province;
  }

  public String getCountry() {
    return country;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getWebsite() {
    return website;
  }

  public int getRookieYear() {
    return rookieYear;
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, number, name, fullName, school, city, province, country, postalCode,
                        website, rookieYear);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Team)) return false;
    Team other = (Team) obj;
    return Objects.equals(key, other.key) && number == other.number
        && Objects.equals(name, other.name) && Objects.equals(fullName, other.fullName)
        && Objects.equals(school, other.school) && Objects.equals(city, other.city)
        && Objects.equals(province, other.province) && Objects.equals(country, other.country)
        && Objects.equals(postalCode, other.postalCode) && Objects.equals(website, other.website)
        && rookieYear == other.rookieYear;
  }

  public static Endpoint<Team> endpoint(String teamKey) {
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
