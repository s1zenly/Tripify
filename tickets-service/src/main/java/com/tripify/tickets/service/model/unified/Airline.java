package com.tripify.tickets.service.model.unified;

import lombok.Builder;

@Builder
public record Airline(
        String code,
        String name,
        String logoUrl
) {
}
