package com.tripify.hotels.service.repository.contract;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import com.tripify.hotels.service.model.HotelReviewsSummary;

public interface HotelReviewsSummaryRepository {

    Optional<HotelReviewsSummary> findByHotelId(UUID hotelId);

    Map<UUID, HotelReviewsSummary> findByHotelIds(List<UUID> hotelIds);

    HotelReviewsSummary upsert(HotelReviewsSummary reviewsSummary);
}
