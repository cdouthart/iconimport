package com.discoverychurch.icon.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IconResponse {
    String number;
    String message;
    Statistics statistics;
    Permissions permissions;
    String session;
    String role;
    int status;

    // this is optional
    @JsonProperty("relationships")
    SimpleId[] ids;

    @JsonProperty("householdindex")
    HouseholdIndex[] householdIndices;

}

