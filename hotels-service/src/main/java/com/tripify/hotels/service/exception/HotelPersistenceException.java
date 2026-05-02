package com.tripify.hotels.service.exception;

public class HotelPersistenceException extends RuntimeException {

    private final Long externalHotelId;

    public HotelPersistenceException(Long externalHotelId, String message, Throwable cause) {
        super(message, cause);
        this.externalHotelId = externalHotelId;
    }

    public HotelPersistenceException(Long externalHotelId, String message) {
        super(message);
        this.externalHotelId = externalHotelId;
    }

    public Long externalHotelId() {
        return externalHotelId;
    }
}
