package com.tripify.info.weather;

import java.time.LocalDate;

public interface WeatherForecastProvider {

    WeatherForecast getForecast(
            double latitude,
            double longitude,
            LocalDate stayFrom,
            LocalDate stayTo,
            LocalDate fetchFrom,
            LocalDate fetchTo
    );
}
