package com.discoverychurch.icon.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class IconEnvelope {
    @JsonProperty("Auth")
    private IconAuth auth;

    @JsonProperty("Request")
    private IconRequest request;
}
