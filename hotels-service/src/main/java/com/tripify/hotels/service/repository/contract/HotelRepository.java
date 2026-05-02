package com.tripify.hotels.service.repository.contract;

import java.util.Optional;
import java.util.UUID;

import com.tripify.hotels.service.model.Hotel;

public interface HotelRepository {

    Optional<Hotel> findById(UUID id);

    Optional<Hotel> findByProviderAndExternalId(String providerName, Long externalHotelId);

    Hotel upsert(Hotel hotel);
}
