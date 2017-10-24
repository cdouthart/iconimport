package com.discoverychurch.icon.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class IconRequest<T> {
    @JsonProperty("DataFormat")
    private String dataFormat = "JSON";

    @JsonProperty("Module")
    private String module;

    @JsonProperty("Section")
    private String section;

    @JsonProperty("Action")
    private String action;

    @JsonProperty("Filters")
    private Object filters;

    @JsonProperty("Data")
    private T data;



}
