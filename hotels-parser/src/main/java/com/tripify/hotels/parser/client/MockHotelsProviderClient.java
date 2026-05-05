package com.tripify.hotels.parser.client;

import com.tripify.hotels.parser.models.City;
import com.tripify.hotels.parser.models.HotelsProviderClient;
import com.tripify.hotels.parser.models.provider.mock.MockBed;
import com.tripify.hotels.parser.models.provider.mock.MockComment;
import com.tripify.hotels.parser.models.provider.mock.MockFacility;
import com.tripify.hotels.parser.models.provider.mock.MockPhoto;
import com.tripify.hotels.parser.models.provider.mock.MockProviderHotel;
import com.tripify.hotels.parser.models.provider.mock.MockReviews;
import com.tripify.hotels.parser.models.provider.mock.MockRoom;
import com.tripify.hotels.parser.models.provider.mock.MockRoomRate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class MockHotelsProviderClient implements HotelsProviderClient<MockProviderHotel> {

    private static final Random RND = new Random(42);

    // ─── Hotel names ───
    private static final List<String> HOTEL_PREFIXES = List.of(
            "Grand", "Royal", "Panorama", "Riviera", "Sapphire", "Azure",
            "Golden", "Emerald", "Crystal", "Coral", "Imperial", "Majestic"
    );
    private static final List<String> HOTEL_SUFFIXES = List.of(
            "Palace", "Resort & Spa", "Hotel", "Suites", "Inn", "Lodge",
            "Beach Resort", "Plaza", "Residence", "Boutique Hotel"
    );

    // ─── Street names ───
    private static final List<String> STREETS = List.of(
            "Palm Avenue", "Seaside Boulevard", "Main Street", "Ocean Drive", "Sunset Road",
            "Liberty Square", "Victoria Street", "Harbour Road", "Garden Lane", "King's Road",
            "Republic Street", "Marina Walk", "Hill Street", "Riverside Promenade", "Bay Crescent"
    );

    // ─── Reviewer names ───
    private static final List<String> REVIEWER_NAMES = List.of(
            "Anton Petrov", "Maria Santos", "John Smith", "Elena Müller", "Yuki Tanaka",
            "Ahmed Hassan", "Sophie Laurent", "Marco Rossi", "Anna Kowalska", "Carlos Rivera",
            "Olga Smirnova", "David Chen", "Fatima Al-Rashid", "Priya Sharma", "Lars Andersen",
            "Irina Volkov", "Thomas Baker", "Leila Amiri", "Kenji Nakamura", "Isabella Fernandez"
    );
    private static final List<String> REVIEWER_COUNTRIES = List.of(
            "RU", "BR", "US", "DE", "JP", "EG", "FR", "IT", "PL", "MX",
            "UA", "CN", "IR", "IN", "DK", "KR", "GB", "TR", "ES", "AR"
    );

    // ─── Review content ───
    private static final List<String> TRIP_KINDS = List.of("Leisure", "Business", "Family", "Romantic", "Solo", "Group tour");
    private static final List<String> PROS = List.of(
            "Great location, walking distance to everything",
            "Beautiful sea view from the balcony",
            "Friendly and helpful staff",
            "Excellent breakfast buffet with local cuisine",
            "Modern and clean rooms",
            "Amazing pool and spa area",
            "Very quiet and peaceful atmosphere",
            "Comfortable beds, slept like a baby",
            "Good value for money",
            "Stylish interior design"
    );
    private static final List<String> CONS = List.of(
            "Noisy air conditioning at night",
            "Slow Wi-Fi in the rooms",
            "Small bathroom, limited counter space",
            "Limited parking, had to park on the street",
            "Minibar was overpriced",
            "Check-in took a while",
            "Elevator was slow during peak hours",
            "No room service after 10 PM",
            "Beach towels were not provided",
            "Breakfast could use more variety"
    );
    private static final List<String> REVIEW_TEXTS = List.of(
            "Overall a wonderful stay. Would definitely come back next year.",
            "Good hotel for the price. Nothing fancy but everything was clean and comfortable.",
            "We enjoyed our vacation here. The kids loved the pool and the beach was close by.",
            "Perfect for a short business trip. The location is convenient and the rooms are quiet.",
            "Great experience! The staff went above and beyond to make our anniversary special.",
            "Solid choice for a family vacation. Plenty of activities for children.",
            "The rooftop restaurant has incredible sunset views. Highly recommended.",
            "Nice boutique hotel with a personal touch. Feels like a hidden gem.",
            "Decent stay but nothing extraordinary. The area is very walkable though.",
            "Loved the spa treatments and the gym. Will return for sure."
    );

    // ─── Room data ───
    private static final List<String> ROOM_TYPES = List.of(
            "STANDARD", "SUPERIOR", "DELUXE", "SUITE", "FAMILY", "STUDIO"
    );
    private static final Map<String, String> ROOM_NAMES = Map.of(
            "STANDARD", "Standard Room",
            "SUPERIOR", "Superior Room",
            "DELUXE", "Deluxe Room",
            "SUITE", "Executive Suite",
            "FAMILY", "Family Room",
            "STUDIO", "Studio Apartment"
    );
    private static final Map<String, String> ROOM_DESCRIPTIONS = Map.of(
            "STANDARD", "Comfortable room with all essential amenities for a pleasant stay",
            "SUPERIOR", "Spacious room with upgraded furnishings and a city or garden view",
            "DELUXE", "Elegantly appointed room with premium amenities and a stunning view",
            "SUITE", "Luxurious suite with separate living area, king bed, and panoramic views",
            "FAMILY", "Generously sized room perfect for families with children, includes extra beds",
            "STUDIO", "Modern studio layout with a kitchenette and a cozy workspace"
    );

    private static final List<String> VIEWS = List.of(
            "SEA_VIEW", "CITY_VIEW", "GARDEN_VIEW", "POOL_VIEW", "MOUNTAIN_VIEW"
    );
    private static final List<String> AMENITY_CODES = List.of(
            "AIR_CONDITIONING", "TV", "WIFI", "MINIBAR", "SAFE", "COFFEE_MACHINE",
            "BALCONY", "DESK", "WARDROBE", "IRON", "HAIRDRYER", "TOILETRIES"
    );
    private static final List<String> HOTEL_AMENITY_CODES = List.of(
            "wifi", "parking", "breakfast", "pool", "spa", "gym",
            "restaurant", "bar", "concierge", "laundry", "airport_shuttle", "room_service"
    );

    // ─── Rate data ───
    private static final List<String> RATE_LABELS = List.of(
            "Flexible Rate", "Non-Refundable Deal", "Breakfast Included",
            "Half Board Package", "Early Bird Special", "Last Minute Offer"
    );
    private static final List<String> MEAL_TYPES = List.of(
            "ROOM_ONLY", "BREAKFAST_INCLUDED", "HALF_BOARD", "FULL_BOARD", "ALL_INCLUSIVE"
    );
    private static final List<String> PAYMENT_TYPES = List.of("PAY_NOW", "PAY_AT_PROPERTY", "PARTIALLY_PREPAID");
    private static final List<String> CARD_TYPES = List.of("VISA", "MASTERCARD", "MIR", "AMEX");
    private static final List<String> PERKS = List.of(
            "FREE_WIFI", "FREE_PARKING", "SPA_ACCESS", "LATE_CHECKOUT", "EARLY_CHECKIN"
    );

    @Override
    public List<MockProviderHotel> fetch(City city) {
        int count = 3 + RND.nextInt(4);
        return java.util.stream.IntStream.range(0, count)
                .mapToObj(i -> buildHotel(city, i))
                .toList();
    }

    // ────────────────────────────────────────────────────────────
    //  Hotel generation
    // ────────────────────────────────────────────────────────────

    private MockProviderHotel buildHotel(City city, int idx) {
        String name = pick(HOTEL_PREFIXES) + " " + pick(HOTEL_SUFFIXES) + " " + city.getDisplayName();
        int stars = 3 + RND.nextInt(3);
        double lat = city.getLatitude() + (RND.nextDouble() - 0.5) * 0.05;
        double lng = city.getLongitude() + (RND.nextDouble() - 0.5) * 0.05;
        String alpha2 = city.getCountry().getAlpha2();

        List<MockFacility> facilities = pickSublist(HOTEL_AMENITY_CODES, 4, 7).stream()
                .map(code -> MockFacility.builder().code(code).free(RND.nextBoolean()).build())
                .toList();

        int roomCount = 2 + RND.nextInt(3);
        List<MockRoom> rooms = new ArrayList<>();
        for (int r = 0; r < roomCount; r++) {
            rooms.add(buildRoom(city, idx, r, stars));
        }

        String summary = buildHotelSummary(name, city.getDisplayName(), stars);

        List<String> allCityPhotos = MockHotelPhotos.getPhotosForCity(city);
        List<MockPhoto> hotelPhotos = distributePhotosAsModel(allCityPhotos, idx);

        return MockProviderHotel.builder()
                .externalId("mock-" + alpha2.toLowerCase() + "-" + city.name().toLowerCase() + "-" + (1000 + idx))
                .name(name)
                .url("https://mock-provider.example.com/hotels/" + alpha2.toLowerCase() + "/" + city.name().toLowerCase() + "/" + (1000 + idx))
                .summary(summary)
                .street(pick(STREETS) + " " + (1 + RND.nextInt(120)))
                .locality(city.getDisplayName())
                .countryCode(alpha2)
                .stars(stars)
                .lat(round(lat, 6))
                .lng(round(lng, 6))
                .photos(hotelPhotos)
                .amenities(facilities)
                .reviewSummary(buildReviews(3 + RND.nextInt(4)))
                .rooms(rooms)
                .build();
    }

    private String buildHotelSummary(String hotelName, String cityName, int stars) {
        return switch (stars) {
            case 5 -> "Luxury " + stars + "-star property in the heart of " + cityName +
                    ". " + hotelName + " offers world-class service, fine dining, and breathtaking views.";
            case 4 -> "Upscale " + stars + "-star hotel in " + cityName +
                    ". " + hotelName + " combines modern comfort with excellent amenities and a prime location.";
            default -> "Comfortable " + stars + "-star hotel in " + cityName +
                    ". " + hotelName + " provides great value with clean rooms and friendly staff.";
        };
    }

    // ────────────────────────────────────────────────────────────
    //  Room generation
    // ────────────────────────────────────────────────────────────

    private MockRoom buildRoom(City city, int hotelIdx, int roomIdx, int hotelStars) {
        String roomType = ROOM_TYPES.get(roomIdx % ROOM_TYPES.size());
        double baseArea = switch (roomType) {
            case "SUITE" -> 55 + RND.nextInt(30);
            case "DELUXE" -> 35 + RND.nextInt(15);
            case "FAMILY" -> 40 + RND.nextInt(20);
            case "STUDIO" -> 30 + RND.nextInt(10);
            case "SUPERIOR" -> 28 + RND.nextInt(12);
            default -> 20 + RND.nextInt(10);
        };

        List<MockBed> beds = buildBeds(roomType);
        int maxAdults = beds.stream().mapToInt(b -> b.getQty() * ("SINGLE_BED".equals(b.getBedType()) ? 1 : 2)).sum();
        maxAdults = Math.max(maxAdults, 1);
        int maxChildren = roomType.equals("FAMILY") ? 2 + RND.nextInt(2) : RND.nextInt(2);
        int maxGuests = maxAdults + maxChildren;

        List<String> views = pickSublist(VIEWS, 1, 2);
        List<String> amenities = pickSublist(AMENITY_CODES, 5, 9);
        int floor = 1 + RND.nextInt(hotelStars * 3);

        List<String> roomPhotoPool = MockHotelPhotos.getRoomPhotos();
        int photoCount = 2 + RND.nextInt(4);
        int photoOffset = ((hotelIdx * 6) + (roomIdx * 3)) % roomPhotoPool.size();
        List<MockPhoto> roomPhotos = new ArrayList<>();
        for (int p = 0; p < photoCount; p++) {
            String url = roomPhotoPool.get((photoOffset + p) % roomPhotoPool.size());
            roomPhotos.add(MockPhoto.builder().url(url).order(p).build());
        }

        int rateCount = 1 + RND.nextInt(3);
        List<MockRoomRate> rates = new ArrayList<>();
        for (int ri = 0; ri < rateCount; ri++) {
            rates.add(buildRate(hotelStars, roomType, ri));
        }

        List<String> accessibility = RND.nextInt(4) == 0
                ? List.of("WHEELCHAIR_ACCESSIBLE", "ELEVATOR_ACCESS")
                : List.of();

        return MockRoom.builder()
                .id("room-" + hotelIdx + "-" + roomIdx)
                .label(ROOM_NAMES.getOrDefault(roomType, "Standard Room"))
                .category(roomType)
                .info(ROOM_DESCRIPTIONS.getOrDefault(roomType, "Comfortable room"))
                .areaSqm(round(baseArea, 1))
                .floor(floor)
                .smoking(RND.nextInt(5) == 0)
                .views(views)
                .photos(roomPhotos)
                .beds(beds)
                .bathroomCount(roomType.equals("SUITE") ? 2 : 1)
                .privateBathroom(true)
                .maxAdults(maxAdults)
                .maxChildren(maxChildren)
                .maxGuests(maxGuests)
                .amenityCodes(amenities)
                .accessibilityCodes(accessibility)
                .rates(rates)
                .build();
    }

    private List<MockBed> buildBeds(String roomType) {
        return switch (roomType) {
            case "SUITE" -> List.of(MockBed.builder().bedType("KING_BED").qty(1).build());
            case "DELUXE" -> List.of(MockBed.builder().bedType("QUEEN_BED").qty(1).build());
            case "FAMILY" -> List.of(
                    MockBed.builder().bedType("DOUBLE_BED").qty(1).build(),
                    MockBed.builder().bedType("SINGLE_BED").qty(RND.nextBoolean() ? 1 : 2).build()
            );
            case "STUDIO" -> List.of(MockBed.builder().bedType("SOFA_BED").qty(1).build());
            case "SUPERIOR" -> List.of(MockBed.builder().bedType("DOUBLE_BED").qty(1).build());
            default -> RND.nextBoolean()
                    ? List.of(MockBed.builder().bedType("DOUBLE_BED").qty(1).build())
                    : List.of(MockBed.builder().bedType("SINGLE_BED").qty(2).build());
        };
    }

    // ────────────────────────────────────────────────────────────
    //  Rate generation
    // ────────────────────────────────────────────────────────────

    private MockRoomRate buildRate(int hotelStars, String roomType, int rateIdx) {
        BigDecimal basePerNight = BigDecimal.valueOf(basePriceForRoom(hotelStars, roomType))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = basePerNight.multiply(BigDecimal.valueOf(0.1 + RND.nextDouble() * 0.08))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = basePerNight.add(tax);

        boolean refundable = rateIdx == 0 || RND.nextBoolean();
        Integer discountPercent = RND.nextInt(4) == 0 ? 10 + RND.nextInt(21) : null;
        String meal = MEAL_TYPES.get(RND.nextInt(MEAL_TYPES.size()));
        String mealNote = switch (meal) {
            case "BREAKFAST_INCLUDED" -> "Buffet breakfast 07:00–10:30";
            case "HALF_BOARD" -> "Breakfast and dinner included";
            case "FULL_BOARD" -> "Three meals daily";
            case "ALL_INCLUSIVE" -> "All meals, snacks, and selected beverages";
            default -> null;
        };

        List<String> tags = new ArrayList<>();
        if (!refundable) tags.add("NON_REFUNDABLE");
        if (refundable) tags.add("FREE_CANCELLATION");
        if (meal.equals("BREAKFAST_INCLUDED")) tags.add("BREAKFAST_INCLUDED");
        if (RND.nextInt(5) == 0) tags.add("BESTSELLER");
        if (discountPercent != null) tags.add("BEST_VALUE");
        if (RND.nextInt(6) == 0) tags.add("LIMITED_AVAILABILITY");

        String payType = PAYMENT_TYPES.get(RND.nextInt(PAYMENT_TYPES.size()));
        List<String> cards = pickSublist(CARD_TYPES, 2, 3);
        int roomsLeft = 1 + RND.nextInt(8);

        return MockRoomRate.builder()
                .id("rate-" + rateIdx + "-" + System.nanoTime() % 100000)
                .label(RATE_LABELS.get(rateIdx % RATE_LABELS.size()))
                .tags(tags)
                .baseAmount(basePerNight)
                .taxAmount(tax)
                .totalAmount(total)
                .currency("USD")
                .discountPercent(discountPercent)
                .paymentType(payType)
                .prepay(payType.equals("PAY_NOW"))
                .acceptedCards(cards)
                .meal(meal)
                .mealNote(mealNote)
                .refundable(refundable)
                .freeCancelBefore(refundable ? Instant.now().plus(7 + RND.nextInt(14), ChronoUnit.DAYS) : null)
                .penaltyType(refundable ? "FIRST_NIGHT" : "FULL_STAY")
                .roomsLeft(roomsLeft)
                .soldOut(false)
                .instantConfirm(RND.nextBoolean())
                .loyaltyPoints(50 + RND.nextInt(200))
                .perks(pickSublist(PERKS, 0, 3))
                .build();
    }

    private double basePriceForRoom(int stars, String roomType) {
        double starMultiplier = switch (stars) {
            case 5 -> 2.5;
            case 4 -> 1.6;
            default -> 1.0;
        };
        double typeMultiplier = switch (roomType) {
            case "SUITE" -> 3.0;
            case "DELUXE" -> 1.8;
            case "FAMILY" -> 1.5;
            case "SUPERIOR" -> 1.3;
            case "STUDIO" -> 1.2;
            default -> 1.0;
        };
        double base = 60 + RND.nextInt(40);
        return base * starMultiplier * typeMultiplier;
    }

    // ────────────────────────────────────────────────────────────
    //  Reviews generation
    // ────────────────────────────────────────────────────────────

    private MockReviews buildReviews(int commentCount) {
        double score = 3.5 + RND.nextDouble() * 1.5;
        int total = 20 + RND.nextInt(300);

        int s5 = 15 + RND.nextInt(30);
        int s4 = 20 + RND.nextInt(20);
        int s3 = 15 + RND.nextInt(15);
        int s2 = 5 + RND.nextInt(10);
        int s1 = 100 - s5 - s4 - s3 - s2;
        Map<String, Integer> distribution = Map.of("5", s5, "4", s4, "3", s3, "2", s2, "1", Math.max(s1, 0));

        List<MockComment> comments = new ArrayList<>();
        for (int c = 0; c < commentCount; c++) {
            int nameIdx = RND.nextInt(REVIEWER_NAMES.size());
            double reviewScore = 3.0 + RND.nextDouble() * 2.0;
            comments.add(MockComment.builder()
                    .userName(REVIEWER_NAMES.get(nameIdx))
                    .userCountry(REVIEWER_COUNTRIES.get(nameIdx % REVIEWER_COUNTRIES.size()))
                    .tripKind(pick(TRIP_KINDS))
                    .score(round(reviewScore, 1))
                    .pros(pick(PROS))
                    .cons(pick(CONS))
                    .text(pick(REVIEW_TEXTS))
                    .date(LocalDate.now().minusDays(1 + RND.nextInt(180)))
                    .images(RND.nextInt(3) == 0
                            ? buildReviewPhotos()
                            : List.of())
                    .build());
        }

        return MockReviews.builder()
                .count(total)
                .score(round(score, 1))
                .distribution(distribution)
                .items(comments)
                .build();
    }

    // ────────────────────────────────────────────────────────────
    //  Helpers
    // ────────────────────────────────────────────────────────────

    private List<MockPhoto> distributePhotosAsModel(List<String> allPhotos, int hotelIdx) {
        if (allPhotos.isEmpty()) return List.of();
        int photosPerHotel = 3 + RND.nextInt(3);
        int offset = (hotelIdx * 3) % allPhotos.size();
        List<MockPhoto> result = new ArrayList<>();
        for (int i = 0; i < photosPerHotel; i++) {
            result.add(MockPhoto.builder()
                    .url(allPhotos.get((offset + i) % allPhotos.size()))
                    .order(i)
                    .build());
        }
        return result;
    }

    private List<MockPhoto> buildReviewPhotos() {
        List<String> pool = MockHotelPhotos.getReviewPhotos();
        int count = 1 + RND.nextInt(3);
        int offset = RND.nextInt(pool.size());
        List<MockPhoto> photos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            photos.add(MockPhoto.builder()
                    .url(pool.get((offset + i) % pool.size()))
                    .order(i)
                    .build());
        }
        return photos;
    }

    private <T> T pick(List<T> list) {
        return list.get(RND.nextInt(list.size()));
    }

    private <T> List<T> pickSublist(List<T> source, int min, int max) {
        int count = Math.min(min + RND.nextInt(max - min + 1), source.size());
        List<T> shuffled = new ArrayList<>(source);
        java.util.Collections.shuffle(shuffled, RND);
        return shuffled.subList(0, count);
    }

    private static double round(double value, int places) {
        return BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
}
