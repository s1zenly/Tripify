package com.tripify.info.currency;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tripify.info.domain.Country;
import com.tripify.info.exception.InfoErrorCode;
import com.tripify.info.exception.InfoServiceException;

/**
 * Поддерживаемые ISO 4217 коды для курсов.
 * Валюты стран — из {@link Country#defaultLocalCurrency()}; GBP/USD — для отображения в приложении.
 */
public final class SupportedCurrencies {

  private static final Set<String> EXTRA = Set.of("USD", "GBP");

  public static final Set<String> ALL = Stream.concat(
          Arrays.stream(Country.values()).map(Country::defaultLocalCurrency),
          EXTRA.stream()
  ).collect(Collectors.toUnmodifiableSet());

  private SupportedCurrencies() {
  }

  public static boolean isSupported(String currency) {
    if (currency == null || currency.isBlank()) {
      return false;
    }
    String normalized = currency.trim().toUpperCase(Locale.ROOT);
    return normalized.matches("[A-Z]{3}") && ALL.contains(normalized);
  }

  public static String normalize(String currency) {
    if (currency == null || currency.isBlank()) {
      throw new InfoServiceException(
              InfoErrorCode.INVALID_CURRENCY_CODE,
              "Currency code is required"
      );
    }

    String normalized = currency.trim().toUpperCase(Locale.ROOT);
    if (!normalized.matches("[A-Z]{3}")) {
      throw new InfoServiceException(
              InfoErrorCode.INVALID_CURRENCY_CODE,
              "Currency must be a 3-letter ISO 4217 code, got=" + currency
      );
    }

    if (!ALL.contains(normalized)) {
      throw new InfoServiceException(
              InfoErrorCode.INVALID_CURRENCY_CODE,
              "Unsupported currency: " + normalized
      );
    }

    return normalized;
  }
}
