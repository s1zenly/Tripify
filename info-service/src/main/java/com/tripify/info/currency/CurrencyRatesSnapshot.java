package com.tripify.info.currency;

import java.time.Instant;
import java.util.List;

public record CurrencyRatesSnapshot(
        String baseCurrency,
        List<CurrencyRate> rates,
        Instant updatedAt
) {
}
