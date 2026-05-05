package com.tripify.hotels.parser.models;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Country {

    RUSSIA("Russia", "RU", "RUS", 643),
    CHINA("China", "CN", "CHN", 156),
    UAE("United Arab Emirates", "AE", "ARE", 784),
    TURKEY("Turkey", "TR", "TUR", 792),
    THAILAND("Thailand", "TH", "THA", 764),
    SPAIN("Spain", "ES", "ESP", 724),
    ITALY("Italy", "IT", "ITA", 380),
    FRANCE("France", "FR", "FRA", 250),
    JAPAN("Japan", "JP", "JPN", 392),
    EGYPT("Egypt", "EG", "EGY", 818);

    private final String displayName;
    private final String alpha2;
    private final String alpha3;
    private final int numeric;

    Country(String displayName, String alpha2, String alpha3, int numeric) {
        this.displayName = displayName;
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
        this.numeric = numeric;
    }

    public List<City> getCities() {
        return Arrays.stream(City.values())
                .filter(c -> c.getCountry() == this)
                .toList();
    }

    public static Country fromAlpha2(String code) {
        for (Country c : values()) {
            if (c.alpha2.equalsIgnoreCase(code)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown country code: " + code);
    }
}
