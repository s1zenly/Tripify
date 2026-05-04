package com.tripify.hotels.service.repository.contract;

import java.util.Optional;
import java.util.UUID;
import com.tripify.hotels.service.model.HotelPaymentMethods;

public interface HotelPaymentMethodsRepository {

    Optional<HotelPaymentMethods> findByHotelId(UUID hotelId);

    HotelPaymentMethods upsert(HotelPaymentMethods paymentMethods);
}
