package com.tripify.users.service.model;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tripify.users.service.exception.app.InvalidUserRegisteredEventException;

public record UserRegisteredEvent(
        @JsonProperty("userId") UUID userId,
        @JsonProperty("phoneNumber") String phoneNumber,
        @JsonProperty("registeredAt") Instant registeredAt
) {

    public void validate() {
        if (userId == null) {
            throw new InvalidUserRegisteredEventException("userId is required");
        }

        if (phoneNumber == null || phoneNumber().isBlank()) {
            throw new InvalidUserRegisteredEventException("phoneNumber is required");
        }

        if (registeredAt == null) {
            throw new InvalidUserRegisteredEventException("registeredAt is required");
        }

        if (registeredAt.isAfter(Instant.now())) {
            throw new InvalidUserRegisteredEventException("registeredAt is invalid");
        }
    }
}
