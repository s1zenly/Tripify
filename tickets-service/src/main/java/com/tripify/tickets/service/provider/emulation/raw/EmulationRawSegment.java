package com.tripify.tickets.service.provider.emulation.raw;

import lombok.Builder;

@Builder
public record EmulationRawSegment(
        String id,
        EmulationRawPoint from,
        EmulationRawPoint to,
        EmulationRawSchedule dep,
        EmulationRawSchedule arr,
        int durationMin,
        EmulationRawCarrier marketing,
        EmulationRawCarrier operating,
        String flightNo,
        String plane,
        String cabin
) {
}
