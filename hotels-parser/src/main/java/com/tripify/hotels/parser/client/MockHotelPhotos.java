package com.tripify.hotels.parser.client;

import com.tripify.hotels.parser.models.City;

import java.util.List;

public final class MockHotelPhotos {

    private MockHotelPhotos() {
    }

    private static final String BASE_URL = "https://storage.yandexcloud.net/test-hotels-images/";

    private static final List<String> PHOTOS = List.of(
            BASE_URL + "441631414.jpg",
            BASE_URL + "462504778.jpg",
            BASE_URL + "551027058.jpg",
            BASE_URL + "632433367.jpg",
            BASE_URL + "632433387.jpg"
    );

    public static List<String> getPhotosForCity(City city) {
        return PHOTOS;
    }

    public static List<String> getRoomPhotos() {
        return PHOTOS;
    }

    public static List<String> getReviewPhotos() {
        return PHOTOS;
    }
}
