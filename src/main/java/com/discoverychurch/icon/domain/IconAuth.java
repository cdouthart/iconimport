package com.discoverychurch.icon.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
public class IconAuth {

    @JsonProperty("Session")
    private String session;

    @JsonProperty("Username")
    private String user = "Discoverymigration"; //"Discnnp";

    @JsonProperty("Password")
    private String password="OhCh5yailahSeeJ4UCei";//"qmhnrc3h";

    @JsonProperty("Phone")
    private String phone="9195537256";
}
