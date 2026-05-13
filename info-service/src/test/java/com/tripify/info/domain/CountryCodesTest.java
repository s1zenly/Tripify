package com.tripify.info.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tripify.info.exception.InfoErrorCode;
import com.tripify.info.exception.InfoServiceException;
import org.junit.jupiter.api.Test;

class CountryCodesTest {

    @Test
    void normalizesSupportedAlpha2ToUppercase() {
        assertThat(CountryCodes.normalize("ae")).isEqualTo("AE");
        assertThat(CountryCodes.normalize(" RU ")).isEqualTo("RU");
    }

    @Test
    void rejectsUnsupportedCountries() {
        assertThatThrownBy(() -> CountryCodes.normalize("AM"))
                .isInstanceOf(InfoServiceException.class)
                .extracting(ex -> ((InfoServiceException) ex).getErrorCode())
                .isEqualTo(InfoErrorCode.INVALID_COUNTRY_CODE);
    }

    @Test
    void rejectsInvalidFormat() {
        assertThatThrownBy(() -> CountryCodes.normalize("armenia"))
                .isInstanceOf(InfoServiceException.class)
                .extracting(ex -> ((InfoServiceException) ex).getErrorCode())
                .isEqualTo(InfoErrorCode.INVALID_COUNTRY_CODE);
    }
}
