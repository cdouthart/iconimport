package com.discoverychurch.icon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleId<T> {
    private T id;
}
