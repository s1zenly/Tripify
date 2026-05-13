package com.tripify.info.weather;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ForecastHorizonTest {

  private static final LocalDate TODAY = LocalDate.of(2026, 5, 31);

  @Test
  void returnsEmptyWhenStayStartsAfterHorizon() {
    assertThat(ForecastHorizon.resolve(
            LocalDate.of(2026, 6, 20),
            LocalDate.of(2026, 6, 25),
            TODAY,
            16
    )).isEmpty();
  }

  @Test
  void returnsEmptyWhenStayIsEntirelyInPast() {
    assertThat(ForecastHorizon.resolve(
            LocalDate.of(2026, 5, 1),
            LocalDate.of(2026, 5, 10),
            TODAY,
            16
    )).isEmpty();
  }

  @Test
  void clipsStayEndToHorizon() {
    var window = ForecastHorizon.resolve(
            LocalDate.of(2026, 6, 10),
            LocalDate.of(2026, 6, 25),
            TODAY,
            16
    ).orElseThrow();

    assertThat(window.fetchFrom()).isEqualTo(LocalDate.of(2026, 6, 10));
    assertThat(window.fetchTo()).isEqualTo(LocalDate.of(2026, 6, 15));
  }

  @Test
  void clipsStayStartToToday() {
    var window = ForecastHorizon.resolve(
            LocalDate.of(2026, 5, 20),
            LocalDate.of(2026, 6, 5),
            TODAY,
            16
    ).orElseThrow();

    assertThat(window.fetchFrom()).isEqualTo(TODAY);
    assertThat(window.fetchTo()).isEqualTo(LocalDate.of(2026, 6, 5));
  }
}
