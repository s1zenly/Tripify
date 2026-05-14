package com.tripify.pack.persistence.postgres.model;

import com.tripify.pack.domain.FailureReason;
import com.tripify.pack.domain.GenerationMode;
import com.tripify.pack.domain.PackRevisionStatus;
import com.tripify.pack.domain.UserType;

import java.time.Instant;
import java.util.UUID;

public record PackRevisionRecord(
        UUID id,
        UserType userType,
        String subjectId,
        String userId,
        String anonymousId,
        String generationId,
        int packRevisionId,
        GenerationMode generationMode,
        PackRevisionStatus status,
        Integer hotelRevisionId,
        Integer ticketRevisionId,
        String hotelSnapshotId,
        String ticketSnapshotId,
        String packSnapshotId,
        FailureReason failureReason,
        Instant createdAt,
        Instant updatedAt
) {
}
