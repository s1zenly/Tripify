package com.tripify.info.currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SupportedCurrenciesTest {

    @Test
    void isSupportedReturnsFalseForUnknownCodeWithoutThrowing() {
        assertThat(SupportedCurrencies.isSupported("ARD")).isFalse();
        assertThat(SupportedCurrencies.isSupported("XXX")).isFalse();
    }

    @Test
    void normalizeThrowsForUnsupportedCode() {
        assertThatThrownBy(() -> SupportedCurrencies.normalize("ARD"))
                .hasMessageContaining("Unsupported currency: ARD");
    }

    @Test
    void acceptsKnownCountryCurrency() {
        assertThat(SupportedCurrencies.isSupported("AED")).isTrue();
        assertThat(SupportedCurrencies.normalize("AED")).isEqualTo("AED");
    }
}
