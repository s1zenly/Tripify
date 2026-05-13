package com.tripify.hotels.service.domain;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum City {
    MOSCOW("MOW", Country.RUSSIA),
    SAINT_PETERSBURG("LED", Country.RUSSIA),
    SOCHI("AER", Country.RUSSIA),
    KAZAN("KZN", Country.RUSSIA),
    YEKATERINBURG("SVX", Country.RUSSIA),

    BEIJING("BJS", Country.CHINA),
    SHANGHAI("SHA", Country.CHINA),
    GUANGZHOU("CAN", Country.CHINA),
    SHENZHEN("SZX", Country.CHINA),
    CHENGDU("CTU", Country.CHINA),

    DUBAI("DXB", Country.UAE),
    ABU_DHABI("AUH", Country.UAE),
    SHARJAH("SHJ", Country.UAE),

    ISTANBUL("IST", Country.TURKEY),
    ANTALYA("AYT", Country.TURKEY),
    BODRUM("BJV", Country.TURKEY),
    IZMIR("IZM", Country.TURKEY),
    FETHIYE("DLM", Country.TURKEY),

    BANGKOK("BKK", Country.THAILAND),
    PHUKET("HKT", Country.THAILAND),
    PATTAYA("UTP", Country.THAILAND),
    CHIANG_MAI("CNX", Country.THAILAND),
    KRABI("KBV", Country.THAILAND),

    BARCELONA("BCN", Country.SPAIN),
    MADRID("MAD", Country.SPAIN),
    MALAGA("AGP", Country.SPAIN),
    SEVILLE("SVQ", Country.SPAIN),
    VALENCIA("VLC", Country.SPAIN),

    ROME("ROM", Country.ITALY),
    MILAN("MIL", Country.ITALY),
    VENICE("VCE", Country.ITALY),
    FLORENCE("FLR", Country.ITALY),
    NAPLES("NAP", Country.ITALY),

    PARIS("PAR", Country.FRANCE),
    NICE("NCE", Country.FRANCE),
    LYON("LYS", Country.FRANCE),
    MARSEILLE("MRS", Country.FRANCE),
    BORDEAUX("BOD", Country.FRANCE),

    TOKYO("TYO", Country.JAPAN),
    OSAKA("OSA", Country.JAPAN),
    KYOTO("UKY", Country.JAPAN),
    YOKOHAMA("YOK", Country.JAPAN),
    SAPPORO("SPK", Country.JAPAN),

    CAIRO("CAI", Country.EGYPT),
    HURGHADA("HRG", Country.EGYPT),
    SHARM_EL_SHEIKH("SSH", Country.EGYPT),
    ALEXANDRIA("HBE", Country.EGYPT),
    LUXOR("LXR", Country.EGYPT);

    private final String iataCode;
    private final Country country;

    City(String iataCode, Country country) {
        this.iataCode = iataCode;
        this.country = country;
    }

    public String iataCode() {
        return iataCode;
    }

    public Country country() {
        return country;
    }

    private static final Map<String, City> BY_IATA = buildKeys();

    public static City fromCode(String code) {
        City city = BY_IATA.get(normalize(code));
        if (city == null) {
            throw new IllegalArgumentException("Unknown city: " + code);
        }
        return city;
    }

    private static Map<String, City> buildKeys() {
        Map<String, City> keys = new HashMap<>();

        for (City city : values()) {
            keys.put(normalize(city.iataCode), city);
        }

        return Map.copyOf(keys);
    }

    private static String normalize(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }
}
