package com.tripify.hotels.service.repository.contract;

import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.model.HotelNearbyPlace;

public interface HotelNearbyPlaceRepository {

    List<HotelNearbyPlace> findByHotelId(UUID hotelId);

    void replaceAll(UUID hotelId, List<HotelNearbyPlace> nearbyPlaces);
}
