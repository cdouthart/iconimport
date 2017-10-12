package com.discoverychurch.icon.domain;

import lombok.Builder;
import org.boon.json.annotations.JsonProperty;

@Builder
public class IconEnvelope {
    @JsonProperty("Auth")
    private IconAuth auth;

    @JsonProperty("Request")
    private IconRequest request;
}
