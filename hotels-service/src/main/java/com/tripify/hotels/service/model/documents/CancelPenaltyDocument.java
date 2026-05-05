package com.tripify.hotels.service.model.documents;

public record CancelPenaltyDocument(
        String type,
        Double amount,
        Integer percent
) {
}
