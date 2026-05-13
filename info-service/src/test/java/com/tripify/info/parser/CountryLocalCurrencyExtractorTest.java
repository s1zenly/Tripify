package com.tripify.info.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.tripify.info.model.CountryInfoFactItem;
import com.tripify.info.model.CountryInfoSection;
import com.tripify.info.model.CountryInfoSectionType;
import com.tripify.info.model.CountryInfoTab;
import com.tripify.info.model.CountryInfoTabCode;
import org.junit.jupiter.api.Test;

class CountryLocalCurrencyExtractorTest {

    private final CountryLocalCurrencyExtractor extractor = new CountryLocalCurrencyExtractor();

    @Test
    void extractsCurrencyCodeFromCurrencyTabFacts() {
        CountryInfoTab currencyTab = new CountryInfoTab(
                CountryInfoTabCode.CURRENCY,
                "Валюта",
                List.of(CountryInfoSection.facts(
                        "Основное",
                        List.of(
                                new CountryInfoFactItem("Название", "юань"),
                                new CountryInfoFactItem("Код валюты", "CNY")
                        )
                ))
        );

        assertThat(extractor.extract(List.of(currencyTab))).contains("CNY");
    }

    @Test
    void extractsFromParenthesesInText() {
        CountryInfoTab currencyTab = new CountryInfoTab(
                CountryInfoTabCode.CURRENCY,
                "Валюта",
                List.of(CountryInfoSection.text(
                        "Валюта",
                        "Национальная валюта — китайский юань (CNY)."
                ))
        );

        assertThat(extractor.extract(List.of(currencyTab))).contains("CNY");
    }

    @Test
    void ignoresFalsePositiveArdFromMasterCardTypo() {
        CountryInfoTab currencyTab = new CountryInfoTab(
                CountryInfoTabCode.CURRENCY,
                "Валюта",
                List.of(CountryInfoSection.text(
                        "Валюта",
                        "Visa, Master \u0421ard, American Express"
                ))
        );

        assertThat(extractor.extract(List.of(currencyTab))).isEmpty();
    }
}
