package com.tripify.hotels.service.kafka.model;

import java.time.Instant;

public record CancellationPolicyDto(
        Boolean refundable,
        Instant freeCancellationUntil,
        CancelPenaltyDto cancelPenalty,
        CancelPenaltyDto noShowPenalty
) {
}
