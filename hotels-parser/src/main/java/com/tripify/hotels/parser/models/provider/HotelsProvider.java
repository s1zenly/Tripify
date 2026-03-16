package com.tripify.hotels.parser.models.provider;

import com.tripify.hotels.parser.dto.HotelsResponseDto;
import com.tripify.hotels.parser.models.Country;
import com.tripify.hotels.parser.models.Provider;

public interface HotelsProvider {

    Provider getProvider();

    HotelsResponseDto supplyHotels(Country country);
}
