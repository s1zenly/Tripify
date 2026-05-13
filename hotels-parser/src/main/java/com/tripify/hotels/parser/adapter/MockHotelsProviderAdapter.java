package com.tripify.hotels.parser.adapter;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.tripify.hotels.parser.dto.HotelsResponseDto;
import com.tripify.hotels.parser.models.HotelsProviderAdapter;
import com.tripify.hotels.parser.models.Provider;
import com.tripify.hotels.parser.models.common.*;
import com.tripify.hotels.parser.models.provider.mock.MockBed;
import com.tripify.hotels.parser.models.provider.mock.MockProviderHotel;
import com.tripify.hotels.parser.models.provider.mock.MockReviews;
import com.tripify.hotels.parser.models.provider.mock.MockRoom;
import com.tripify.hotels.parser.models.provider.mock.MockRoomRate;
import org.springframework.stereotype.Component;

@Component
public class MockHotelsProviderAdapter implements HotelsProviderAdapter<MockProviderHotel> {

    @Override
    public HotelsResponseDto adapt(List<MockProviderHotel> providerData) {
        if (providerData == null || providerData.isEmpty()) {
            return HotelsResponseDto.builder()
                    .hotels(List.of())
                    .countryInfo(CountryInfo.builder().alpha2("").build())
                    .providerName(Provider.MOCK.getProviderName())
                    .parsedAt(Instant.now())
                    .providedAt(Instant.now())
                    .totalHotels(0)
                    .build();
        }

        List<Hotel> hotels = providerData.stream()
                .map(this::toHotel)
                .collect(Collectors.toList());

        MockProviderHotel first = providerData.getFirst();
        String countryAlpha2 = first.getCountryCode();

        return HotelsResponseDto.builder()
                .hotels(hotels)
                .countryInfo(CountryInfo.builder().alpha2(countryAlpha2).build())
                .city(first.getLocality())
                .providerName(Provider.MOCK.getProviderName())
                .parsedAt(Instant.now())
                .providedAt(Instant.now())
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
                        Place.builder().title("Cafe").distance(0.2).unit("km").build()))
                .beaches(List.of(
                        Place.builder().title("Beach").distance(1.0).unit("km").build()))
                .build();

        Reviews reviews = toReviews(raw.getReviewSummary());

        TermsPlacement terms = TermsPlacement.builder()
                .checkIn(CheckInOut.builder().afterTime("14:00").beforeTime("00:00").timezone("UTC").build())
                .checkOut(CheckInOut.builder().afterTime("00:00").beforeTime("11:00").timezone("UTC").build())
                .petFriendly(false)
                .partyFriendly(false)
                .build();

        List<Photo> photos = raw.getPhotos() != null
                ? IntStream.range(0, raw.getPhotos().size())
                .mapToObj(i -> Photo.builder()
                        .link(raw.getPhotos().get(i).getUrl())
                        .order(i)
                        .build())
                .collect(Collectors.toList())
                : Collections.emptyList();

        List<Facility> facilities = raw.getAmenities() != null
                ? raw.getAmenities().stream()
                .map(a -> Facility.builder().type(a.getCode()).isFree(a.isFree()).build())
                .collect(Collectors.toList())
                : Collections.emptyList();

        List<Room> rooms = raw.getRooms() != null
                ? raw.getRooms().stream().map(this::toRoom).collect(Collectors.toList())
                : Collections.emptyList();

        return Hotel.builder()
                .hid(hashId(raw.getExternalId()))
                .title(raw.getName())
                .link(raw.getUrl())
                .description(raw.getSummary())
                .address(raw.getStreet())
                .city(raw.getLocality())
                .country(raw.getCountryCode())
                .hotelClass(raw.getStars())
                .gpsCoordinates(gps)
                .nearbyPlaces(nearby)
                .reviews(reviews)
                .score(buildScore(raw, reviews))
                .termsPlacement(terms)
                .photos(photos)
                .facilities(facilities)
                .rooms(rooms)
                .build();
    }


    private Room toRoom(MockRoom raw) {
        RoomArea area = RoomArea.builder()
                .value(raw.getAreaSqm())
                .unit("M2")
                .build();

        List<Photo> photos = raw.getPhotos() != null
                ? IntStream.range(0, raw.getPhotos().size())
                .mapToObj(i -> Photo.builder()
                        .link(raw.getPhotos().get(i).getUrl())
                        .order(i)
                        .build())
                .collect(Collectors.toList())
                : Collections.emptyList();

        List<Bed> beds = raw.getBeds() != null
                ? raw.getBeds().stream()
                .map(b -> Bed.builder().type(b.getBedType()).count(b.getQty()).build())
                .collect(Collectors.toList())
                : Collections.emptyList();

        Bathrooms bathrooms = Bathrooms.builder()
                .count(raw.getBathroomCount())
                .isPrivate(raw.isPrivateBathroom())
                .build();

        RoomOccupancy occupancy = RoomOccupancy.builder()
                .minAdults(1)
                .maxAdults(raw.getMaxAdults())
                .maxChildren(raw.getMaxChildren())
                .maxGuests(raw.getMaxGuests())
                .build();

        List<RoomRate> rates = raw.getRates() != null
                ? raw.getRates().stream().map(this::toRoomRate).collect(Collectors.toList())
                : Collections.emptyList();

        return Room.builder()
                .roomId(UUID.randomUUID().toString())
                .providerRoomId(raw.getId())
                .name(raw.getLabel())
                .roomType(raw.getCategory())
                .description(raw.getInfo())
                .area(area)
                .floor(raw.getFloor())
                .smokingAllowed(raw.isSmoking())
                .views(raw.getViews() != null ? raw.getViews() : Collections.emptyList())
                .photos(photos)
                .beds(beds)
                .bathrooms(bathrooms)
                .occupancy(occupancy)
                .amenities(raw.getAmenityCodes() != null ? raw.getAmenityCodes() : Collections.emptyList())
                .accessibility(raw.getAccessibilityCodes() != null ? raw.getAccessibilityCodes() : Collections.emptyList())
                .rates(rates)
                .build();
    }


    private RoomRate toRoomRate(MockRoomRate raw) {
        Money base = Money.builder().amount(raw.getBaseAmount()).currency(raw.getCurrency()).build();
        Money tax = Money.builder().amount(raw.getTaxAmount()).currency(raw.getCurrency()).build();
        Money total = Money.builder().amount(raw.getTotalAmount()).currency(raw.getCurrency()).build();

        Discount discount = raw.getDiscountPercent() != null
                ? Discount.builder().percent(raw.getDiscountPercent()).build()
                : null;

        RatePricing pricing = RatePricing.builder()
                .basePrice(base)
                .taxesAndFees(tax)
                .totalPrice(total)
                .pricePerNight(base)
                .discount(discount)
                .build();

        RatePayment payment = RatePayment.builder()
                .type(raw.getPaymentType())
                .prepaymentRequired(raw.isPrepay())
                .cards(raw.getAcceptedCards())
                .build();

        MealPlan mealPlan = MealPlan.builder()
                .type(raw.getMeal())
                .description(raw.getMealNote())
                .build();

        Penalty cancelPenalty = Penalty.builder()
                .type(raw.getPenaltyType())
                .build();

        CancellationPolicy cancellation = CancellationPolicy.builder()
                .refundable(raw.isRefundable())
                .freeCancellationUntil(raw.getFreeCancelBefore())
                .cancelPenalty(cancelPenalty)
                .build();

        RateAvailability availability = RateAvailability.builder()
                .roomsLeft(raw.getRoomsLeft())
                .soldOut(raw.isSoldOut())
                .build();

        Loyalty loyalty = Loyalty.builder()
                .pointsEarned(raw.getLoyaltyPoints())
                .build();

        return RoomRate.builder()
                .rateId(UUID.randomUUID().toString())
                .providerRateId(raw.getId())
                .title(raw.getLabel())
                .tags(raw.getTags())
                .pricing(pricing)
                .payment(payment)
                .mealPlan(mealPlan)
                .cancellationPolicy(cancellation)
                .availability(availability)
                .instantConfirmation(raw.isInstantConfirm())
                .loyalty(loyalty)
                .perks(raw.getPerks())
                .build();
    }


    private HotelScore buildScore(MockProviderHotel raw, Reviews reviews) {
        double rating = reviews != null && reviews.getRating() > 0
                ? Math.min(1.0, reviews.getRating() / 5.0)
                : 0.75;
        int stars = raw.getStars() > 0 ? raw.getStars() : 3;
        double price = Math.min(1.0, 0.35 + (6 - stars) * 0.12);
        double location = Math.min(1.0, 0.55 + stars * 0.08);
        int amenityCount = raw.getAmenities() != null ? raw.getAmenities().size() : 0;
        double facilities = Math.min(1.0, amenityCount / 12.0);
        double finalScore = rating * 0.35 + price * 0.25 + location * 0.25 + facilities * 0.15;

        return HotelScore.builder()
                .finalScore(roundScore(finalScore))
                .breakdown(ScoreBreakdown.builder()
                        .price(roundScore(price))
                        .rating(roundScore(rating))
                        .location(roundScore(location))
                        .facilities(roundScore(facilities))
                        .build())
                .build();
    }

    private static double roundScore(double value) {
        return Math.round(value * 10_000.0) / 10_000.0;
    }

    private Reviews toReviews(MockReviews mock) {
        if (mock == null) {
            return Reviews.builder()
                    .total(0).rating(0)
                    .reviewsHistogram(Map.of()).reviewsClasses(Map.of())
                    .comments(List.of()).totalComments(0)
                    .build();
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
                                ? IntStream.range(0, c.getImages().size())
                                .mapToObj(i -> Photo.builder()
                                        .link(c.getImages().get(i).getUrl())
                                        .order(i)
                                        .build())
                                .collect(Collectors.toList())
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


    private static long hashId(String s) {
        long h = 0;
        for (int i = 0; i < s.length(); i++) {
            h = 31 * h + s.charAt(i);
        }
        return Math.abs(h) % 1_000_000_000L;
    }
}
