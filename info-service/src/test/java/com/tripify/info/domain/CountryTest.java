package com.tripify.info.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CountryTest {

    @Test
    void containsTenSupportedCountries() {
        assertThat(Country.values()).hasSize(10);
    }

    @Test
    void buildsTutuSourceUrl() {
        assertThat(Country.UAE.tutuSourceUrl())
                .isEqualTo("https://www.tutu.ru/geo/strana/united_arab_emirates/");
        assertThat(Country.CHINA.tutuSourceUrl())
                .isEqualTo("https://www.tutu.ru/geo/strana/china/");
    }

    @Test
    void providesDefaultLocalCurrency() {
        assertThat(Country.CHINA.defaultLocalCurrency()).isEqualTo("CNY");
        assertThat(Country.RUSSIA.defaultLocalCurrency()).isEqualTo("RUB");
    }
}
