package com.tripify.info.currency;

import java.math.BigDecimal;

public record CurrencyRate(
        String currency,
        BigDecimal rate
) {
}
