package com.tripify.hotels.service.service.mapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tripify.hotels.service.domain.City;
import com.tripify.hotels.service.domain.Country;
import com.tripify.hotels.service.kafka.model.FacilityDto;
import com.tripify.hotels.service.kafka.model.GpsCoordinatesDto;
import com.tripify.hotels.service.kafka.model.HotelScoreDto;
import com.tripify.hotels.service.kafka.model.HotelsParsedEvent;
import com.tripify.hotels.service.kafka.model.KafkaHotelDto;
import com.tripify.hotels.service.kafka.model.MoneyDto;
import com.tripify.hotels.service.kafka.model.NearbyPlaceDto;
import com.tripify.hotels.service.kafka.model.PlacementTimeDto;
import com.tripify.hotels.service.kafka.model.RateDto;
import com.tripify.hotels.service.kafka.model.ReviewsDto;
import com.tripify.hotels.service.kafka.model.RoomDto;
import com.tripify.hotels.service.kafka.model.TermsPlacementDto;
import com.tripify.hotels.service.model.Hotel;
import com.tripify.hotels.service.model.HotelFacility;
import com.tripify.hotels.service.model.HotelNearbyPlace;
import com.tripify.hotels.service.model.HotelReviewsSummary;
import com.tripify.hotels.service.model.HotelScore;
import com.tripify.hotels.service.model.HotelTermsPlacement;
import com.tripify.hotels.service.service.currency.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelPersistenceMapper {

    private final CurrencyConversionService currencyConversion;

    public Hotel toHotel(
            KafkaHotelDto hotelDto,
            HotelsParsedEvent event,
            UUID hotelId,
            Instant now
    ) {
        ReviewsDto reviews = hotelDto.reviews();
        GpsCoordinatesDto gps = hotelDto.gpsCoordinates();
        City city = City.fromCode(defaultString(hotelDto.city()));
        Country country = Country.fromAlpha2(defaultString(hotelDto.country()));

        BigDecimal minPriceUsd = computeMinPriceUsd(hotelDto.rooms());
        int maxGuests = computeMaxGuests(hotelDto.rooms());

        return new Hotel(
                hotelId,
                hotelDto.hid(),
                event.providerName(),
                defaultString(hotelDto.title()),
                defaultString(hotelDto.link()),
                defaultString(hotelDto.description()),
                defaultString(hotelDto.address()),
                city.iataCode(),
                country.alpha2(),
                currencyConversion.storageCurrency(),
                minPriceUsd,
                maxGuests,
                hotelDto.hotelClass() != null ? hotelDto.hotelClass() : 0,
                gps != null && gps.latitude() != null ? gps.latitude() : BigDecimal.ZERO,
                gps != null && gps.longitude() != null ? gps.longitude() : BigDecimal.ZERO,
                reviews != null && reviews.total() != null ? reviews.total() : 0,
                reviews != null && reviews.rating() != null ? reviews.rating() : BigDecimal.ZERO,
                event.parsedAt(),
                event.providedAt(),
                now,
                now
        );
    }

    public List<HotelFacility> toFacilities(UUID hotelId, List<FacilityDto> facilities, Instant now) {
        if (facilities == null || facilities.isEmpty()) {
            return List.of();
        }

        return facilities.stream()
                .filter(facility -> facility.type() != null && !facility.type().isBlank())
                .map(facility -> new HotelFacility(
                        hotelId,
                        facility.type(),
                        Boolean.TRUE.equals(facility.free()),
                        now
                ))
                .toList();
    }

    public List<HotelNearbyPlace> toNearbyPlaces(
            UUID hotelId,
            Map<String, List<NearbyPlaceDto>> nearbyPlaces,
            Instant now
    ) {
        if (nearbyPlaces == null || nearbyPlaces.isEmpty()) {
            return List.of();
        }

        List<HotelNearbyPlace> result = new ArrayList<>();

        nearbyPlaces.forEach((category, places) -> {
            if (places == null) {
                return;
            }

            for (NearbyPlaceDto place : places) {
                if (place.title() == null || place.title().isBlank()) {
                    continue;
                }

                result.add(new HotelNearbyPlace(
                        UUID.randomUUID(),
                        hotelId,
                        category,
                        place.title(),
                        place.distance() != null ? place.distance() : BigDecimal.ZERO,
                        defaultString(place.unit()),
                        now
                ));
            }
        });

        return List.copyOf(result);
    }

    public HotelTermsPlacement toTermsPlacement(UUID hotelId, TermsPlacementDto terms, Instant now) {
        if (terms == null) {
            return null;
        }

        PlacementTimeDto checkIn = terms.checkIn();
        PlacementTimeDto checkOut = terms.checkOut();

        return new HotelTermsPlacement(
                hotelId,
                checkIn != null ? checkIn.afterTime() : null,
                checkIn != null ? checkIn.beforeTime() : null,
                checkOut != null ? checkOut.afterTime() : null,
                checkOut != null ? checkOut.beforeTime() : null,
                checkIn != null ? checkIn.timezone() : null,
                Boolean.TRUE.equals(terms.petFriendly()),
                Boolean.TRUE.equals(terms.partyFriendly()),
                terms.ageRestriction(),
                terms.additionalInfo(),
                now,
                now
        );
    }

    public HotelReviewsSummary toReviewsSummary(UUID hotelId, ReviewsDto reviews, Instant now) {
        if (reviews == null) {
            return null;
        }

        Map<String, Object> reviewsClasses = reviews.reviewsClasses();

        return new HotelReviewsSummary(
                hotelId,
                reviews.rating() != null ? reviews.rating() : BigDecimal.ZERO,
                reviews.total() != null ? reviews.total() : 0,
                toBigDecimal(reviewsClasses, "cleanliness"),
                toBigDecimal(reviewsClasses, "service"),
                toBigDecimal(reviewsClasses, "priceQuality"),
                toBigDecimal(reviewsClasses, "room"),
                toBigDecimal(reviewsClasses, "location"),
                now,
                now
        );
    }

    public HotelScore toHotelScore(UUID hotelId, HotelScoreDto score, Instant now) {
        if (score == null || score.finalScore() == null) {
            return null;
        }

        var breakdown = score.breakdown();

        return new HotelScore(
                hotelId,
                score.finalScore(),
                breakdown != null ? breakdown.price() : null,
                breakdown != null ? breakdown.rating() : null,
                breakdown != null ? breakdown.location() : null,
                breakdown != null ? breakdown.facilities() : null,
                now,
                now
        );
    }

    private BigDecimal computeMinPriceUsd(List<RoomDto> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal minPrice = null;

        for (RoomDto room : rooms) {
            if (room.rates() == null) {
                continue;
            }
            for (RateDto rate : room.rates()) {
                if (rate.pricing() == null || rate.pricing().pricePerNight() == null) {
                    continue;
                }
                MoneyDto perNight = rate.pricing().pricePerNight();
                if (perNight.amount() == null) {
                    continue;
                }

                BigDecimal priceInUsd = currencyConversion.toStorageCurrency(
                        perNight.amount(),
                        perNight.currency()
                );

                if (minPrice == null || priceInUsd.compareTo(minPrice) < 0) {
                    minPrice = priceInUsd;
                }
            }
        }

        return minPrice != null ? minPrice : BigDecimal.ZERO;
    }

    private int computeMaxGuests(List<RoomDto> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return 2;
        }

        int maxGuests = 0;
        for (RoomDto room : rooms) {
            if (room.occupancy() != null && room.occupancy().maxGuests() != null) {
                maxGuests = Math.max(maxGuests, room.occupancy().maxGuests());
            }
        }
        return maxGuests > 0 ? maxGuests : 2;
    }

    private static BigDecimal toBigDecimal(Map<String, Object> values, String key) {
        if (values == null || !values.containsKey(key)) {
            return null;
        }

        Object value = values.get(key);
        return switch (value) {
            case BigDecimal bigDecimal -> bigDecimal;
            case Number number -> BigDecimal.valueOf(number.doubleValue());
            case String string -> new BigDecimal(string);
            case null, default -> null;
        };
    }

    private static String defaultString(String value) {
        return value != null ? value : "";
    }
}
