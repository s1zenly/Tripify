package com.tripify.auth.service.domain.model;

import java.time.Instant;
import java.util.UUID;

import com.tripify.auth.service.domain.enums.OtpPurpose;
import com.tripify.auth.service.domain.enums.OtpStatus;

public record OtpRequest(
        UUID id,
        String phone,
        String otpHash,
        OtpPurpose purpose,
        OtpStatus status,
        int attempts,
        Instant expiresAt,
        Instant createdAt,
        Instant updatedAt
) {
}
