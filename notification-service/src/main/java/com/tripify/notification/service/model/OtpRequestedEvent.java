package com.tripify.notification.service.model;

import java.time.Instant;

public record OtpRequestedEvent(
        String requestId,
        String phone,
        String code,
        Instant createdAt,
        Instant expiresAt
) {
}
