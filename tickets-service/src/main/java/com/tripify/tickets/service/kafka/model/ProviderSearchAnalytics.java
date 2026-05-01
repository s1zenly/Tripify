package com.tripify.tickets.service.kafka.model;

public record ProviderSearchAnalytics(
        String code,
        String status,
        int offerCount,
        long durationMs,
        String error
) {
}
