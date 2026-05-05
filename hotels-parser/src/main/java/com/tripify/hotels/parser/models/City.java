package com.tripify.hotels.parser.models;

import lombok.Getter;

@Getter
public enum City {

    // Russia
    MOSCOW("Moscow", Country.RUSSIA, 55.7558, 37.6173),
    SAINT_PETERSBURG("Saint Petersburg", Country.RUSSIA, 59.9343, 30.3351),
    SOCHI("Sochi", Country.RUSSIA, 43.6028, 39.7342),
    KAZAN("Kazan", Country.RUSSIA, 55.7963, 49.1088),
    YEKATERINBURG("Yekaterinburg", Country.RUSSIA, 56.8389, 60.6057),

    // China
    BEIJING("Beijing", Country.CHINA, 39.9042, 116.4074),
    SHANGHAI("Shanghai", Country.CHINA, 31.2304, 121.4737),
    GUANGZHOU("Guangzhou", Country.CHINA, 23.1291, 113.2644),
    SHENZHEN("Shenzhen", Country.CHINA, 22.5431, 114.0579),
    CHENGDU("Chengdu", Country.CHINA, 30.5728, 104.0668),

    // UAE
    DUBAI("Dubai", Country.UAE, 25.2048, 55.2708),
    ABU_DHABI("Abu Dhabi", Country.UAE, 24.4539, 54.3773),
    SHARJAH("Sharjah", Country.UAE, 25.3463, 55.4209),

    // Turkey
    ISTANBUL("Istanbul", Country.TURKEY, 41.0082, 28.9784),
    ANTALYA("Antalya", Country.TURKEY, 36.8969, 30.7133),
    BODRUM("Bodrum", Country.TURKEY, 37.0344, 27.4305),
    IZMIR("Izmir", Country.TURKEY, 38.4192, 27.1287),
    FETHIYE("Fethiye", Country.TURKEY, 36.6515, 29.1164),

    // Thailand
    BANGKOK("Bangkok", Country.THAILAND, 13.7563, 100.5018),
    PHUKET("Phuket", Country.THAILAND, 7.8804, 98.3923),
    PATTAYA("Pattaya", Country.THAILAND, 12.9236, 100.8825),
    CHIANG_MAI("Chiang Mai", Country.THAILAND, 18.7883, 98.9853),
    KRABI("Krabi", Country.THAILAND, 8.0863, 98.9063),

    // Spain
    BARCELONA("Barcelona", Country.SPAIN, 41.3874, 2.1686),
    MADRID("Madrid", Country.SPAIN, 40.4168, -3.7038),
    MALAGA("Malaga", Country.SPAIN, 36.7213, -4.4214),
    SEVILLE("Seville", Country.SPAIN, 37.3891, -5.9845),
    VALENCIA("Valencia", Country.SPAIN, 39.4699, -0.3763),

    // Italy
    ROME("Rome", Country.ITALY, 41.9028, 12.4964),
    MILAN("Milan", Country.ITALY, 45.4642, 9.1900),
    VENICE("Venice", Country.ITALY, 45.4408, 12.3155),
    FLORENCE("Florence", Country.ITALY, 43.7696, 11.2558),
    NAPLES("Naples", Country.ITALY, 40.8518, 14.2681),

    // France
    PARIS("Paris", Country.FRANCE, 48.8566, 2.3522),
    NICE("Nice", Country.FRANCE, 43.7102, 7.2620),
    LYON("Lyon", Country.FRANCE, 45.7640, 4.8357),
    MARSEILLE("Marseille", Country.FRANCE, 43.2965, 5.3698),
    BORDEAUX("Bordeaux", Country.FRANCE, 44.8378, -0.5792),

    // Japan
    TOKYO("Tokyo", Country.JAPAN, 35.6762, 139.6503),
    OSAKA("Osaka", Country.JAPAN, 34.6937, 135.5023),
    KYOTO("Kyoto", Country.JAPAN, 35.0116, 135.7681),
    YOKOHAMA("Yokohama", Country.JAPAN, 35.4437, 139.6380),
    SAPPORO("Sapporo", Country.JAPAN, 43.0618, 141.3545),

    // Egypt
    CAIRO("Cairo", Country.EGYPT, 30.0444, 31.2357),
    HURGHADA("Hurghada", Country.EGYPT, 27.2579, 33.8116),
    SHARM_EL_SHEIKH("Sharm El Sheikh", Country.EGYPT, 27.9158, 34.3300),
    ALEXANDRIA("Alexandria", Country.EGYPT, 31.2001, 29.9187),
    LUXOR("Luxor", Country.EGYPT, 25.6872, 32.6396);

    private final String displayName;
    private final Country country;
    private final double latitude;
    private final double longitude;

    City(String displayName, Country country, double latitude, double longitude) {
        this.displayName = displayName;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
