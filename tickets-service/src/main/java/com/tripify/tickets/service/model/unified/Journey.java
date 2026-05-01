package com.tripify.tickets.service.model.unified;

import lombok.Builder;

import java.util.List;

@Builder
public record Journey(
        JourneyType type,
        LocationPoint origin,
        LocationPoint destination,
        SchedulePoint departure,
        SchedulePoint arrival,
        int durationMinutes,
        Transfers transfers,
        List<Segment> segments
) {
}
