package com.tripify.info.weather;

import java.time.LocalDate;
import java.util.List;

public record WeatherForecast(
        LocalDate dateFrom,
        LocalDate dateTo,
        double latitude,
        double longitude,
        List<WeatherDayForecast> days
) {
}
