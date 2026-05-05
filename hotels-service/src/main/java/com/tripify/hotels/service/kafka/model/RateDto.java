package com.tripify.hotels.service.kafka.model;

import java.util.List;

public record RateDto(
        String rateId,
        String providerRateId,
        String title,
        List<String> tags,
        RatePricingDto pricing,
        RatePaymentDto payment,
        MealPlanDto mealPlan,
        CancellationPolicyDto cancellationPolicy,
        RateAvailabilityDto availability,
        Boolean instantConfirmation,
        LoyaltyDto loyalty,
        List<String> perks
) {
}
