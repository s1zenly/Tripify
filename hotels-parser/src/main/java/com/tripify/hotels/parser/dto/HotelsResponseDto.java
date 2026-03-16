package com.tripify.hotels.parser.dto;

import com.tripify.hotels.parser.models.Hotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelsResponseDto {
    private List<Hotel> hotels;
    private int providerId;
    private String providerName;
    private int totalHotels;
}