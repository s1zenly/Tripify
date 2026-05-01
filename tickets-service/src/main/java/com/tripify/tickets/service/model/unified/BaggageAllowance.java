package com.tripify.tickets.service.model.unified;

import lombok.Builder;

@Builder
public record BaggageAllowance(
        boolean included,
        Integer pieces,
        Integer weightKg
) {
}
