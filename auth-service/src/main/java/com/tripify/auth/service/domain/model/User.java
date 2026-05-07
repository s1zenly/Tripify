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
    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }

    public boolean isBlocked() {
        return status == UserStatus.BLOCKED;
    }

    public boolean isDeleted() {
        return status == UserStatus.DELETED;
    }
}
