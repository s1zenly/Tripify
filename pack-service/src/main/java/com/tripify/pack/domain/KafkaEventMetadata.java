package com.tripify.pack.domain;

public record KafkaEventMetadata(
        String topic,
        int kafkaPartition,
        long kafkaOffset,
        String eventId
) {
}
