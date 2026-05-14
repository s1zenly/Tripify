package com.tripify.pack.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

public enum GenerationMode {
    FULL,
    HOTELS,
    TICKETS;

    @JsonCreator
    public static GenerationMode fromValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("generationMode is required");
        }
        return valueOf(value.trim().toUpperCase(Locale.ROOT));
    }
}
