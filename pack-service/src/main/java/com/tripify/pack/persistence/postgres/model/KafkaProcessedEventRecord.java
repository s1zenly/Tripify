package com.tripify.pack.persistence.postgres.model;

import java.time.Instant;
import java.util.UUID;

public record KafkaProcessedEventRecord(
        UUID id,
        String topic,
        int kafkaPartition,
        long kafkaOffset,
        String eventId,
        String subjectId,
        String generationId,
        int packRevisionId,
        Instant createdAt
) {
}
