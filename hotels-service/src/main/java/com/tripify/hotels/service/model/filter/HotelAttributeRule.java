package com.tripify.hotels.service.model.filter;

import java.math.BigDecimal;

public record HotelAttributeRule(
        Integer minHotelClass,
        Integer maxHotelClass,
        BigDecimal minReviewsRating
) {

    public static HotelAttributeRule minStars(int min) {
        return new HotelAttributeRule(min, null, null);
    }

    public static HotelAttributeRule exactStars(int stars) {
        return new HotelAttributeRule(stars, stars, null);
    }

    public static HotelAttributeRule minRating(double minRating) {
        return new HotelAttributeRule(null, null, BigDecimal.valueOf(minRating));
    }
}
