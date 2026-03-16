package com.tripify.hotels.parser.provider;

import com.tripify.hotels.parser.dto.HotelsResponseDto;
import com.tripify.hotels.parser.models.Country;
import com.tripify.hotels.parser.models.Provider;
import com.tripify.hotels.parser.models.provider.HotelsProvider;
import com.tripify.hotels.parser.models.provider.HotelsProviderAdapter;
import com.tripify.hotels.parser.models.provider.HotelsProviderParser;
import com.tripify.hotels.parser.models.provider.mock.MockProviderHotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mock-поставщик: фасад с парсером (который использует Client) и адаптером.
 */
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
    public HotelsResponseDto supplyHotels(Country country) {
        return adapter.adapt(parser.parse(country));
    }
}
