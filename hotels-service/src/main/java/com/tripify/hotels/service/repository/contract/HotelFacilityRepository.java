package com.tripify.hotels.service.repository.contract;

import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.model.HotelFacility;

public interface HotelFacilityRepository {

    List<HotelFacility> findByHotelId(UUID hotelId);

    void replaceAll(UUID hotelId, List<HotelFacility> facilities);
}
