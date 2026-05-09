package com.tripify.auth.service.domain.model;

import java.util.UUID;

public record CreateOtpResult(UUID id, String rawOtp) {
}
