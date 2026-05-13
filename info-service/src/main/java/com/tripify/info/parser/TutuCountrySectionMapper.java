package com.tripify.info.parser;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import com.tripify.info.model.CountryInfoTabCode;
import org.springframework.stereotype.Component;

@Component
public class TutuCountrySectionMapper {

    private static final Map<CountryInfoTabCode, String[]> TAB_KEYWORDS = Map.of(
            CountryInfoTabCode.OVERVIEW, new String[]{
                    "зачем ехать", "основное про страну", "краткое описание", "столица", "язык",
                    "религия", "часовой пояс", "дневной бюджет", "отношение к туристам",
                    "экономика", "рельеф", "климат", "основные факты", "о стране"
            },
            CountryInfoTabCode.VISA, new String[]{
                    "виза и въезд", "как оформить визу", "правила въезда", "таможня",
                    "документы", "прививки", "паспорт", "визовый режим", "въезд"
            },
            CountryInfoTabCode.WHEN_TO_GO, new String[]{
                    "когда ехать", "лучшее время", "сезон", "температура по месяцам",
                    "рекомендации по одежде", "национальные праздники", "погода"
            },
            CountryInfoTabCode.CURRENCY, new String[]{
                    "валюта", "название валюты", "код валюты", "обменник", "банкомат",
                    "оплата картой", "наличные", "деньги"
            },
            CountryInfoTabCode.PRICES, new String[]{
                    "цены", "стоимость жилья", "стоимость еды", "стоимость музеев",
                    "стоимость такси", "стоимость транспорта", "сколько стоит", "бюджет поездки"
            },
            CountryInfoTabCode.HOW_TO_GET, new String[]{
                    "как добраться", "прямые рейсы", "время в пути", "аэропорт",
                    "маршрут", "варианты транспорта", "перелет", "добраться до"
            }
    );

    private static final String[] EXCLUDED_HEADINGS = {
            "пляж", "курорт", "достопримечательност", "развлечен", "кухн", "сувенир",
            "безопасност", "медицин", "традици", "интернет", "полезные факты", "местные законы"
    };

    public Optional<CountryInfoTabCode> mapHeading(String heading) {
        String normalized = normalizeHeading(heading);
        if (normalized.isBlank()) {
            return Optional.empty();
        }

        if (isExcluded(normalized) && !containsPriceHint(normalized)) {
            return Optional.empty();
        }

        CountryInfoTabCode bestTab = null;
        int bestScore = 0;

        for (var entry : TAB_KEYWORDS.entrySet()) {
            int score = scoreKeywords(normalized, entry.getValue());
            if (score > bestScore) {
                bestScore = score;
                bestTab = entry.getKey();
            }
        }

        if (bestTab == null || bestScore == 0) {
            if (containsPriceHint(normalized)) {
                return Optional.of(CountryInfoTabCode.PRICES);
            }
            return Optional.empty();
        }

        return Optional.of(bestTab);
    }

    public boolean isExcludedHeading(String heading) {
        String normalized = normalizeHeading(heading);
        return isExcluded(normalized) && !containsPriceHint(normalized);
    }

    private static boolean isExcluded(String normalized) {
        for (String keyword : EXCLUDED_HEADINGS) {
            if (normalized.contains(keyword)) {
                return true;
            }
        }
        return normalized.equals("транспорт");
    }

    private static boolean containsPriceHint(String normalized) {
        return normalized.contains("стоимость")
                || normalized.contains("цена")
                || normalized.contains("бюджет");
    }

    private static int scoreKeywords(String normalized, String[] keywords) {
        int score = 0;
        for (String keyword : keywords) {
            if (normalized.contains(keyword)) {
                score += keyword.length();
            }
        }
        return score;
    }

    static String normalizeHeading(String heading) {
        if (heading == null) {
            return "";
        }
        String normalized = Normalizer.normalize(heading, Normalizer.Form.NFC)
                .toLowerCase(Locale.ROOT)
                .replace('ё', 'е');
        normalized = normalized.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\s]", " ");
        return normalized.replaceAll("\\s+", " ").trim();
    }
}
