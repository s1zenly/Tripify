package com.tripify.tickets.service.model.unified;

import lombok.Builder;

@Builder
public record LocationPoint(
        String cityCode,
        String cityName,
        String airportCode,
        String airportName,
        String terminal
) {
}
