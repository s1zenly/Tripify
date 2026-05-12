package com.tripify.notification.service.model;

import java.time.Instant;

public record DlqMessage(
        Object payload,
        String errorMessage,
        Instant failedAt
) {
}
