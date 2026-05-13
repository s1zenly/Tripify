package com.tripify.info.parser;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tripify.info.currency.SupportedCurrencies;
import com.tripify.info.model.CountryInfoFactItem;
import com.tripify.info.model.CountryInfoSection;
import com.tripify.info.model.CountryInfoTab;
import com.tripify.info.model.CountryInfoTabCode;
import org.springframework.stereotype.Component;

@Component
public class CountryLocalCurrencyExtractor {

    private static final Pattern PAREN_CODE = Pattern.compile("\\(([A-Z]{3})\\)");
    private static final Pattern ISO_CODE = Pattern.compile("\\b([A-Z]{3})\\b");

    public Optional<String> extract(List<CountryInfoTab> tabs) {
        return tabs.stream()
                .filter(tab -> tab.code() == CountryInfoTabCode.CURRENCY)
                .flatMap(tab -> tab.sections().stream())
                .map(this::extractFromSection)
                .flatMap(Optional::stream)
                .findFirst();
    }

    private Optional<String> extractFromSection(CountryInfoSection section) {
        if (section.items() != null) {
            for (CountryInfoFactItem item : section.items()) {
                Optional<String> fromFact = extractFromFact(item.label(), item.value());
                if (fromFact.isPresent()) {
                    return fromFact;
                }
            }
        }

        if (section.content() != null) {
            return extractFromText(section.content());
        }

        return Optional.empty();
    }

    private Optional<String> extractFromFact(String label, String value) {
        if (label == null || value == null) {
            return Optional.empty();
        }

        String normalizedLabel = label.toLowerCase(Locale.ROOT);
        if (normalizedLabel.contains("код") || normalizedLabel.contains("валют") || normalizedLabel.contains("currency")) {
            return extractFromText(value);
        }

        return Optional.empty();
    }

    private Optional<String> extractFromText(String text) {
        if (text == null || text.isBlank()) {
            return Optional.empty();
        }

        String upper = text.toUpperCase(Locale.ROOT);

        Matcher parenMatcher = PAREN_CODE.matcher(upper);
        while (parenMatcher.find()) {
            String code = parenMatcher.group(1);
            if (SupportedCurrencies.isSupported(code)) {
                return Optional.of(code);
            }
        }

        Matcher isoMatcher = ISO_CODE.matcher(upper);
        while (isoMatcher.find()) {
            String code = isoMatcher.group(1);
            if (SupportedCurrencies.isSupported(code)) {
                return Optional.of(code);
            }
        }

        return Optional.empty();
    }
}
