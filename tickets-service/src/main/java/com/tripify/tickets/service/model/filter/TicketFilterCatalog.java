package com.tripify.tickets.service.model.filter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public final class TicketFilterCatalog {

    public static final String DIRECT = "direct";
    public static final String MAX_1_STOP = "max_1_stop";
    public static final String MAX_2_STOPS = "max_2_stops";
    public static final String NO_OVERNIGHT = "no_overnight";
    public static final String NO_AIRPORT_CHANGE = "no_airport_change";
    public static final String NO_SELF_TRANSFER = "no_self_transfer";
    public static final String REFUNDABLE = "refundable";
    public static final String CHECKED_BAGGAGE = "checked_baggage";

    private static final Map<String, TicketFilterDefinition> BY_ID;

    static {
        Map<String, TicketFilterDefinition> definitions = new LinkedHashMap<>();

        registerFacet(definitions, DIRECT, "Без пересадок");
        registerFacet(definitions, MAX_1_STOP, "Не больше 1 пересадки");
        registerFacet(definitions, MAX_2_STOPS, "Не больше 2 пересадок");
        registerFacet(definitions, NO_OVERNIGHT, "Без ночной стыковки");
        registerFacet(definitions, NO_AIRPORT_CHANGE, "Без смены аэропорта");
        registerFacet(definitions, NO_SELF_TRANSFER, "Без самостоятельной пересадки");

        registerTerms(definitions, REFUNDABLE, "Возвратный тариф");
        registerTerms(definitions, CHECKED_BAGGAGE, "С багажом");

        BY_ID = Map.copyOf(definitions);
    }

    private TicketFilterCatalog() {
    }

    public static Optional<TicketFilterDefinition> find(String filterId) {
        if (filterId == null || filterId.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(BY_ID.get(normalizeId(filterId)));
    }

    public static List<TicketFilterDefinition> all() {
        return List.copyOf(BY_ID.values());
    }

    public static String normalizeId(String filterId) {
        return filterId.trim().toLowerCase(Locale.ROOT).replace('-', '_');
    }

    private static void registerFacet(Map<String, TicketFilterDefinition> definitions, String id, String label) {
        definitions.put(id, new TicketFilterDefinition(id, TicketFilterType.FACET, label));
    }

    private static void registerTerms(Map<String, TicketFilterDefinition> definitions, String id, String label) {
        definitions.put(id, new TicketFilterDefinition(id, TicketFilterType.TERMS, label));
    }
}
