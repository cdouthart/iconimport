package com.discoverychurch.icon.domain;

import org.boon.json.annotations.JsonProperty;

public class Member {
    @JsonProperty("household_id")
    String householdId;

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    String gender;

    String relationship;

    @JsonProperty("special_dates")
    Date[] specialDates;

    Phone[] phones;

}
