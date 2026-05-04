package com.tripify.hotels.service.repository.contract;

import java.util.Optional;
import java.util.UUID;

import com.tripify.hotels.service.model.Hotel;
import com.tripify.hotels.service.model.HotelSearchFilter;
import com.tripify.hotels.service.model.HotelSearchPage;

public interface HotelRepository {

    Optional<Hotel> findById(UUID id);

    HotelSearchPage search(HotelSearchFilter filter);

    Hotel upsert(Hotel hotel);
}
