package com.tripify.hotels.service.model.documents;

public record PricingDocument(
        MoneyDocument basePrice,
        MoneyDocument taxesAndFees,
        MoneyDocument totalPrice,
        MoneyDocument pricePerNight,
        DiscountDocument discount
) {
}
