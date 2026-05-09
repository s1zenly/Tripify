package com.tripify.auth.service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JsonUtils {

    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public static <MODEL> String toJson(MODEL payload) {
        try {
            return DEFAULT_OBJECT_MAPPER.writeValueAsString(payload);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Failed to serialize model payload", exception);
        }
    }
}
