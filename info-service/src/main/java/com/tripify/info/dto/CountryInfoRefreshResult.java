package com.tripify.info.dto;

public record CountryInfoRefreshResult(
        int updatedCount,
        int failedCount
) {
}
