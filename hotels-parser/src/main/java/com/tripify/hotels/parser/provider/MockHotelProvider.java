package com.tripify.hotels.parser.provider;

import com.tripify.hotels.parser.dto.HotelsResponseDto;
import com.tripify.hotels.parser.models.City;
import com.tripify.hotels.parser.models.Provider;
import com.tripify.hotels.parser.models.HotelsProvider;
import com.tripify.hotels.parser.models.HotelsProviderAdapter;
import com.tripify.hotels.parser.models.HotelsProviderParser;
import com.tripify.hotels.parser.models.provider.mock.MockProviderHotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockHotelProvider implements HotelsProvider {

    private final HotelsProviderParser<MockProviderHotel> parser;
    private final HotelsProviderAdapter<MockProviderHotel> adapter;

    @Override
    public Provider getProvider() {
        return Provider.MOCK;
    }

    @Override
    public HotelsResponseDto supplyHotels(City city) {
        return adapter.adapt(parser.parse(city));
    }
}
