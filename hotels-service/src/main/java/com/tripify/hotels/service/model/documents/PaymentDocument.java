package com.tripify.hotels.service.model.documents;

import java.util.List;

public record PaymentDocument(
        String type,
        Boolean prepaymentRequired,
        List<String> cards
) {
}
