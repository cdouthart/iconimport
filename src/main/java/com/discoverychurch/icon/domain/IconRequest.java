package com.discoverychurch.icon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.boon.json.annotations.JsonProperty;

@Getter @Setter @Builder
public class IconRequest<T> {

    @JsonProperty("Module")
    private String module;

    @JsonProperty("Section")
    private String section;

    @JsonProperty("Action")
    private String action;

    @JsonProperty("Data")
    private T data;


}
