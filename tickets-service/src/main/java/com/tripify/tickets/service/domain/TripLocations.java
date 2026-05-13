package com.tripify.tickets.service.domain;

import com.tripify.tickets.service.exception.BadRequestException;

public final class TripLocations {

    private TripLocations() {
    }

    public static ResolvedTripLocation resolveOrBadRequest(String countryCode, String cityCode) {
        try {
            return TripLocation.resolve(countryCode, cityCode);
        } catch (IllegalArgumentException exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }
}
