package com.tripify.tickets.service.provider.emulation;

import com.tripify.tickets.service.provider.emulation.raw.EmulationRawPoint;

import java.util.Map;
import java.util.Optional;

final class RouteCatalog {

    private static final Map<String, RouteEndpoint> ENDPOINTS = Map.ofEntries(
            Map.entry("MOW", new RouteEndpoint("MOW", "Москва", "SVO", "Шереметьево", "C", "Europe/Moscow")),
            Map.entry("LED", new RouteEndpoint("LED", "Санкт-Петербург", "LED", "Пулково", "1", "Europe/Moscow")),
            Map.entry("DXB", new RouteEndpoint("DXB", "Дубай", "DXB", "Dubai International Airport", "3", "Asia/Dubai")),
            Map.entry("IST", new RouteEndpoint("IST", "Стамбул", "IST", "Istanbul Airport", "1", "Europe/Istanbul")),
            Map.entry("PAR", new RouteEndpoint("PAR", "Париж", "CDG", "Charles de Gaulle", "2E", "Europe/Paris")),
            Map.entry("NYC", new RouteEndpoint("NYC", "Нью-Йорк", "JFK", "John F. Kennedy International", "4", "America/New_York"))
    );

    private RouteCatalog() {
    }

    static Optional<RouteEndpoint> find(String cityCode) {
        if (cityCode == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(ENDPOINTS.get(cityCode.toUpperCase()));
    }

    static EmulationRawPoint toRawPoint(RouteEndpoint endpoint) {
        return EmulationRawPoint.builder()
                .cityIata(endpoint.cityCode())
                .cityTitle(endpoint.cityName())
                .airportIata(endpoint.airportCode())
                .airportTitle(endpoint.airportName())
                .terminal(endpoint.terminal())
                .build();
    }

    record RouteEndpoint(
            String cityCode,
            String cityName,
            String airportCode,
            String airportName,
            String terminal,
            String timezone
    ) {
    }
}
