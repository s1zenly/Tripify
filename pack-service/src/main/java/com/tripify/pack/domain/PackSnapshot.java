package com.tripify.pack.domain;

import com.fasterxml.jackson.databind.JsonNode;

public record PackSnapshot(
        UserType userType,
        String userId,
        String anonymousId,
        String generationId,
        int packRevisionId,
        GenerationMode generationMode,
        int hotelRevisionId,
        int ticketRevisionId,
        JsonNode hotel,
        JsonNode ticket,
        SearchContext searchContext
) {
    public String subjectId() {
        if (userType == UserType.AUTH) {
            if (userId == null || userId.isBlank()) {
                throw new IllegalStateException("user_id is required for AUTH pack snapshots");
            }
            return userId;
        }
        if (anonymousId == null || anonymousId.isBlank()) {
            throw new IllegalStateException("anonymous_id is required for ANONYMOUS pack snapshots");
        }
        return anonymousId;
    }
}
