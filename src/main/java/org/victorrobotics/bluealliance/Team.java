package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Team {
  @JsonProperty
  private String key;

  @JsonProperty("team_number")
  private int number;

  @JsonProperty("nickname")
  private String name;

  @JsonProperty("name")
  private String fullName;

  @JsonProperty("school_name")
  private String school;

  @JsonProperty
  private String city;

  @JsonProperty("state_prov")
  private String province;

  @JsonProperty
  private String country;

  @JsonProperty("postal_code")
  private String postalCode;

  @JsonProperty
  private String website;

  @JsonProperty("rookie_year")
  private int rookieYear;

  public static class Robot {
    @JsonProperty
    private int year;

    @JsonProperty("robot_name")
    private String name;

    @JsonProperty
    private String key;

    @JsonProperty("team_key")
    private String teamKey;

    public static Endpoint<List<Robot>> endpointForTeam(String teamKey) {
      return Endpoint.forList("/team/" + teamKey + "/robots", Robot.class);
    }
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Team [key=")
           .append(key)
           .append(", number=")
           .append(number)
           .append(", name=")
           .append(name)
           .append(", fullName=")
           .append(fullName)
           .append(", school=")
           .append(school)
           .append(", city=")
           .append(city)
           .append(", province=")
           .append(province)
           .append(", country=")
           .append(country)
           .append(", postalCode=")
           .append(postalCode)
           .append(", website=")
           .append(website)
           .append(", rookieYear=")
           .append(rookieYear)
           .append("]");
    return builder.toString();
  }

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
}
