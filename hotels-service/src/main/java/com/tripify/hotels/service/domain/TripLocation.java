package com.tripify.hotels.service.domain;

public final class TripLocation {

    private TripLocation() {
    }

    public static ResolvedTripLocation resolve(String countryCode, String cityCode) {
        if (countryCode == null || countryCode.isBlank()) {
            throw new IllegalArgumentException("country is required");
        }
        if (cityCode == null || cityCode.isBlank()) {
            throw new IllegalArgumentException("city is required");
        }

        Country country = Country.fromAlpha2(countryCode);
        City city = City.fromCode(cityCode);

        if (city.country() != country) {
            throw new IllegalArgumentException(
                    "City " + city.iataCode() + " does not belong to country " + country.alpha2());
        }

        return new ResolvedTripLocation(country, city);
    }
}
