package com.tripify.tickets.service.model.unified;

import lombok.Builder;

@Builder
public record Segment(
        String segmentId,
        LocationPoint origin,
        LocationPoint destination,
        SchedulePoint departure,
        SchedulePoint arrival,
        int durationMinutes,
        Airline marketingAirline,
        Airline operatingAirline,
        String flightNumber,
        String aircraft,
        CabinClass cabinClass
) {
}
