package com.tripify.info.weather;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.tripify.info.client.OpenMeteoClient;
import com.tripify.info.client.OpenMeteoResponse;
import com.tripify.info.exception.InfoErrorCode;
import com.tripify.info.exception.InfoServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "tripify.info.weather.provider", havingValue = "open-meteo", matchIfMissing = true)
public class OpenMeteoWeatherForecastProvider implements WeatherForecastProvider {

    private final OpenMeteoClient openMeteoClient;

    @Override
    public WeatherForecast getForecast(
            double latitude,
            double longitude,
            LocalDate stayFrom,
            LocalDate stayTo,
            LocalDate fetchFrom,
            LocalDate fetchTo
    ) {
        OpenMeteoResponse response = openMeteoClient.fetchDailyForecast(
                latitude,
                longitude,
                fetchFrom,
                fetchTo
        );
        OpenMeteoResponse.Daily daily = response.daily();

        List<WeatherDayForecast> days = new ArrayList<>();
        for (int index = 0; index < daily.time().size(); index++) {
            LocalDate date = LocalDate.parse(daily.time().get(index));
            if (date.isBefore(stayFrom) || date.isAfter(stayTo)) {
                continue;
            }

            double temperature = requireValue(daily.temperature2mMean(), index, "temperature_2m_mean");
            double precipitation = valueOrZero(daily.precipitationSum(), index);
            int weatherCode = requireInt(daily.weatherCode(), index, "weather_code");

            days.add(new WeatherDayForecast(
                    date,
                    roundOneDecimal(temperature),
                    WmoWeatherCodeMapper.fromWmoCode(weatherCode),
                    roundOneDecimal(precipitation)
            ));
        }

        if (days.isEmpty()) {
            throw new InfoServiceException(
                    InfoErrorCode.WEATHER_FORECAST_FAILED,
                    "Open-Meteo returned no days within stay period " + stayFrom + ".." + stayTo
            );
        }

        return new WeatherForecast(stayFrom, stayTo, latitude, longitude, List.copyOf(days));
    }

    private static double requireValue(List<Double> values, int index, String field) {
        if (values == null || index >= values.size() || values.get(index) == null) {
            throw new InfoServiceException(
                    InfoErrorCode.WEATHER_FORECAST_FAILED,
                    "Open-Meteo response missing " + field + " for day index " + index
            );
        }
        return values.get(index);
    }

    private static int requireInt(List<Integer> values, int index, String field) {
        if (values == null || index >= values.size() || values.get(index) == null) {
            throw new InfoServiceException(
                    InfoErrorCode.WEATHER_FORECAST_FAILED,
                    "Open-Meteo response missing " + field + " for day index " + index
            );
        }
        return values.get(index);
    }

    private static double valueOrZero(List<Double> values, int index) {
        if (values == null || index >= values.size() || values.get(index) == null) {
            return 0.0;
        }
        return values.get(index);
    }

    private static double roundOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
