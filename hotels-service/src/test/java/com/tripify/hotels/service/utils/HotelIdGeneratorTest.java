package com.tripify.hotels.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class HotelIdGeneratorTest {

    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"
    );

    @Test
    void generate_isDeterministicUuidFromProviderKey() {
        UUID first = HotelIdGenerator.generate("Mock", 7467355L);
        UUID second = HotelIdGenerator.generate("mock", 7467355L);

        assertEquals(first, second);
        assertEquals(
                UUID.nameUUIDFromBytes("mock:7467355".getBytes(StandardCharsets.UTF_8)),
                first
        );
        assertNotEquals(HotelIdGenerator.generate("mock", 7467356L), first);
        assertNotEquals("mock:7467355", first.toString());
        assertEquals(true, UUID_PATTERN.matcher(first.toString()).matches());
    }
}
