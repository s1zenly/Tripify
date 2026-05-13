package com.tripify.info.service;

import java.util.List;
import java.util.Optional;

import com.tripify.info.config.CountryInfoProperties;
import com.tripify.info.domain.Country;
import com.tripify.info.model.CountryInfoTab;
import com.tripify.info.parser.CountryLocalCurrencyExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountryLocalCurrencyResolver {

    private final CountryLocalCurrencyExtractor currencyExtractor;

    public String resolve(
            String countryAlpha2,
            CountryInfoProperties.CountryConfig countryConfig,
            List<CountryInfoTab> tabs
    ) {
        Optional<String> fromParser = currencyExtractor.extract(tabs);
        if (fromParser.isPresent()) {
            return fromParser.get();
        }

        if (countryConfig.localCurrency() != null && !countryConfig.localCurrency().isBlank()) {
            return countryConfig.localCurrency().trim().toUpperCase();
        }

        return Country.fromAlpha2(countryAlpha2).defaultLocalCurrency();
    }
}
