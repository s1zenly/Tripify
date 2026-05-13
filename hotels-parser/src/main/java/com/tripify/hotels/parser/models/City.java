package com.tripify.hotels.parser.models;

import lombok.Getter;

@Getter
public enum City {

    MOSCOW("Moscow", "MOW", Country.RUSSIA, 55.7558, 37.6173),
    SAINT_PETERSBURG("Saint Petersburg", "LED", Country.RUSSIA, 59.9343, 30.3351),
    SOCHI("Sochi", "AER", Country.RUSSIA, 43.6028, 39.7342),
    KAZAN("Kazan", "KZN", Country.RUSSIA, 55.7963, 49.1088),
    YEKATERINBURG("Yekaterinburg", "SVX", Country.RUSSIA, 56.8389, 60.6057),

    BEIJING("Beijing", "BJS", Country.CHINA, 39.9042, 116.4074),
    SHANGHAI("Shanghai", "SHA", Country.CHINA, 31.2304, 121.4737),
    GUANGZHOU("Guangzhou", "CAN", Country.CHINA, 23.1291, 113.2644),
    SHENZHEN("Shenzhen", "SZX", Country.CHINA, 22.5431, 114.0579),
    CHENGDU("Chengdu", "CTU", Country.CHINA, 30.5728, 104.0668),

    DUBAI("Dubai", "DXB", Country.UAE, 25.2048, 55.2708),
    ABU_DHABI("Abu Dhabi", "AUH", Country.UAE, 24.4539, 54.3773),
    SHARJAH("Sharjah", "SHJ", Country.UAE, 25.3463, 55.4209),

    ISTANBUL("Istanbul", "IST", Country.TURKEY, 41.0082, 28.9784),
    ANTALYA("Antalya", "AYT", Country.TURKEY, 36.8969, 30.7133),
    BODRUM("Bodrum", "BJV", Country.TURKEY, 37.0344, 27.4305),
    IZMIR("Izmir", "IZM", Country.TURKEY, 38.4192, 27.1287),
    FETHIYE("Fethiye", "DLM", Country.TURKEY, 36.6515, 29.1164),

    BANGKOK("Bangkok", "BKK", Country.THAILAND, 13.7563, 100.5018),
    PHUKET("Phuket", "HKT", Country.THAILAND, 7.8804, 98.3923),
    PATTAYA("Pattaya", "UTP", Country.THAILAND, 12.9236, 100.8825),
    CHIANG_MAI("Chiang Mai", "CNX", Country.THAILAND, 18.7883, 98.9853),
    KRABI("Krabi", "KBV", Country.THAILAND, 8.0863, 98.9063),

    BARCELONA("Barcelona", "BCN", Country.SPAIN, 41.3874, 2.1686),
    MADRID("Madrid", "MAD", Country.SPAIN, 40.4168, -3.7038),
    MALAGA("Malaga", "AGP", Country.SPAIN, 36.7213, -4.4214),
    SEVILLE("Seville", "SVQ", Country.SPAIN, 37.3891, -5.9845),
    VALENCIA("Valencia", "VLC", Country.SPAIN, 39.4699, -0.3763),

    ROME("Rome", "ROM", Country.ITALY, 41.9028, 12.4964),
    MILAN("Milan", "MIL", Country.ITALY, 45.4642, 9.1900),
    VENICE("Venice", "VCE", Country.ITALY, 45.4408, 12.3155),
    FLORENCE("Florence", "FLR", Country.ITALY, 43.7696, 11.2558),
    NAPLES("Naples", "NAP", Country.ITALY, 40.8518, 14.2681),

    PARIS("Paris", "PAR", Country.FRANCE, 48.8566, 2.3522),
    NICE("Nice", "NCE", Country.FRANCE, 43.7102, 7.2620),
    LYON("Lyon", "LYS", Country.FRANCE, 45.7640, 4.8357),
    MARSEILLE("Marseille", "MRS", Country.FRANCE, 43.2965, 5.3698),
    BORDEAUX("Bordeaux", "BOD", Country.FRANCE, 44.8378, -0.5792),

    TOKYO("Tokyo", "TYO", Country.JAPAN, 35.6762, 139.6503),
    OSAKA("Osaka", "OSA", Country.JAPAN, 34.6937, 135.5023),
    KYOTO("Kyoto", "UKY", Country.JAPAN, 35.0116, 135.7681),
    YOKOHAMA("Yokohama", "YOK", Country.JAPAN, 35.4437, 139.6380),
    SAPPORO("Sapporo", "SPK", Country.JAPAN, 43.0618, 141.3545),

    CAIRO("Cairo", "CAI", Country.EGYPT, 30.0444, 31.2357),
    HURGHADA("Hurghada", "HRG", Country.EGYPT, 27.2579, 33.8116),
    SHARM_EL_SHEIKH("Sharm El Sheikh", "SSH", Country.EGYPT, 27.9158, 34.3300),
    ALEXANDRIA("Alexandria", "HBE", Country.EGYPT, 31.2001, 29.9187),
    LUXOR("Luxor", "LXR", Country.EGYPT, 25.6872, 32.6396);

    private final String displayName;
    private final String iataCode;
    private final Country country;
    private final double latitude;
    private final double longitude;

    City(String displayName, String iataCode, Country country, double latitude, double longitude) {
        this.displayName = displayName;
        this.iataCode = iataCode;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
