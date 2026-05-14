package com.tripify.pack.kafka.model;

import com.tripify.pack.domain.GenerationMode;
import com.tripify.pack.domain.UserType;

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
