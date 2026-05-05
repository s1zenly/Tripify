package com.tripify.hotels.service.model.documents;

import java.time.Instant;

public record CancellationPolicyDocument(
        Boolean refundable,
        Instant freeCancellationUntil,
        CancelPenaltyDocument cancelPenalty,
        CancelPenaltyDocument noShowPenalty
) {
}
