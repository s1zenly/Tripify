package com.tripify.hotels.parser.models.provider;

import com.tripify.hotels.parser.models.Country;

import java.util.List;

/**
 * Клиент поставщика: забирает сырые данные по стране.
 */
public interface HotelsProviderClient<T> {
    List<T> fetch(Country country);
}
