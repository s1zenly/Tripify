package com.tripify.tickets.service.model.unified;

import lombok.Builder;

@Builder
public record Fare(
        boolean refundable,
        boolean exchangeable,
        String fareFamily
) {
}
