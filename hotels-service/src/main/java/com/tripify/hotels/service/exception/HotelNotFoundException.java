package com.tripify.hotels.service.exception;

import java.util.UUID;

public class HotelNotFoundException extends RuntimeException {

    public HotelNotFoundException(UUID hotelId) {
        super("Hotel not found. hotelId=" + hotelId);
    }
}
