package com.tripify.hotels.service.repository.contract;

import java.util.Optional;
import java.util.UUID;
import com.tripify.hotels.service.model.HotelScore;

public interface HotelScoreRepository {

    Optional<HotelScore> findByHotelId(UUID hotelId);

    HotelScore upsert(HotelScore hotelScore);
}
