package com.tripify.hotels.parser.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRate {
    private String rateId;
    private String providerRateId;
    private String title;
    private List<String> tags;
    private RatePricing pricing;
    private RatePayment payment;
    private MealPlan mealPlan;
    private CancellationPolicy cancellationPolicy;
    private RateAvailability availability;
    private Boolean instantConfirmation;
    private Loyalty loyalty;
    private List<String> perks;
}
