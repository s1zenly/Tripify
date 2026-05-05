package com.tripify.hotels.parser.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatePricing {
    private Money basePrice;
    private Money taxesAndFees;
    private Money totalPrice;
    private Money pricePerNight;
    private Discount discount;
}
