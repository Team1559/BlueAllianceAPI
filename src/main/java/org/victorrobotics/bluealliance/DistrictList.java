package org.victorrobotics.bluealliance;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DistrictList(@JsonProperty("key") String key,
                           @JsonProperty("abbrevitation") String abbrevitation,
                           @JsonProperty("display_name") String displayName,
                           @JsonProperty("year") int year) {}
