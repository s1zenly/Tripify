package com.tripify.hotels.service.service.currency;

import java.math.BigDecimal;

public interface CurrencyConversionService {

    String storageCurrency();

    BigDecimal toStorageCurrency(BigDecimal amount, String sourceCurrency);

    BigDecimal fromStorageCurrency(BigDecimal amountInStorageCurrency, String targetCurrency);
}
