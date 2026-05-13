package com.tripify.info.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import com.tripify.info.client.TutuGeoClient;
import com.tripify.info.config.CountryInfoProperties;
import com.tripify.info.domain.CountryCodes;
import com.tripify.info.dto.CountryInfoRefreshResult;
import com.tripify.info.exception.CountryInfoNotFoundException;
import com.tripify.info.exception.InfoErrorCode;
import com.tripify.info.exception.InfoServiceException;
import com.tripify.info.model.CountryInfoDocument;
import com.tripify.info.model.CountryInfoSource;
import com.tripify.info.model.CountryInfoTab;
import com.tripify.info.parser.TutuCountryPageParser;
import com.tripify.info.repository.CountryInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryInfoService {

    private final CountryInfoRepository repository;
    private final TutuGeoClient tutuGeoClient;
    private final TutuCountryPageParser parser;
    private final CountryInfoProperties properties;
    private final CountryLocalCurrencyResolver localCurrencyResolver;
    private final CountryWeatherService countryWeatherService;

    public CountryInfoQueryResult getCountryInfo(String countryId, LocalDate dateFrom, LocalDate dateTo) {
        String alpha2 = CountryCodes.normalize(countryId);
        CountryInfoDocument document = repository.findById(alpha2)
                .orElseThrow(() -> new CountryInfoNotFoundException(alpha2));
        CountryInfoDocument enriched = ensureLocalCurrency(document);
        var weather = countryWeatherService.tryForecast(alpha2, dateFrom, dateTo);
        return new CountryInfoQueryResult(enriched, weather);
    }

    public CountryInfoDocument refreshCountry(String countryId) {
        String alpha2 = CountryCodes.normalize(countryId);
        CountryInfoProperties.CountryConfig countryConfig = findCountryConfig(alpha2);

        log.info("Starting country info refresh for country_id={} source_url={}",
                alpha2, countryConfig.sourceUrl());

        String html = tutuGeoClient.downloadPage(countryConfig.sourceUrl());
        List<CountryInfoTab> tabs = parser.parse(html);
        String localCurrency = localCurrencyResolver.resolve(alpha2, countryConfig, tabs);

        int sectionCount = tabs.stream()
                .mapToInt(tab -> tab.sections().size())
                .sum();

        CountryInfoDocument document = new CountryInfoDocument(
                countryConfig.alpha2(),
                countryConfig.name(),
                localCurrency,
                countryConfig.sourceUrl(),
                tabs,
                Instant.now(),
                CountryInfoSource.TUTU
        );

        CountryInfoDocument saved = repository.save(document);

        log.info(
                "Country info saved for country_id={} local_currency={} tabs={} sections={}",
                alpha2,
                localCurrency,
                tabs.size(),
                sectionCount
        );

        return saved;
    }

    private CountryInfoDocument ensureLocalCurrency(CountryInfoDocument document) {
        if (document.localCurrency() != null && !document.localCurrency().isBlank()) {
            return document;
        }

        CountryInfoProperties.CountryConfig config = findCountryConfig(document.countryId());
        String localCurrency = localCurrencyResolver.resolve(document.countryId(), config, document.tabs());

        return new CountryInfoDocument(
                document.countryId(),
                document.countryName(),
                localCurrency,
                document.sourceUrl(),
                document.tabs(),
                document.updatedAt(),
                document.source()
        );
    }

    public CountryInfoRefreshResult refreshAllCountries() {
        List<CountryInfoProperties.CountryConfig> countries = properties.scraping().countries();
        log.info("Starting refresh for {} countries", countries.size());

        int updatedCount = 0;
        int failedCount = 0;

        for (CountryInfoProperties.CountryConfig country : countries) {
            try {
                refreshCountry(country.alpha2());
                updatedCount++;
            } catch (RuntimeException exception) {
                failedCount++;
                log.error("Failed to refresh country_id={} source_url={}",
                        country.alpha2(), country.sourceUrl(), exception);
            }
        }

        log.info("Country info refresh finished: updated_count={} failed_count={}",
                updatedCount, failedCount);

        return new CountryInfoRefreshResult(updatedCount, failedCount);
    }

    private CountryInfoProperties.CountryConfig findCountryConfig(String alpha2) {
        return properties.scraping().countries().stream()
                .filter(country -> country.alpha2().equalsIgnoreCase(alpha2))
                .findFirst()
                .orElseThrow(() -> new InfoServiceException(
                        InfoErrorCode.COUNTRY_CONFIG_NOT_FOUND,
                        "Country config not found for country_id=" + alpha2
                ));
    }
}
