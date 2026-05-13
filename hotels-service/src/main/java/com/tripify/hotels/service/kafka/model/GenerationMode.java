package com.tripify.hotels.service.kafka.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

public enum GenerationMode {
    FULL,
    HOTELS;

    @JsonCreator
    public static GenerationMode fromValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("generation_mode is required");
        }
        return valueOf(value.trim().toUpperCase(Locale.ROOT));
    }
}
