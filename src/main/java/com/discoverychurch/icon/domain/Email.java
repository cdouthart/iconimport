package com.discoverychurch.icon.domain;

import lombok.Data;
import org.boon.json.annotations.JsonProperty;

@Data
public class Email {
    String id;
    String email;

    @JsonProperty("email_unlisted")
    boolean unlisted = false;

}
