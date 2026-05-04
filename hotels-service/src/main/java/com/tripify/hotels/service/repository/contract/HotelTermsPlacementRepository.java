package com.tripify.hotels.service.repository.contract;

import java.util.Optional;
import java.util.UUID;
import com.tripify.hotels.service.model.HotelTermsPlacement;

public interface HotelTermsPlacementRepository {

    Optional<HotelTermsPlacement> findByHotelId(UUID hotelId);

    HotelTermsPlacement upsert(HotelTermsPlacement termsPlacement);
}
