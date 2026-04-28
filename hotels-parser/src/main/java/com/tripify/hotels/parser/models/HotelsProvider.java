package com.tripify.hotels.parser.models;

import com.tripify.hotels.parser.dto.HotelsResponseDto;

public interface HotelsProvider {

    Provider getProvider();

    HotelsResponseDto supplyHotels(Country country);
}
