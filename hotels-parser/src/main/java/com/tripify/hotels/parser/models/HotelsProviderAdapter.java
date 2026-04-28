package com.tripify.hotels.parser.models;

import com.tripify.hotels.parser.dto.HotelsResponseDto;

import java.util.List;

/**
 * Адаптер поставщика: приводит DTO поставщика к общему HotelsResponseDto.
 */
public interface HotelsProviderAdapter<T> {
    HotelsResponseDto adapt(List<T> providerHotels);
}
