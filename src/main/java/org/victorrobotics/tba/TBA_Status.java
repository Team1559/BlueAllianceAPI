package org.victorrobotics.tba;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TBA_Status(@JsonProperty("current_season") int currentSeason,
                         @JsonProperty("max_season") int maxSeason,
                         @JsonProperty("is_datafeed_down") boolean isDatafeedDown,
                         @JsonProperty("down_events") Set<String> downEvents) {}
