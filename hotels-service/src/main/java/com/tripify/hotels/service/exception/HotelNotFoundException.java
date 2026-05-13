package com.tripify.hotels.service.exception;

import java.util.UUID;

public class HotelNotFoundException extends RuntimeException {

    public HotelNotFoundException(UUID hotelId) {
        super("Hotel not found. hotelId=" + hotelId);
    }

    public static HotelNotFoundException forEmptySearch() {
        return new HotelNotFoundException("No hotels found for search criteria");
    }

    private HotelNotFoundException(String message) {
        super(message);
    }
}
