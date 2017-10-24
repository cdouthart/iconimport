package com.discoverychurch.icon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Permissions {
    boolean create;
    boolean read;
    boolean update;
    boolean delete;
}


