package com.discoverychurch.icon.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Email {
    String id;
    String email;

    @JsonProperty("email_unlisted")
    boolean unlisted = false;

}
