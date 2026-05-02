package com.tripify.hotels.parser.dto;

import com.tripify.hotels.parser.models.common.CountryInfo;
import com.tripify.hotels.parser.models.common.Hotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelsResponseDto {
    private List<Hotel> hotels;
    private CountryInfo countryInfo;
    private String providerName;
    private Instant parsedAt;
    private Instant providedAt;
    private int totalHotels;
}