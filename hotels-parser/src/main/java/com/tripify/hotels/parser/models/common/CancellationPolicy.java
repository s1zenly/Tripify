package com.tripify.hotels.parser.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancellationPolicy {
    private boolean refundable;
    private Instant freeCancellationUntil;
    private Penalty cancelPenalty;
    private Penalty noShowPenalty;
}
