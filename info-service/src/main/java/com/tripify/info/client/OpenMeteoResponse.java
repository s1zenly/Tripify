package com.tripify.info.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenMeteoResponse(Daily daily) {

    public record Daily(
            List<String> time,
            @JsonProperty("temperature_2m_mean") List<Double> temperature2mMean,
            @JsonProperty("precipitation_sum") List<Double> precipitationSum,
            @JsonProperty("weather_code") List<Integer> weatherCode
    ) {
    }
}
