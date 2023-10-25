package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
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

  public final Type                type;
  public final String              foreignKey;
  public final Map<String, String> details;
  public final boolean             highQuality;
  public final String              directURL;
  public final String              viewURL;

  @JsonCreator
  Media(@JsonProperty("type") Type type, @JsonProperty("foreign_key") String foreignKey,
                @JsonProperty("details") Map<String, String> details,
                @JsonProperty("preferred") boolean highQuality,
                @JsonProperty("direct_url") String directURL,
                @JsonProperty("view_url") String viewURL) {
    this.type = type;
    this.foreignKey = foreignKey;
    this.details = details == null ? null : Map.copyOf(details);
    this.highQuality = highQuality;
    this.directURL = directURL;
    this.viewURL = viewURL;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Media [type=")
           .append(type)
           .append(", foreignKey=")
           .append(foreignKey)
           .append(", details=")
           .append(details)
           .append(", highQuality=")
           .append(highQuality)
           .append(", directURL=")
           .append(directURL)
           .append(", viewURL=")
           .append(viewURL)
           .append("]");
    return builder.toString();
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
