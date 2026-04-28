package com.tripify.hotels.parser.client;

import com.tripify.hotels.parser.models.Country;
import com.tripify.hotels.parser.models.HotelsProviderClient;
import com.tripify.hotels.parser.models.provider.mock.MockComment;
import com.tripify.hotels.parser.models.provider.mock.MockFacility;
import com.tripify.hotels.parser.models.provider.mock.MockProviderHotel;
import com.tripify.hotels.parser.models.provider.mock.MockReviews;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * Mock-клиент: возвращает случайные данные по стране (без внешнего API).
 */
@Component
public class MockHotelsProviderClient implements HotelsProviderClient<MockProviderHotel> {

    private static final Random RANDOM = new Random(42);
    private static final List<String> NAMES = List.of(
            "Grand Plaza Hotel", "Sea View Resort", "Mountain Lodge",
            "City Center Inn", "Sunset Beach Hotel", "Alpine Comfort"
    );
    private static final List<String> AMENITY_CODES = List.of("wifi", "parking", "breakfast", "pool", "spa", "gym");

    // TODO: Логика создания mock, тут будет только restClient и ручки для получения каких-то данных от парсера
    @Override
    public List<MockProviderHotel> fetch(Country country) {
        int count = 2 + RANDOM.nextInt(3);
        return java.util.stream.IntStream.range(0, count)
                .mapToObj(i -> buildRandomHotel(country, i))
                .toList();
    }

    private MockProviderHotel buildRandomHotel(Country country, int index) {
        String name = NAMES.get(RANDOM.nextInt(NAMES.size())) + " " + (index + 1);
        BigDecimal price = BigDecimal.valueOf(50 + RANDOM.nextInt(350)).setScale(2, java.math.RoundingMode.HALF_UP);
        int stars = 3 + RANDOM.nextInt(3);
        double lat = 40 + RANDOM.nextDouble() * 20;
        double lng = 10 + RANDOM.nextDouble() * 30;

        List<MockFacility> amenities = AMENITY_CODES.stream()
                .limit(2 + RANDOM.nextInt(3))
                .map(code -> MockFacility.builder().code(code).free(RANDOM.nextBoolean()).build())
                .toList();

        MockReviews reviews = MockReviews.builder()
                .count(10 + RANDOM.nextInt(100))
                .score(3.5 + RANDOM.nextDouble() * 1.5)
                .distribution(java.util.Map.of("5", 20, "4", 30, "3", 25, "2", 15, "1", 10))
                .items(List.of(
                        MockComment.builder()
                                .userName("Guest_" + index)
                                .userCountry(country.getAlpha2())
                                .tripKind("Leisure")
                                .score(4.0)
                                .pros("Clean, quiet")
                                .cons("No view")
                                .text("Nice stay.")
                                .date(java.time.LocalDate.now().minusDays(RANDOM.nextInt(90)))
                                .images(List.of())
                                .build()
                ))
                .build();

        return MockProviderHotel.builder()
                .externalId("mock-" + country.getAlpha2().toLowerCase() + "-" + (1000 + index))
                .name(name)
                .url("https://mock.example.com/hotel/" + (1000 + index))
                .summary("Comfortable accommodation in " + country.name().replace("_", " "))
                .street("Sample St " + (1 + index))
                .locality("Sample City")
                .countryCode(country.getAlpha2())
                .priceCurrency("USD")
                .priceAmount(price)
                .stars(stars)
                .lat(lat)
                .lng(lng)
                .amenities(amenities)
                .reviewSummary(reviews)
                .build();
    }
}
