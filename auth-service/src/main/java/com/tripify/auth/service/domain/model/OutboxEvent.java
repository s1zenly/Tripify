package com.tripify.auth.service.domain.model;

import java.time.Instant;
import java.util.UUID;

import com.tripify.auth.service.domain.enums.AggregateType;
import com.tripify.auth.service.domain.enums.OutboxEventStatus;
import com.tripify.auth.service.domain.enums.OutboxEventType;

public record OutboxEvent(
        UUID id,
        AggregateType aggregateType,
        String aggregateId,
        OutboxEventType eventType,
        String topic,
        String payload,
        OutboxEventStatus status,
        int attempts,
        String errorMessage,
        Instant createdAt,
        Instant updatedAt,
        Instant publishedAt
) {
}
