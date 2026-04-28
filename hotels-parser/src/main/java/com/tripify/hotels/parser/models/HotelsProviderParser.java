package com.tripify.hotels.parser.models;

import java.util.List;

/**
 * Парсер поставщика: запускает парсинг по стране (использует внутри ProviderClient).
 */
public interface HotelsProviderParser<T> {
    List<T> parse(Country country);
}
