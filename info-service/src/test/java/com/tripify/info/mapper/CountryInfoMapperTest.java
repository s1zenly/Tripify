package com.tripify.info.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.tripify.info.currency.ExchangeRateService;
import com.tripify.info.currency.StubExchangeRateProvider;
import com.tripify.info.model.CountryInfoDocument;
import com.tripify.info.model.CountryInfoSource;
import com.tripify.info.model.CountryInfoTab;
import com.tripify.info.model.CountryInfoTabCode;
import com.tripify.info.service.CountryInfoQueryResult;
import com.tripify.info.weather.WeatherCondition;
import com.tripify.info.weather.WeatherDayForecast;
import com.tripify.info.weather.WeatherForecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountryInfoMapperTest {

    private CountryInfoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CountryInfoMapper(new ExchangeRateService(new StubExchangeRateProvider()));
    }

    @Test
    void appendsWeatherTabWhenForecastPresent() {
        CountryInfoDocument document = new CountryInfoDocument(
                "CN",
                "Китай",
                "CNY",
                "https://example.com",
                List.of(new CountryInfoTab(CountryInfoTabCode.OVERVIEW, "Основное", List.of())),
                java.time.Instant.now(),
                CountryInfoSource.TUTU
        );
        WeatherForecast forecast = new WeatherForecast(
                LocalDate.of(2026, 6, 15),
                LocalDate.of(2026, 6, 15),
                39.9,
                116.4,
                List.of(new WeatherDayForecast(
                        LocalDate.of(2026, 6, 15),
                        22.0,
                        WeatherCondition.RAIN,
                        1.2
                ))
        );

        var response = mapper.toApiResponse(new CountryInfoQueryResult(document, Optional.of(forecast)));

        assertThat(response.getTabs()).hasSize(2);
        assertThat(response.getTabs().get(1).getCode().getValue()).isEqualTo("weather");
        assertThat(response.getTabs().get(1).getSections().getFirst().getData().getFirst())
                .containsEntry("condition", "rain");
    }

    @Test
    void omitsWeatherTabWhenForecastMissing() {
        CountryInfoDocument document = new CountryInfoDocument(
                "CN",
                "Китай",
                "CNY",
                "https://example.com",
                List.of(new CountryInfoTab(CountryInfoTabCode.OVERVIEW, "Основное", List.of())),
                java.time.Instant.now(),
                CountryInfoSource.TUTU
        );

        var response = mapper.toApiResponse(new CountryInfoQueryResult(document, Optional.empty()));

        assertThat(response.getTabs()).hasSize(1);
        assertThat(response.getTabs().getFirst().getCode().getValue()).isEqualTo("overview");
    }

    @Test
    void addsGlobalRatesList() {
        CountryInfoDocument document = new CountryInfoDocument(
                "CN",
                "Китай",
                "CNY",
                "https://example.com",
                List.of(new CountryInfoTab(CountryInfoTabCode.CURRENCY, "Валюта", List.of())),
                java.time.Instant.now(),
                CountryInfoSource.TUTU
        );

        var response = mapper.toApiResponse(new CountryInfoQueryResult(document, Optional.empty()));

        assertThat(response.getRates()).isNotEmpty();
        assertThat(response.getRates()).extracting("currency").contains("USD", "EUR", "RUB");
        assertThat(response.getRates()).noneMatch(rate -> "CNY".equals(rate.getCurrency()));
    }
}
