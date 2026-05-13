package com.tripify.info.parser;

import static org.assertj.core.api.Assertions.assertThat;

import com.tripify.info.model.CountryInfoTabCode;
import org.junit.jupiter.api.Test;

class TutuCountrySectionMapperTest {

    private final TutuCountrySectionMapper mapper = new TutuCountrySectionMapper();

    @Test
    void mapsOverviewAndVisaHeadings() {
        assertThat(mapper.mapHeading("Зачем ехать")).contains(CountryInfoTabCode.OVERVIEW);
        assertThat(mapper.mapHeading("Визовый режим")).contains(CountryInfoTabCode.VISA);
    }

    @Test
    void skipsExcludedBeachSection() {
        assertThat(mapper.mapHeading("Пляжи")).isEmpty();
    }

    @Test
    void mapsTransportPriceSubheadingToPrices() {
        assertThat(mapper.mapHeading("Стоимость такси")).contains(CountryInfoTabCode.PRICES);
    }
}
