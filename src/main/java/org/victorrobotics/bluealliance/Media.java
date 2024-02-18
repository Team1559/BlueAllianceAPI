package org.victorrobotics.bluealliance;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public record Media(@JsonProperty("type") Type type,
                    @JsonProperty("foreign_key") String foreignKey,
                    @JsonProperty("details") Map<String, String> details,
                    @JsonProperty("preferred") boolean highQuality,
                    @JsonProperty("direct_url") String directURL,
                    @JsonProperty("view_url") String viewURL) {
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
