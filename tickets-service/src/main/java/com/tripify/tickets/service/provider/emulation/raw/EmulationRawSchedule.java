package com.tripify.tickets.service.provider.emulation.raw;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record EmulationRawSchedule(
        OffsetDateTime at,
        String tz
) {
}
