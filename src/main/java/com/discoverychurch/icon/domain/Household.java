package com.discoverychurch.icon.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Household {
    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    @JsonProperty("address_1")
    String address;
    String city;
    String state;
    String zip;

    @JsonProperty("user_defined_1")
    String carePartner;
    Phone[] phones;
    Email[] emails;
    @JsonProperty("special_dates")
    Date[] specialDates;

    String email;
    String phone;

}
