package com.tripify.hotels.parser.dto;

import com.tripify.hotels.parser.models.common.CountryInfo;
import com.tripify.hotels.parser.models.common.Hotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelsResponseDto {
    private List<Hotel> hotels;
    private CountryInfo countryInfo;
    private String providerName;
    private LocalDateTime parsedAt;
    private LocalDateTime providedAt;
    private int totalHotels;
}