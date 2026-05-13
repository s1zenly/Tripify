package com.tripify.info.weather;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Open-Meteo forecast is available from {@code today} through {@code today + maxDays - 1} (inclusive).
 */
public final class ForecastHorizon {

    private ForecastHorizon() {
    }

    public record Window(LocalDate fetchFrom, LocalDate fetchTo) {
    }

    public static Optional<Window> resolve(LocalDate stayFrom, LocalDate stayTo, LocalDate today, int maxDays) {
        LocalDate horizonEnd = today.plusDays(maxDays - 1L);

        if (stayTo.isBefore(today) || stayFrom.isAfter(horizonEnd)) {
            return Optional.empty();
        }

        LocalDate fetchFrom = stayFrom.isBefore(today) ? today : stayFrom;
        LocalDate fetchTo = stayTo.isAfter(horizonEnd) ? horizonEnd : stayTo;
        return Optional.of(new Window(fetchFrom, fetchTo));
    }
}
