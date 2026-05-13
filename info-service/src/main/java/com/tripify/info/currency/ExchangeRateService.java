package com.tripify.info.currency;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final StubExchangeRateProvider rateProvider;

    public CurrencyRatesSnapshot getRates(String baseCurrency) {
        String base = SupportedCurrencies.normalize(baseCurrency);
        rateProvider.validateSupported(base);

        List<CurrencyRate> rates = new ArrayList<>();
        for (String currency : rateProvider.supportedCurrencies()) {
            if (currency.equals(base)) {
                continue;
            }
            rates.add(new CurrencyRate(currency, rateProvider.rateToBase(currency, base)));
        }

        return new CurrencyRatesSnapshot(base, rates, rateProvider.ratesUpdatedAt());
    }
}
