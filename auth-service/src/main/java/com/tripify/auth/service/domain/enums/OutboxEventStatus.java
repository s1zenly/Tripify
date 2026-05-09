package com.tripify.auth.service.domain.enums;

public enum OutboxEventStatus {
    PENDING,
    PUBLISHED,
    FAILED,
    SKIPPED,
}
