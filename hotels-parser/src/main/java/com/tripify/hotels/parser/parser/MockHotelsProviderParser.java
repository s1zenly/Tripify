package com.tripify.hotels.parser.parser;

import com.tripify.hotels.parser.models.City;
import com.tripify.hotels.parser.models.HotelsProviderClient;
import com.tripify.hotels.parser.models.HotelsProviderParser;
import com.tripify.hotels.parser.models.provider.mock.MockProviderHotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockHotelsProviderParser implements HotelsProviderParser<MockProviderHotel> {

    private final HotelsProviderClient<MockProviderHotel> client;

    @Override
    public List<MockProviderHotel> parse(City city) {
        return client.fetch(city);
    }
}
