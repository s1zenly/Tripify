package com.tripify.info.service;

import java.util.Optional;

import com.tripify.info.model.CountryInfoDocument;
import com.tripify.info.weather.WeatherForecast;

public record CountryInfoQueryResult(
        CountryInfoDocument document,
        Optional<WeatherForecast> weather
) {
}
