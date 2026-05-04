package com.tripify.hotels.service.repository.contract;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.tripify.hotels.service.model.HotelFacility;

public interface HotelFacilityRepository {

    List<HotelFacility> findByHotelId(UUID hotelId);

    Map<UUID, List<HotelFacility>> findByHotelIds(List<UUID> hotelIds);

    void replaceAll(UUID hotelId, List<HotelFacility> facilities);
}
