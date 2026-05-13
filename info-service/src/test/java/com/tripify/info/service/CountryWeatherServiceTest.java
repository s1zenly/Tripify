package com.tripify.info.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import com.tripify.info.config.CountryInfoProperties;
import com.tripify.info.exception.InfoErrorCode;
import com.tripify.info.exception.InfoServiceException;
import com.tripify.info.weather.StubWeatherForecastProvider;
import com.tripify.info.weather.WeatherCondition;
import com.tripify.info.weather.WeatherDayForecast;
import com.tripify.info.weather.WeatherForecast;
import com.tripify.info.weather.WeatherForecastProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CountryWeatherServiceTest {

    @Mock
    private WeatherForecastProvider weatherForecastProvider;

    private CountryWeatherService service;
    private CountryWeatherService stubService;

    @BeforeEach
    void setUp() {
        CountryInfoProperties properties = new CountryInfoProperties(
                null,
                new CountryInfoProperties.Weather(true, "stub", "http://example", 1000, 1000, 16)
        );
        service = new CountryWeatherService(properties, weatherForecastProvider);
        stubService = new CountryWeatherService(properties, new StubWeatherForecastProvider());
    }

    @Test
    void returnsForecastForEachStayDayWithinHorizon() {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        LocalDate from = today.plusDays(1);
        LocalDate to = today.plusDays(3);

        WeatherForecast forecast = stubService.tryForecast("CN", from, to).orElseThrow();

        assertThat(forecast.days()).hasSize(3);
        assertThat(forecast.days().get(0).date()).isEqualTo(from);
        assertThat(forecast.days().get(0).condition()).isEqualTo(WeatherCondition.CLEAR);
    }

    @Test
    void skipsProviderWhenStayIsBeyondHorizon() {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        LocalDate from = today.plusDays(20);
        LocalDate to = today.plusDays(25);

        assertThat(service.tryForecast("CN", from, to)).isEmpty();

        verify(weatherForecastProvider, never()).getForecast(
                anyDouble(),
                anyDouble(),
                any(LocalDate.class),
                any(LocalDate.class),
                any(LocalDate.class),
                any(LocalDate.class)
        );
    }

    @Test
    void requestsClippedWindowForPartialOverlap() {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        LocalDate stayFrom = today.plusDays(12);
        LocalDate stayTo = today.plusDays(20);
        LocalDate fetchTo = today.plusDays(15);

        when(weatherForecastProvider.getForecast(
                eq(39.9042),
                eq(116.4074),
                eq(stayFrom),
                eq(stayTo),
                eq(stayFrom),
                eq(fetchTo)
        )).thenReturn(new WeatherForecast(
                stayFrom,
                stayTo,
                39.9042,
                116.4074,
                List.of(new WeatherDayForecast(stayFrom, 20.0, WeatherCondition.CLEAR, 0.0))
        ));

        WeatherForecast forecast = service.tryForecast("CN", stayFrom, stayTo).orElseThrow();

        assertThat(forecast.days()).hasSize(1);
        verify(weatherForecastProvider).getForecast(
                eq(39.9042),
                eq(116.4074),
                eq(stayFrom),
                eq(stayTo),
                eq(stayFrom),
                eq(fetchTo)
        );
    }

    @Test
    void rejectsInvalidDateRange() {
        assertThatThrownBy(() -> service.tryForecast(
                "CN",
                LocalDate.of(2026, 6, 20),
                LocalDate.of(2026, 6, 10)
        ))
                .isInstanceOf(InfoServiceException.class)
                .extracting(ex -> ((InfoServiceException) ex).getErrorCode())
                .isEqualTo(InfoErrorCode.INVALID_STAY_DATES);
    }

    @Test
    void returnsEmptyWhenProviderFails() {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        LocalDate from = today.plusDays(1);
        LocalDate to = today.plusDays(2);

        when(weatherForecastProvider.getForecast(
                anyDouble(),
                anyDouble(),
                any(LocalDate.class),
                any(LocalDate.class),
                any(LocalDate.class),
                any(LocalDate.class)
        )).thenThrow(new InfoServiceException(InfoErrorCode.WEATHER_FORECAST_FAILED, "provider down"));

        assertThat(service.tryForecast("CN", from, to)).isEmpty();
    }
}
