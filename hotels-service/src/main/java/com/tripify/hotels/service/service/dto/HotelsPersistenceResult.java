package com.tripify.hotels.service.service.dto;

import java.util.List;

public record HotelsPersistenceResult(
        int totalHotels,
        int savedHotels,
        int failedHotels,
        List<HotelPersistenceFailure> failures
) {
    public boolean hasFailures() {
        return failedHotels > 0;
    }

    public boolean isFullySuccessful() {
        return failedHotels == 0 && savedHotels == totalHotels;
    }
}
