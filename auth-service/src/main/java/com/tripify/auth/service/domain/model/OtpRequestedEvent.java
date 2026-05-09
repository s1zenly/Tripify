package com.tripify.auth.service.domain.model;

import java.time.Instant;

public record OtpRequestedEvent(
        String phone,
        String code,
        Instant createdAt,
        Instant expiresAt
) {
}
