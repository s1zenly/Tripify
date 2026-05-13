package com.tripify.hotels.service.kafka.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HotelScoreDto(
        @JsonProperty("final") BigDecimal finalScore,
        HotelScoreBreakdownDto breakdown
) {
}
