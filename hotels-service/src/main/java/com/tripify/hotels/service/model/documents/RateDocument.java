package com.tripify.hotels.service.model.documents;

import java.util.List;

public record RateDocument(
        String rateId,
        String providerRateId,
        String title,
        List<String> tags,
        PricingDocument pricing,
        PaymentDocument payment,
        MealPlanDocument mealPlan,
        CancellationPolicyDocument cancellationPolicy,
        AvailabilityDocument availability,
        Boolean instantConfirmation,
        LoyaltyDocument loyalty,
        List<String> perks
) {
}
