package com.tripify.info.weather;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "tripify.info.weather.provider", havingValue = "stub")
public class StubWeatherForecastProvider implements WeatherForecastProvider {

    private static final WeatherCondition[] ROTATION = {
            WeatherCondition.CLEAR,
            WeatherCondition.PARTLY_CLOUDY,
            WeatherCondition.CLOUDY,
            WeatherCondition.RAIN,
            WeatherCondition.SHOWERS,
            WeatherCondition.THUNDERSTORM
    };

    @Override
    public WeatherForecast getForecast(
            double latitude,
            double longitude,
            LocalDate stayFrom,
            LocalDate stayTo,
            LocalDate fetchFrom,
            LocalDate fetchTo
    ) {
        List<WeatherDayForecast> days = new ArrayList<>();
        LocalDate cursor = fetchFrom;
        int dayIndex = 0;

        while (!cursor.isAfter(fetchTo)) {
            if (!cursor.isBefore(stayFrom) && !cursor.isAfter(stayTo)) {
                days.add(new WeatherDayForecast(
                        cursor,
                        18.0 + dayIndex,
                        ROTATION[dayIndex % ROTATION.length],
                        dayIndex % 3 == 0 ? 2.5 : 0.0
                ));
            }
            cursor = cursor.plusDays(1);
            dayIndex++;
        }

        return new WeatherForecast(stayFrom, stayTo, latitude, longitude, List.copyOf(days));
    }
}
