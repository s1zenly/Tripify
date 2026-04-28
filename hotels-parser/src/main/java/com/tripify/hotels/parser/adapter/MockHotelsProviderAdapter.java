package com.tripify.hotels.parser.adapter;

import com.tripify.hotels.parser.dto.HotelsResponseDto;
import com.tripify.hotels.parser.models.Provider;
import com.tripify.hotels.parser.models.common.*;
import com.tripify.hotels.parser.models.HotelsProviderAdapter;
import com.tripify.hotels.parser.models.provider.mock.MockProviderHotel;
import com.tripify.hotels.parser.models.provider.mock.MockReviews;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mock-адаптер: приводит DTO поставщика (MockProviderHotel) к общему HotelsResponseDto.
 */
@Component
public class MockHotelsProviderAdapter implements HotelsProviderAdapter<MockProviderHotel> {

    @Override
    public HotelsResponseDto adapt(List<MockProviderHotel> providerData) {
        if (providerData == null || providerData.isEmpty()) {
            return HotelsResponseDto.builder()
                    .hotels(List.of())
                    .countryInfo(CountryInfo.builder().title("").alpha2("").build())
                    .providerName(Provider.MOCK.getProviderName())
                    .parsedAt(LocalDateTime.now())
                    .providedAt(LocalDateTime.now())
                    .totalHotels(0)
                    .build();
        }

        List<Hotel> hotels = providerData.stream()
                .map(this::toHotel)
                .collect(Collectors.toList());

        String countryAlpha2 = providerData.getFirst().getCountryCode();
        CountryInfo countryInfo = CountryInfo.builder()
                .title(countryAlpha2)
                .alpha2(countryAlpha2)
                .build();

        return HotelsResponseDto.builder()
                .hotels(hotels)
                .countryInfo(countryInfo)
                .providerName(Provider.MOCK.getProviderName())
                .parsedAt(LocalDateTime.now())
                .providedAt(LocalDateTime.now())
                .totalHotels(hotels.size())
                .build();
    }

    private Hotel toHotel(MockProviderHotel raw) {
        GpsCoordinates gps = GpsCoordinates.builder()
                .latitude(raw.getLat())
                .longitude(raw.getLng())
                .build();

        NearbyPlaces nearby = NearbyPlaces.builder()
                .food(List.of(
                        Place.builder().title("Restaurant").distance(0.5).unit("km").build(),
                        Place.builder().title("Cafe").distance(0.2).unit("km").build()
                ))
                .beaches(List.of(Place.builder().title("Beach").distance(1.0).unit("km").build()))
                .build();

        Reviews reviews = toReviews(raw.getReviewSummary());
        TermsPlacement terms = TermsPlacement.builder()
                .checkIn(CheckInOut.builder().afterTime("14:00").beforeTime("00:00").timezone("UTC").build())
                .checkOut(CheckInOut.builder().afterTime("00:00").beforeTime("11:00").timezone("UTC").build())
                .cancellation(true)
                .refundRule(RefundRule.builder()
                        .refundPrepayment(true)
                        .conditions(List.of(
                                Condition.builder().quantityPercent(100).condition("Free cancellation 24h before").build()))
                        .build())
                .smoking(false)
                .petFriendly(false)
                .partyFriendly(false)
                .build();

        PaymentMethods payment = PaymentMethods.builder()
                .cashInfo(CashInfo.builder().isCash(true).currency(List.of(raw.getPriceCurrency())).build())
                .cardsInfo(CardsInfo.builder().isCard(true).cardTypes(List.of("Visa", "MasterCard")).build())
                .build();

        List<Facility> facilities = raw.getAmenities() != null
                ? raw.getAmenities().stream()
                .map(a -> Facility.builder().type(a.getCode()).isFree(a.isFree()).build())
                .collect(Collectors.toList())
                : Collections.emptyList();

        return Hotel.builder()
                .hid(hashId(raw.getExternalId()))
                .title(raw.getName())
                .link(raw.getUrl())
                .description(raw.getSummary())
                .address(raw.getStreet())
                .city(raw.getLocality())
                .country(raw.getCountryCode())
                .currency(raw.getPriceCurrency())
                .price(raw.getPriceAmount())
                .hotelClass(raw.getStars())
                .gpsCoordinates(gps)
                .nearbyPlaces(nearby)
                .reviews(reviews)
                .termsPlacement(terms)
                .paymentMethods(payment)
                .facilities(facilities)
                .build();
    }

    private static long hashId(String s) {
        long h = 0;
        for (int i = 0; i < s.length(); i++) {
            h = 31 * h + s.charAt(i);
        }
        return Math.abs(h) % 1_000_000_000L;
    }

    private Reviews toReviews(MockReviews mock) {
        if (mock == null) {
            return Reviews.builder().total(0).rating(0).reviewsHistogram(Map.of()).reviewsClasses(Map.of()).comments(List.of()).totalComments(0).build();
        }
        List<Comment> comments = mock.getItems() != null
                ? mock.getItems().stream()
                .map(c -> Comment.builder()
                        .author(c.getUserName())
                        .country(c.getUserCountry())
                        .vacationType(c.getTripKind())
                        .rating(c.getScore())
                        .goodPart(c.getPros())
                        .badPart(c.getCons())
                        .commonText(c.getText())
                        .reviewDate(c.getDate())
                        .photos(c.getImages() != null
                                ? c.getImages().stream().map(p -> Photo.builder().link(p.getUrl()).build()).collect(Collectors.toList())
                                : List.of())
                        .build())
                .collect(Collectors.toList())
                : List.of();
        return Reviews.builder()
                .total(mock.getCount())
                .rating(mock.getScore())
                .reviewsHistogram(mock.getDistribution() != null ? mock.getDistribution() : Map.of())
                .reviewsClasses(Map.of())
                .comments(comments)
                .totalComments(comments.size())
                .build();
    }
}
