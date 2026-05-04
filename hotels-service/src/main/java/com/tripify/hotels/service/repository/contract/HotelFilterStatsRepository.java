package com.tripify.hotels.service.repository.contract;

import java.util.Map;

import com.tripify.hotels.service.model.filter.HotelFilterDefinition;

public interface HotelFilterStatsRepository {

    Map<String, Integer> countFacetUsage(String country, String city);

    int countFreeCancellation(String country, String city);

    int countAttributeFilter(String country, String city, HotelFilterDefinition definition);
}
