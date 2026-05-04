package com.tripify.hotels.service.model.outbox;

import java.time.Instant;
import java.util.UUID;

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
