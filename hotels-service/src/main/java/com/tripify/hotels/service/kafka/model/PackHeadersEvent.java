package com.tripify.hotels.service.kafka.model;

public record PackHeadersEvent(
        UserType userType,
        String userId,
        String anonymousId,
        String generationId,
        int packRevision,
        GenerationMode generationMode,
        String requestId,
        Integer serviceRevision
) {
}
