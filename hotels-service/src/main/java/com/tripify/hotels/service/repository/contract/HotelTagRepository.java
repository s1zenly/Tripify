package com.tripify.hotels.service.repository.contract;

import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.model.HotelTag;

public interface HotelTagRepository {

    List<HotelTag> findByHotelId(UUID hotelId);

    void replaceAll(UUID hotelId, List<HotelTag> tags);
}
