package com.tripify.tickets.service.provider.emulation.raw;

import lombok.Builder;

import java.util.List;

@Builder
public record EmulationRawLeg(
        String direction,
        EmulationRawPoint from,
        EmulationRawPoint to,
        EmulationRawSchedule dep,
        EmulationRawSchedule arr,
        int durationMin,
        int stops,
        boolean overnight,
        boolean airportChange,
        boolean selfTransfer,
        List<EmulationRawSegment> segments
) {
}
