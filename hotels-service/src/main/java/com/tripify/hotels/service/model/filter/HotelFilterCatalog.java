package com.tripify.hotels.service.model.filter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * MVP-каталог фильтров Tripify. Только осмысленные, короткие id для {@code GET /hotels?filters=}.
 */
public final class HotelFilterCatalog {

    public static final String POOL = "pool";
    public static final String WIFI = "wifi";
    public static final String BREAKFAST = "breakfast";
    public static final String BEACHFRONT = "beachfront";
    public static final String PET_FRIENDLY = "pet_friendly";
    public static final String FREE_CANCELLATION = "free_cancellation";

    public static final String RATING_8_PLUS = "rating_8_plus";
    public static final String RATING_9_PLUS = "rating_9_plus";
    public static final String STARS_4_PLUS = "stars_4_plus";
    public static final String STARS_5 = "stars_5";

    private static final Map<String, HotelFilterDefinition> BY_ID;

    static {
        Map<String, HotelFilterDefinition> definitions = new LinkedHashMap<>();

        registerFacet(definitions, POOL, "Бассейн");
        registerFacet(definitions, WIFI, "Wi‑Fi");
        registerFacet(definitions, BREAKFAST, "Завтрак");
        registerFacet(definitions, BEACHFRONT, "У моря");
        registerFacet(definitions, PET_FRIENDLY, "Можно с животными");

        registerTerms(definitions, FREE_CANCELLATION, "Бесплатная отмена");

        registerAttribute(definitions, RATING_8_PLUS, "Рейтинг отзывов от 8", HotelAttributeRule.minRating(8.0));
        registerAttribute(definitions, RATING_9_PLUS, "Рейтинг отзывов от 9", HotelAttributeRule.minRating(9.0));
        registerAttribute(definitions, STARS_4_PLUS, "4 звезды и выше", HotelAttributeRule.minStars(4));
        registerAttribute(definitions, STARS_5, "5 звёзд", HotelAttributeRule.exactStars(5));

        BY_ID = Map.copyOf(definitions);
    }

    private HotelFilterCatalog() {
    }

    public static Optional<HotelFilterDefinition> find(String filterId) {
        if (filterId == null || filterId.isBlank()) {
            return Optional.empty();
        }

        return Optional.ofNullable(BY_ID.get(normalizeId(filterId)));
    }

    public static List<HotelFilterDefinition> all() {
        return List.copyOf(BY_ID.values());
    }

    public static String normalizeId(String filterId) {
        return filterId.trim().toLowerCase(Locale.ROOT).replace('-', '_');
    }

    private static void registerFacet(
            Map<String, HotelFilterDefinition> definitions,
            String id,
            String label
    ) {
        definitions.put(id, new HotelFilterDefinition(id, HotelFilterType.FACET, label));
    }

    private static void registerTerms(
            Map<String, HotelFilterDefinition> definitions,
            String id,
            String label
    ) {
        definitions.put(id, new HotelFilterDefinition(id, HotelFilterType.TERMS, label));
    }

    private static void registerAttribute(
            Map<String, HotelFilterDefinition> definitions,
            String id,
            String label,
            HotelAttributeRule rule
    ) {
        definitions.put(id, new HotelFilterDefinition(id, HotelFilterType.ATTRIBUTE, label, rule));
    }
}
