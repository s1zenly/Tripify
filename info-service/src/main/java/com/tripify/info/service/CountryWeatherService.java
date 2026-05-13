package com.tripify.info.service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

import com.tripify.info.config.CountryInfoProperties;
import com.tripify.info.domain.Country;
import com.tripify.info.domain.CountryCodes;
import com.tripify.info.exception.InfoErrorCode;
import com.tripify.info.exception.InfoServiceException;
import com.tripify.info.weather.ForecastHorizon;
import com.tripify.info.weather.WeatherForecast;
import com.tripify.info.weather.WeatherForecastProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryWeatherService {

    private final CountryInfoProperties properties;
    private final WeatherForecastProvider weatherForecastProvider;

    public Optional<WeatherForecast> tryForecast(String countryId, LocalDate dateFrom, LocalDate dateTo) {
        validateStayDates(dateFrom, dateTo);

        if (!properties.weather().enabled()) {
            log.debug("Weather forecast is disabled, skipping weather tab");
            return Optional.empty();
        }

        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        Optional<ForecastHorizon.Window> horizon = ForecastHorizon.resolve(
                dateFrom,
                dateTo,
                today,
                properties.weather().maxDays()
        );
        if (horizon.isEmpty()) {
            log.debug(
                    "Stay period {}..{} is outside forecast horizon ({}..{}), skipping weather tab",
                    dateFrom,
                    dateTo,
                    today,
                    today.plusDays(properties.weather().maxDays() - 1L)
            );
            return Optional.empty();
        }

        ForecastHorizon.Window window = horizon.get();
        try {
            Country country = Country.fromAlpha2(CountryCodes.normalize(countryId));
            WeatherForecast forecast = weatherForecastProvider.getForecast(
                    country.weatherLatitude(),
                    country.weatherLongitude(),
                    dateFrom,
                    dateTo,
                    window.fetchFrom(),
                    window.fetchTo()
            );
            return Optional.of(forecast);
        } catch (InfoServiceException exception) {
            log.warn(
                    "Weather forecast failed for country_id={} date_from={} date_to={}: {}",
                    countryId,
                    dateFrom,
                    dateTo,
                    exception.getMessage()
            );
            return Optional.empty();
        } catch (RuntimeException exception) {
            log.warn(
                    "Weather forecast failed for country_id={} date_from={} date_to={}",
                    countryId,
                    dateFrom,
                    dateTo,
                    exception
            );
            return Optional.empty();
        }
    }

    private void validateStayDates(LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom == null || dateTo == null) {
            throw new InfoServiceException(
                    InfoErrorCode.INVALID_STAY_DATES,
                    "date_from and date_to are required"
            );
        }

        if (dateTo.isBefore(dateFrom)) {
            throw new InfoServiceException(
                    InfoErrorCode.INVALID_STAY_DATES,
                    "date_to must be on or after date_from"
            );
        }
    }
}
