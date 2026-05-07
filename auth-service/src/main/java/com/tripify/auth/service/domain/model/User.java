package com.tripify.auth.service.domain.model;

import java.time.Instant;
import java.util.UUID;

import com.tripify.auth.service.domain.enums.UserStatus;

public record User(
        UUID id,
        String phone,
        UserStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}
