package com.tripify.tickets.service.kafka.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;

public record PackViewEvent(
        Instant occurredAt,
        PackHeadersEvent headers,
        SearchContextEvent searchContext,
        JsonNode entity
) {
}
