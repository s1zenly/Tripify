package com.tripify.hotels.parser.models;

public record ProviderConfig(
        String poolName,
        int corePoolSize,
        int maxPoolSize,
        int queueSize,
        int batchSize,
        int rps
) {
}
