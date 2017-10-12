package com.discoverychurch.icon.domain;

import lombok.Data;

@Data
public class Household {
    String first_name;
    String last_name;
    String city;
    String state;
    Phone[] phones;
    Email[] emails;

}
