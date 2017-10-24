package com.discoverychurch.icon.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
//@AllArgsConstructor
public class Statistics {

    int records;

    @JsonProperty("startat")
    int startAt;

    int limit;

    @JsonProperty("records_created")
    String recordsCreated;

    @JsonProperty("last_id")
    String lastId;
}


/*
{
  "statistics": {
          "records_created": 1
          ,"last_id": 1048
          }
          ,"permissions": {
          "create": true
          ,"read": true
          ,"update": true
          ,"delete": true
          }
          ,"session": "589fsnpb9f4fb9gtgiptcgku24"
          ,"role": "staff"
          }
          */