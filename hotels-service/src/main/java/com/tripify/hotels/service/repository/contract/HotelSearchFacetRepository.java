package com.tripify.hotels.service.repository.contract;

import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.model.HotelSearchFacet;

public interface HotelSearchFacetRepository {

    void replaceAll(UUID hotelId, List<HotelSearchFacet> facets);
}
