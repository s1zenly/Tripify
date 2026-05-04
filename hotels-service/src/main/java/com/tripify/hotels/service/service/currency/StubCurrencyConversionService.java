package com.tripify.hotels.service.service.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Map;

import com.tripify.hotels.service.exception.BadRequestException;
import org.springframework.stereotype.Service;

/**
 * Заглушка курсов: в БД храним USD, на чтение конвертируем в валюту запроса фронта.
 * Курсы: сколько USD за 1 единицу исходной валюты (1 EUR = 1.09 USD и т.д.).
 */
@Service
public class StubCurrencyConversionService implements CurrencyConversionService {

    private static final String USD = "USD";
    private static final int SCALE = 2;

    /**
     * USD за 1 единицу валюты.
     */
    private static final Map<String, BigDecimal> USD_PER_UNIT = Map.of(
            USD, BigDecimal.ONE,
            "EUR", new BigDecimal("1.09"),
            "RUB", new BigDecimal("0.011"),
            "AED", new BigDecimal("0.27"),
            "GBP", new BigDecimal("1.27")
    );

    @Override
    public String storageCurrency() {
        return USD;
    }

    @Override
    public BigDecimal toStorageCurrency(BigDecimal amount, String sourceCurrency) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }

        String normalized = normalize(sourceCurrency);
        if (USD.equals(normalized)) {
            return amount.setScale(SCALE, RoundingMode.HALF_UP);
        }

        BigDecimal usdPerUnit = rateOrThrow(normalized);
        return amount.multiply(usdPerUnit).setScale(SCALE, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal fromStorageCurrency(BigDecimal amountInStorageCurrency, String targetCurrency) {
        if (amountInStorageCurrency == null) {
            return BigDecimal.ZERO;
        }

        String normalized = normalize(targetCurrency);
        if (USD.equals(normalized)) {
            return amountInStorageCurrency.setScale(SCALE, RoundingMode.HALF_UP);
        }

        BigDecimal usdPerUnit = rateOrThrow(normalized);
        return amountInStorageCurrency
                .divide(usdPerUnit, SCALE, RoundingMode.HALF_UP);
    }

    private static BigDecimal rateOrThrow(String currency) {
        BigDecimal rate = USD_PER_UNIT.get(currency);
        if (rate == null) {
            throw new BadRequestException("Unsupported currency: " + currency);
        }

        return rate;
    }

    private static String normalize(String currency) {
        if (currency == null || currency.isBlank()) {
            return USD;
        }

        return currency.trim().toUpperCase(Locale.ROOT);
    }
}
