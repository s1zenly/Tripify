package com.tripify.info.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.tripify.info.exception.InfoErrorCode;
import com.tripify.info.exception.InfoServiceException;
import org.springframework.stereotype.Component;

/**
 * Заглушка курсов: якорь USD. rate(c→base) = usdPerUnit(c) / usdPerUnit(base).
 */
@Component
public class StubExchangeRateProvider {

    private static final String USD = "USD";
    private static final int RATE_SCALE = 6;

    /**
     * USD за 1 единицу валюты.
     */
    private static final Map<String, BigDecimal> USD_PER_UNIT = Map.ofEntries(
            Map.entry(USD, BigDecimal.ONE),
            Map.entry("EUR", new BigDecimal("1.09")),
            Map.entry("RUB", new BigDecimal("0.011")),
            Map.entry("AED", new BigDecimal("0.27")),
            Map.entry("GBP", new BigDecimal("1.27")),
            Map.entry("CNY", new BigDecimal("0.14")),
            Map.entry("TRY", new BigDecimal("0.031")),
            Map.entry("THB", new BigDecimal("0.028")),
            Map.entry("JPY", new BigDecimal("0.0067")),
            Map.entry("EGP", new BigDecimal("0.021"))
    );

    private final Instant ratesUpdatedAt = Instant.parse("2026-05-31T00:00:00Z");

    public List<String> supportedCurrencies() {
        return new ArrayList<>(SupportedCurrencies.ALL);
    }

    public Instant ratesUpdatedAt() {
        return ratesUpdatedAt;
    }

    public void validateSupported(String currency) {
        if (!SupportedCurrencies.isSupported(currency)) {
            throw new InfoServiceException(
                    InfoErrorCode.INVALID_CURRENCY_CODE,
                    "Unsupported currency: " + currency
            );
        }
    }

    /**
     * Сколько единиц {@code baseCurrency} за 1 единицу {@code currency}.
     */
    public BigDecimal rateToBase(String currency, String baseCurrency) {
        String from = SupportedCurrencies.normalize(currency);
        String base = SupportedCurrencies.normalize(baseCurrency);
        validateSupported(from);
        validateSupported(base);

        if (from.equals(base)) {
            return BigDecimal.ONE;
        }

        BigDecimal usdPerFrom = usdPerUnitOrThrow(from);
        BigDecimal usdPerBase = usdPerUnitOrThrow(base);

        return usdPerFrom.divide(usdPerBase, RATE_SCALE, RoundingMode.HALF_UP);
    }

    private static BigDecimal usdPerUnitOrThrow(String currency) {
        BigDecimal rate = USD_PER_UNIT.get(currency);
        if (rate == null) {
            throw new InfoServiceException(
                    InfoErrorCode.INVALID_CURRENCY_CODE,
                    "Unsupported currency: " + currency
            );
        }
        return rate;
    }
}
