package com.tripify.info.weather;

import java.time.LocalDate;

public record WeatherDayForecast(
        LocalDate date,
        double temperatureAvgC,
        WeatherCondition condition,
        double precipitationMm
) {
}
