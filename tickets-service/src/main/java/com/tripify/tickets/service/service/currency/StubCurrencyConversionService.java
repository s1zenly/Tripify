package com.tripify.tickets.service.service.currency;

import com.tripify.tickets.service.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Map;

/**
 * Заглушка курсов: офферы храним в RUB, на чтение конвертируем в валюту запроса фронта.
 * Курсы: сколько RUB за 1 единицу исходной валюты (1 USD = 90 RUB и т.д.).
 */
@Service
public class StubCurrencyConversionService implements CurrencyConversionService {

    private static final String RUB = "RUB";
    private static final int SCALE = 0;

    /**
     * RUB за 1 единицу валюты.
     */
    private static final Map<String, BigDecimal> RUB_PER_UNIT = Map.of(
            RUB, BigDecimal.ONE,
            "USD", new BigDecimal("90"),
            "EUR", new BigDecimal("98")
    );

    @Override
    public String storageCurrency() {
        return RUB;
    }

    @Override
    public BigDecimal toStorageCurrency(BigDecimal amount, String sourceCurrency) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }

        String normalized = normalize(sourceCurrency);
        if (RUB.equals(normalized)) {
            return amount.setScale(SCALE, RoundingMode.HALF_UP);
        }

        BigDecimal rubPerUnit = rateOrThrow(normalized);
        return amount.multiply(rubPerUnit).setScale(SCALE, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal fromStorageCurrency(BigDecimal amountInStorageCurrency, String targetCurrency) {
        if (amountInStorageCurrency == null) {
            return BigDecimal.ZERO;
        }

        String normalized = normalize(targetCurrency);
        if (RUB.equals(normalized)) {
            return amountInStorageCurrency.setScale(SCALE, RoundingMode.HALF_UP);
        }

        throw new BadRequestException("Unsupported currency: " + normalized);
    }

    private static BigDecimal rateOrThrow(String currency) {
        BigDecimal rate = RUB_PER_UNIT.get(currency);
        if (rate == null) {
            throw new BadRequestException("Unsupported currency: " + currency);
        }

        return rate;
    }

    private static String normalize(String currency) {
        if (currency == null || currency.isBlank()) {
            throw new BadRequestException("currency is required");
        }

        return currency.trim().toUpperCase(Locale.ROOT);
    }
}
