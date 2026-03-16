package com.tripify.hotels.parser.models.provider;

import com.tripify.hotels.parser.models.Country;
import com.tripify.hotels.parser.models.Provider;

import java.util.List;

/**
 * Парсер поставщика: запускает парсинг по стране (использует внутри ProviderClient).
 */
public interface HotelsProviderParser<T> {
    List<T> parse(Country country);
}
