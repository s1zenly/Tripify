package com.tripify.tickets.service.model.unified;

import lombok.Builder;

@Builder
public record Passengers(
        int adults,
        int children
) {
}
