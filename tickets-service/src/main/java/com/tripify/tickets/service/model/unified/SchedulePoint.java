package com.tripify.tickets.service.model.unified;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record SchedulePoint(
        OffsetDateTime datetime,
        String timezone
) {
}
