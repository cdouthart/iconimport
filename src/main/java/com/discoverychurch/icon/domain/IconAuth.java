package com.discoverychurch.icon.domain;

import lombok.Builder;
import org.boon.json.annotations.JsonProperty;

@Builder
public class IconAuth {
    @JsonProperty("Session")
    private String session;
}
