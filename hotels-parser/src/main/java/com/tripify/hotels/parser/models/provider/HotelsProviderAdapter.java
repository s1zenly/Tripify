package com.tripify.hotels.parser.models.provider;

import com.tripify.hotels.parser.dto.HotelsResponseDto;
import com.tripify.hotels.parser.models.Provider;

import java.util.List;

/**
 * Адаптер поставщика: приводит DTO поставщика к общему HotelsResponseDto.
 */
public interface HotelsProviderAdapter<T> {
    HotelsResponseDto adapt(List<T> providerHotels);
}
