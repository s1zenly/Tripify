package com.tripify.info.currency;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class StubExchangeRateProviderTest {

    private final StubExchangeRateProvider provider = new StubExchangeRateProvider();

    @Test
    void convertsUsdToRubConsistentlyWithHotelsStub() {
        BigDecimal rate = provider.rateToBase("USD", "RUB");

        assertThat(rate).isEqualByComparingTo(new BigDecimal("90.909091"));
    }

    @Test
    void convertsUsdToCny() {
        BigDecimal rate = provider.rateToBase("USD", "CNY");

        assertThat(rate).isEqualByComparingTo(new BigDecimal("7.142857"));
    }

    @Test
    void convertsRubToCny() {
        BigDecimal rate = provider.rateToBase("RUB", "CNY");

        assertThat(rate).isGreaterThan(BigDecimal.ZERO);
    }
}
