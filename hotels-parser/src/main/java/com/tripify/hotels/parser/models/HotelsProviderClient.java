package com.tripify.hotels.parser.models;

import java.util.List;

/**
 * Клиент поставщика: забирает сырые данные по городу.
 */
public interface HotelsProviderClient<T> {
    List<T> fetch(City city);
}
