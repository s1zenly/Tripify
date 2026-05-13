package com.tripify.info.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CountryInfoSectionType {
    TEXT("text"),
    FACTS("facts"),
    TABLE("table"),
    LIST("list");

    private final String value;

    CountryInfoSectionType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
