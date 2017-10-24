package com.discoverychurch.icon.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HouseholdIndex {
    private String id;
    private String status;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
}

// {"id":"4","status":"Active","first_name":"Alan","last_name":"Bancroft"}