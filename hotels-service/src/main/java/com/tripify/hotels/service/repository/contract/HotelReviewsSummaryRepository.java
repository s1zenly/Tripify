package com.tripify.hotels.service.repository.contract;

import java.util.Optional;
import java.util.UUID;

import com.tripify.hotels.service.model.HotelReviewsSummary;

public interface HotelReviewsSummaryRepository {

    Optional<HotelReviewsSummary> findByHotelId(UUID hotelId);

    HotelReviewsSummary upsert(HotelReviewsSummary reviewsSummary);
}
