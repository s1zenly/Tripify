package com.tripify.tickets.service.domain;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Country {
    RUSSIA("RU"),
    CHINA("CN"),
    UAE("AE"),
    TURKEY("TR"),
    THAILAND("TH"),
    SPAIN("ES"),
    ITALY("IT"),
    FRANCE("FR"),
    JAPAN("JP"),
    EGYPT("EG");

    private final String alpha2;

    Country(String alpha2) {
        this.alpha2 = alpha2;
    }

    public String alpha2() {
        return alpha2;
    }

    private static final Map<String, Country> BY_ALPHA2 = buildKeys();

    public static Country fromAlpha2(String code) {
        Country country = BY_ALPHA2.get(normalize(code));
        if (country == null) {
            throw new IllegalArgumentException("Unknown country: " + code);
        }
        return country;
    }

    private static Map<String, Country> buildKeys() {
        Map<String, Country> keys = new HashMap<>();

        for (Country country : values()) {
            keys.put(normalize(country.alpha2), country);
        }

        return Map.copyOf(keys);
    }

    private static String normalize(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }
}
