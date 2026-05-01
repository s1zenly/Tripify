package com.tripify.tickets.service.model.unified;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum CabinClass {
    ECONOMY("economy"),
    PREMIUM_ECONOMY("premium_economy"),
    BUSINESS("business"),
    FIRST("first");

    private final String value;

    CabinClass(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CabinClass fromValue(String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(cabin -> cabin.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown cabin class: " + value));
    }
}
