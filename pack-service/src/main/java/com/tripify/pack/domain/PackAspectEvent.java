package com.tripify.pack.domain;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;

public record PackAspectEvent(
        String eventId,
        UserType userType,
        String userId,
        String anonymousId,
        String generationId,
        GenerationMode generationMode,
        int packRevisionId,
        AspectType aspectType,
        int aspectRevisionId,
        SearchContext searchContext,
        JsonNode payload,
        Instant occurredAt,
        KafkaEventMetadata kafkaMetadata
) {
    public String subjectId() {
        if (userType == UserType.AUTH) {
            if (userId == null || userId.isBlank()) {
                throw new IllegalStateException("user_id is required for AUTH events");
            }
            return userId;
        }
        if (anonymousId == null || anonymousId.isBlank()) {
            throw new IllegalStateException("anonymous_id is required for ANONYMOUS events");
        }
        return anonymousId;
    }
}
