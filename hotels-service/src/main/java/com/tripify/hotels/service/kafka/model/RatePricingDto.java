package com.tripify.hotels.service.kafka.model;

public record RatePricingDto(
        MoneyDto basePrice,
        MoneyDto taxesAndFees,
        MoneyDto totalPrice,
        MoneyDto pricePerNight,
        DiscountDto discount
) {
}
