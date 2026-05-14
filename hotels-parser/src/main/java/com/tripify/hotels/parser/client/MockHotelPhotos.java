package com.tripify.hotels.parser.client;

import com.tripify.hotels.parser.models.City;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class MockHotelPhotos {

    private static final String BASE_URL = "https://storage.yandexcloud.net/test-hotels-images/";
    private static final Map<City, List<String>> PHOTOS_BY_CITY = new EnumMap<>(City.class);

    static {
        Map<String, List<String>> byPrefix = new java.util.HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(
                        MockHotelPhotos.class.getClassLoader().getResourceAsStream("mock-hotel-photos.txt"),
                        "mock-hotel-photos.txt not found on classpath"),
                StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String prefix = line.substring(0, line.lastIndexOf('/'));
                byPrefix.computeIfAbsent(prefix, k -> new ArrayList<>()).add(BASE_URL + line);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to load mock hotel photos: " + e.getMessage());
        }

        for (City city : City.values()) {
            String prefix = s3Prefix(city);
            PHOTOS_BY_CITY.put(city, List.copyOf(byPrefix.getOrDefault(prefix, List.of())));
        }
    }

    private MockHotelPhotos() {
    }

    public static List<String> getPhotosForCity(City city) {
        return PHOTOS_BY_CITY.getOrDefault(city, List.of());
    }

    private static String s3Prefix(City city) {
        return switch (city) {
            case ALEXANDRIA -> "EG/ALY";
            case IZMIR -> "TR/ADB";
            default -> city.getCountry().getAlpha2() + "/" + city.getIataCode();
        };
    }
}
