package com.tripify.hotels.parser.models;

import java.util.List;

/**
 * Парсер поставщика: запускает парсинг по городу (использует внутри ProviderClient).
 */
public interface HotelsProviderParser<T> {
    List<T> parse(City city);
}
