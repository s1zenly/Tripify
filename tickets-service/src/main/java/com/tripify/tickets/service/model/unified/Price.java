package com.tripify.tickets.service.model.unified;

import lombok.Builder;

@Builder
public record Price(
        long amount,
        Currency currency,
        Long originalAmount,
        Currency originalCurrency
) {
}
