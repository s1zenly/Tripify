package com.tripify.hotels.service.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record HotelPaymentMethods(
        UUID hotelId,
        Boolean isCash,
        List<String> cashCurrencies,
        Boolean isCard,
        List<String> cardTypes,
        Instant createdAt,
        Instant updatedAt
) {
}
