package com.tripify.hotels.parser.parser;

import com.tripify.hotels.parser.models.Country;
import com.tripify.hotels.parser.models.Provider;
import com.tripify.hotels.parser.models.provider.HotelsProviderClient;
import com.tripify.hotels.parser.models.provider.HotelsProviderParser;
import com.tripify.hotels.parser.models.provider.mock.MockProviderHotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mock-парсер: запускает парсинг по стране через ProviderClient.
 */
@Component
@RequiredArgsConstructor
public class MockHotelsProviderParser implements HotelsProviderParser<MockProviderHotel> {

    private final HotelsProviderClient<MockProviderHotel> client;

    // TODO: Здесь будут еще методы чтобы парсить отдельные элементы отеля от клиента
    @Override
    public List<MockProviderHotel> parse(Country country) {
        return client.fetch(country);
    }
}
