package com.tripify.hotels.service.utils;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.UUID;

public final class HotelIdGenerator {

    private static final String DELIMITER = ":";

    private HotelIdGenerator() {
    }

    /**
     * Детерминированный Tripify id: {@link UUID#nameUUIDFromBytes(byte[])} от {@code provider:externalHotelId}.
     */
    public static UUID generate(String providerName, long externalHotelId) {
        String key = providerName.toLowerCase(Locale.ROOT) + DELIMITER + externalHotelId;
        return UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    }
}
