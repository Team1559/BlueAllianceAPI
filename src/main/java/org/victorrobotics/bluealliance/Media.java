package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public final class Media {
  public enum Type {
    YOUTUBE("youtube"),
    CHIEF_DELPHI("cdphotothread"),
    IMGUR("imgur"),
    FACEBOOK("facebook-profile"),
    YOUTUBE_CHANNEL("youtube-channel"),
    TWITTER("twitter-profile"),
    GITHUB("github-profile"),
    INSTAGRAM("instagram-profile"),
    PERISCOPE("periscope-profile"),
    GITLAB("gitlab-profile"),
    GRABCAD("grabcad"),
    INSTAGRAM_IMAGE("instagram-image"),
    LINK("external-link"),
    AVATAR("avatar"),

    @JsonEnumDefaultValue
    OTHER("???");

    @JsonValue
    private final String id;

    Type(String id) {
      this.id = id;
    }
  }

  @JsonProperty("type")
  private Type type;

  @JsonProperty("foreign_key")
  private String foreignKey;

  @JsonProperty("details")
  private Map<String, String> details;

  @JsonProperty("preferred")
  private boolean highQuality;

  @JsonProperty("direct_url")
  private String directURL;

  @JsonProperty("view_url")
  private String viewURL;

  public Media() {}

  public Type getType() {
    return type;
  }

  public String getForeignKey() {
    return foreignKey;
  }

  public Map<String, String> getDetails() {
    return details;
  }

  public boolean isHighQuality() {
    return highQuality;
  }

  public String getDirectURL() {
    return directURL;
  }

  public String getViewURL() {
    return viewURL;
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, foreignKey, details, highQuality, directURL, viewURL);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Media)) return false;
    Media other = (Media) obj;
    return type == other.type && Objects.equals(foreignKey, other.foreignKey)
        && Objects.equals(details, other.details) && highQuality == other.highQuality
        && Objects.equals(directURL, other.directURL) && Objects.equals(viewURL, other.viewURL);
  }

  public static Endpoint<List<Media>> endpointForSocialMedia(String teamKey) {
    return Endpoint.forList("/team/" + teamKey + "/social_media", Media.class);
  }

  public static Endpoint<List<Media>> endpointForTeam(String teamKey, int year) {
    return Endpoint.forList("/team/" + teamKey + "/media/" + year, Media.class);
  }

  public static Endpoint<List<Media>> endpointForTag(String teamKey, String tag) {
    return Endpoint.forList("/team/" + teamKey + "/media/tag/" + tag, Media.class);
  }

  public static Endpoint<List<Media>> endpointForTag(String teamKey, String tag, int year) {
    return Endpoint.forList("/team/" + teamKey + "/media/tag/" + tag + "/" + year, Media.class);
  }
}
