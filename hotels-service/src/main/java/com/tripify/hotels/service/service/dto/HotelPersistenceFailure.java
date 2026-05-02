package com.tripify.hotels.service.service.dto;

public record HotelPersistenceFailure(
        Long externalHotelId,
        String reason
) {
}
