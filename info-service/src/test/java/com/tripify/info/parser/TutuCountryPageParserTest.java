package com.tripify.info.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;

import com.tripify.info.model.CountryInfoSectionType;
import com.tripify.info.model.CountryInfoTab;
import com.tripify.info.model.CountryInfoTabCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StreamUtils;

class TutuCountryPageParserTest {

    private TutuCountryPageParser parser;

    @BeforeEach
    void setUp() {
        parser = new TutuCountryPageParser(new TutuCountrySectionMapper(), new TextNormalizer());
    }

    @Test
    void parsesSampleArmeniaPageIntoTabs() throws Exception {
        String html = StreamUtils.copyToString(
                getClass().getResourceAsStream("/fixtures/armenia-sample.html"),
                StandardCharsets.UTF_8
        );

        var tabs = parser.parse(html);

        assertThat(tabs).extracting(CountryInfoTab::code)
                .contains(
                        CountryInfoTabCode.OVERVIEW,
                        CountryInfoTabCode.VISA,
                        CountryInfoTabCode.WHEN_TO_GO,
                        CountryInfoTabCode.CURRENCY,
                        CountryInfoTabCode.PRICES,
                        CountryInfoTabCode.HOW_TO_GET
                );

        CountryInfoTab overview = tabs.stream()
                .filter(tab -> tab.code() == CountryInfoTabCode.OVERVIEW)
                .findFirst()
                .orElseThrow();

        assertThat(overview.sections())
                .anyMatch(section -> section.type() == CountryInfoSectionType.TEXT
                        && section.title().equals("Зачем ехать")
                        && section.content().contains("монастырями"));

        assertThat(overview.sections())
                .anyMatch(section -> section.type() == CountryInfoSectionType.FACTS
                        && section.items().stream().anyMatch(item -> item.label().equals("Столица")));

        CountryInfoTab whenToGo = tabs.stream()
                .filter(tab -> tab.code() == CountryInfoTabCode.WHEN_TO_GO)
                .findFirst()
                .orElseThrow();

        assertThat(whenToGo.sections())
                .anyMatch(section -> section.type() == CountryInfoSectionType.TABLE
                        && section.data() != null
                        && section.data().getFirst().containsKey("month"));

        assertThat(tabs.stream()
                .flatMap(tab -> tab.sections().stream())
                .noneMatch(section -> section.content() != null && section.content().contains("Пляжи")))
                .isTrue();
    }
}
