package com.tripify.info.domain;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.tripify.info.exception.InfoErrorCode;
import com.tripify.info.exception.InfoServiceException;

public enum Country {
    RUSSIA("RU", "Россия", "rossiya", "RUB", 55.7558, 37.6173),
    CHINA("CN", "Китай", "china", "CNY", 39.9042, 116.4074),
    UAE("AE", "ОАЭ", "united_arab_emirates", "AED", 24.4539, 54.3773),
    TURKEY("TR", "Турция", "turkey", "TRY", 39.9334, 32.8597),
    THAILAND("TH", "Таиланд", "thailand", "THB", 13.7563, 100.5018),
    SPAIN("ES", "Испания", "spain", "EUR", 40.4168, -3.7038),
    ITALY("IT", "Италия", "italy", "EUR", 41.9028, 12.4964),
    FRANCE("FR", "Франция", "france", "EUR", 48.8566, 2.3522),
    JAPAN("JP", "Япония", "japan", "JPY", 35.6762, 139.6503),
    EGYPT("EG", "Египет", "egypt", "EGP", 30.0444, 31.2357);

    private static final Map<String, Country> BY_ALPHA2 = buildIndex();

    private final String alpha2;
    private final String displayName;
    private final String tutuSlug;
    private final String defaultLocalCurrency;
    private final double weatherLatitude;
    private final double weatherLongitude;

    Country(
            String alpha2,
            String displayName,
            String tutuSlug,
            String defaultLocalCurrency,
            double weatherLatitude,
            double weatherLongitude
    ) {
        this.alpha2 = alpha2;
        this.displayName = displayName;
        this.tutuSlug = tutuSlug;
        this.defaultLocalCurrency = defaultLocalCurrency;
        this.weatherLatitude = weatherLatitude;
        this.weatherLongitude = weatherLongitude;
    }

    public String alpha2() {
        return alpha2;
    }

    public String displayName() {
        return displayName;
    }

    public String tutuSourceUrl() {
        return "https://www.tutu.ru/geo/strana/" + tutuSlug + "/";
    }

    public String defaultLocalCurrency() {
        return defaultLocalCurrency;
    }

    public double weatherLatitude() {
        return weatherLatitude;
    }

    public double weatherLongitude() {
        return weatherLongitude;
    }

    public static Country fromAlpha2(String code) {
        Country country = BY_ALPHA2.get(normalize(code));
        if (country == null) {
            throw new InfoServiceException(
                    InfoErrorCode.INVALID_COUNTRY_CODE,
                    "Unsupported country code: " + code
            );
        }
        return country;
    }

    private static Map<String, Country> buildIndex() {
        Map<String, Country> index = new HashMap<>();
        for (Country country : values()) {
            index.put(country.alpha2, country);
        }
        return Map.copyOf(index);
    }

    private static String normalize(String code) {
        if (code == null) {
            throw new InfoServiceException(
                    InfoErrorCode.INVALID_COUNTRY_CODE,
                    "Country code must be ISO 3166-1 alpha-2"
            );
        }
        return code.trim().toUpperCase(Locale.ROOT);
    }
}
