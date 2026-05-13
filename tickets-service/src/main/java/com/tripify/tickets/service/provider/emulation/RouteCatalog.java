package com.tripify.tickets.service.provider.emulation;

import com.tripify.tickets.service.domain.City;
import com.tripify.tickets.service.domain.Country;
import com.tripify.tickets.service.provider.emulation.raw.EmulationRawPoint;

import java.util.Map;
import java.util.Optional;

final class RouteCatalog {

    private static final Map<City, RouteEndpoint> DETAILED = Map.of(
            City.MOSCOW, endpoint(City.MOSCOW, "SVO", "Sheremetyevo", "C", "Europe/Moscow"),
            City.SAINT_PETERSBURG, endpoint(City.SAINT_PETERSBURG, "LED", "Pulkovo", "1", "Europe/Moscow"),
            City.DUBAI, endpoint(City.DUBAI, "DXB", "Dubai International Airport", "3", "Asia/Dubai"),
            City.ISTANBUL, endpoint(City.ISTANBUL, "IST", "Istanbul Airport", "1", "Europe/Istanbul"),
            City.PARIS, endpoint(City.PARIS, "CDG", "Charles de Gaulle", "2E", "Europe/Paris")
    );

    private static final Map<Country, String> TIMEZONES = Map.ofEntries(
            Map.entry(Country.RUSSIA, "Europe/Moscow"),
            Map.entry(Country.CHINA, "Asia/Shanghai"),
            Map.entry(Country.UAE, "Asia/Dubai"),
            Map.entry(Country.TURKEY, "Europe/Istanbul"),
            Map.entry(Country.THAILAND, "Asia/Bangkok"),
            Map.entry(Country.SPAIN, "Europe/Madrid"),
            Map.entry(Country.ITALY, "Europe/Rome"),
            Map.entry(Country.FRANCE, "Europe/Paris"),
            Map.entry(Country.JAPAN, "Asia/Tokyo"),
            Map.entry(Country.EGYPT, "Africa/Cairo")
    );

    private RouteCatalog() {
    }

    static Optional<RouteEndpoint> find(String cityCode) {
        if (cityCode == null || cityCode.isBlank()) {
            return Optional.empty();
        }

        try {
            City city = City.fromCode(cityCode);
            return Optional.of(toEndpoint(city));
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

    static EmulationRawPoint toRawPoint(RouteEndpoint endpoint) {
        return EmulationRawPoint.builder()
                .cityIata(endpoint.cityCode())
                .airportIata(endpoint.airportCode())
                .airportTitle(endpoint.airportName())
                .terminal(endpoint.terminal())
                .build();
    }

    private static RouteEndpoint toEndpoint(City city) {
        RouteEndpoint detailed = DETAILED.get(city);
        if (detailed != null) {
            return detailed;
        }

        return endpoint(
                city,
                city.iataCode(),
                city.iataCode() + " Airport",
                "1",
                TIMEZONES.get(city.country())
        );
    }

    private static RouteEndpoint endpoint(
            City city,
            String airportCode,
            String airportName,
            String terminal,
            String timezone
    ) {
        return new RouteEndpoint(
                city.iataCode(),
                airportCode,
                airportName,
                terminal,
                timezone
        );
    }

    record RouteEndpoint(
            String cityCode,
            String airportCode,
            String airportName,
            String terminal,
            String timezone
    ) {
    }
}
