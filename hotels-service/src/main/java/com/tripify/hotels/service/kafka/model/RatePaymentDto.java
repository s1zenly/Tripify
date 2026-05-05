package com.tripify.hotels.service.kafka.model;

import java.util.List;

public record RatePaymentDto(
        String type,
        Boolean prepaymentRequired,
        List<String> cards
) {
}
