package com.tripify.info.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import com.tripify.info.exception.InfoErrorCode;
import com.tripify.info.exception.InfoServiceException;
import com.tripify.info.model.CountryInfoFactItem;
import com.tripify.info.model.CountryInfoSection;
import com.tripify.info.model.CountryInfoTab;
import com.tripify.info.model.CountryInfoTabCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TutuCountryPageParser {

    private static final Set<String> HEADING_TAGS = Set.of("h1", "h2", "h3");

    private static final Pattern FACT_DELIMITER = Pattern.compile("\\s*[:—–-]\\s*", Pattern.UNICODE_CHARACTER_CLASS);

    private static final List<String> MONTHS = List.of(
            "январ", "феврал", "март", "апрел", "май", "июн", "июл",
            "август", "сентябр", "октябр", "ноябр", "декабр"
    );

    private final TutuCountrySectionMapper sectionMapper;
    private final TextNormalizer textNormalizer;

    public List<CountryInfoTab> parse(String html) {
        try {
            Document document = Jsoup.parse(html);
            Element root = Optional.ofNullable(document.selectFirst("main"))
                    .or(() -> Optional.ofNullable(document.selectFirst("article")))
                    .orElse(document.body());

            Map<CountryInfoTabCode, List<CountryInfoSection>> sectionsByTab = new EnumMap<>(CountryInfoTabCode.class);
            List<HeadingBlock> blocks = extractHeadingBlocksFallback(root);

            for (HeadingBlock block : blocks) {
                Optional<CountryInfoTabCode> tabCode = sectionMapper.mapHeading(block.heading());
                if (tabCode.isEmpty()) {
                    continue;
                }

                CountryInfoSection section = buildSection(block);
                if (section != null) {
                    sectionsByTab.computeIfAbsent(tabCode.get(), ignored -> new ArrayList<>()).add(section);
                }
            }

            return Arrays.stream(CountryInfoTabCode.values())
                    .filter(CountryInfoTabCode::scrapedFromSource)
                    .map(code -> {
                        List<CountryInfoSection> sections = sectionsByTab.getOrDefault(code, List.of());
                        if (sections.isEmpty()) {
                            return null;
                        }
                        return new CountryInfoTab(code, code.title(), dedupeSections(sections));
                    })
                    .filter(tab -> tab != null)
                    .toList();
        } catch (RuntimeException exception) {
            throw new InfoServiceException(
                    InfoErrorCode.TUTU_PAGE_PARSE_FAILED,
                    "Failed to parse Tutu country page",
                    exception
            );
        }
    }

    private List<HeadingBlock> extractHeadingBlocksFallback(Element root) {
        List<HeadingBlock> blocks = new ArrayList<>();
        Elements headings = root.select("h1, h2, h3");

        for (int index = 0; index < headings.size(); index++) {
            Element heading = headings.get(index);
            String title = heading.text();
            List<Element> content = new ArrayList<>();

            Element sibling = heading.nextElementSibling();
            Element nextHeading = index + 1 < headings.size() ? headings.get(index + 1) : null;

            while (sibling != null && sibling != nextHeading) {
                if (HEADING_TAGS.contains(sibling.tagName().toLowerCase(Locale.ROOT))) {
                    break;
                }
                if (isContentElement(sibling)) {
                    content.add(sibling);
                }
                sibling = sibling.nextElementSibling();
            }

            blocks.add(new HeadingBlock(title, content));
        }

        return blocks;
    }

    private CountryInfoSection buildSection(HeadingBlock block) {
        String title = textNormalizer.normalize(block.heading());
        if (title.isBlank()) {
            return null;
        }

        Optional<List<Map<String, Object>>> temperatureTable = extractTemperatureTable(block.content());
        if (temperatureTable.isPresent()) {
            return CountryInfoSection.table("Температура по месяцам", temperatureTable.get());
        }

        List<CountryInfoFactItem> facts = extractFacts(block);
        if (!facts.isEmpty()) {
            return CountryInfoSection.facts(title, facts);
        }

        Element table = block.content().stream()
                .filter(element -> "table".equals(element.tagName()))
                .findFirst()
                .orElse(null);
        if (table != null) {
            List<Map<String, Object>> rows = extractGenericTable(table);
            if (!rows.isEmpty()) {
                return CountryInfoSection.table(title, rows);
            }
        }

        List<String> listItems = extractListItems(block.content());
        if (listItems.size() >= 2) {
            return CountryInfoSection.list(title, String.join("\n", listItems));
        }

        String text = extractText(block.content());
        text = textNormalizer.normalize(text);
        if (text.isBlank()) {
            return null;
        }

        return CountryInfoSection.text(title, text);
    }

    private List<CountryInfoFactItem> extractFacts(HeadingBlock block) {
        if (block.heading().toLowerCase(Locale.ROOT).contains("факт")) {
            List<CountryInfoFactItem> facts = new ArrayList<>();
            for (Element element : block.content()) {
                facts.addAll(parseFactItems(element.text()));
            }
            return facts;
        }

        for (Element element : block.content()) {
            if ("dl".equals(element.tagName())) {
                return parseDefinitionList(element);
            }
            if ("table".equals(element.tagName()) && element.select("tr").size() <= 12) {
                List<CountryInfoFactItem> tableFacts = parseFactTable(element);
                if (!tableFacts.isEmpty()) {
                    return tableFacts;
                }
            }
        }

        return List.of();
    }

    private List<CountryInfoFactItem> parseDefinitionList(Element dl) {
        List<CountryInfoFactItem> facts = new ArrayList<>();
        Elements terms = dl.select("dt");
        Elements values = dl.select("dd");
        int size = Math.min(terms.size(), values.size());
        for (int index = 0; index < size; index++) {
            String label = textNormalizer.normalize(terms.get(index).text());
            String value = textNormalizer.normalize(values.get(index).text());
            if (!label.isBlank() && !value.isBlank()) {
                facts.add(new CountryInfoFactItem(label, value));
            }
        }
        return facts;
    }

    private List<CountryInfoFactItem> parseFactTable(Element table) {
        List<CountryInfoFactItem> facts = new ArrayList<>();
        for (Element row : table.select("tr")) {
            Elements cells = row.select("th, td");
            if (cells.size() == 2) {
                String label = textNormalizer.normalize(cells.get(0).text());
                String value = textNormalizer.normalize(cells.get(1).text());
                if (!label.isBlank() && !value.isBlank()) {
                    facts.add(new CountryInfoFactItem(label, value));
                }
            }
        }
        return facts;
    }

    private List<CountryInfoFactItem> parseFactItems(String text) {
        List<CountryInfoFactItem> facts = new ArrayList<>();
        for (String line : text.split("\\n")) {
            String normalizedLine = textNormalizer.normalize(line);
            if (normalizedLine.isBlank()) {
                continue;
            }
            String[] parts = FACT_DELIMITER.split(normalizedLine, 2);
            if (parts.length == 2) {
                facts.add(new CountryInfoFactItem(parts[0].trim(), parts[1].trim()));
            }
        }
        return facts;
    }

    private Optional<List<Map<String, Object>>> extractTemperatureTable(List<Element> content) {
        for (Element element : content) {
            if (!"table".equals(element.tagName())) {
                continue;
            }
            List<Map<String, Object>> rows = new ArrayList<>();
            for (Element row : element.select("tr")) {
                Elements cells = row.select("th, td");
                if (cells.size() < 2) {
                    continue;
                }
                String firstCell = textNormalizer.normalize(cells.get(0).text()).toLowerCase(Locale.ROOT);
                if (!containsMonth(firstCell)) {
                    continue;
                }
                Map<String, Object> rowData = new LinkedHashMap<>();
                rowData.put("month", textNormalizer.normalize(cells.get(0).text()));
                if (cells.size() >= 2) {
                    rowData.put("day", parseNumber(cells.get(1).text()));
                }
                if (cells.size() >= 3) {
                    rowData.put("night", parseNumber(cells.get(2).text()));
                }
                rows.add(rowData);
            }
            if (rows.size() >= 3) {
                return Optional.of(rows);
            }
        }
        return Optional.empty();
    }

    private List<Map<String, Object>> extractGenericTable(Element table) {
        List<Map<String, Object>> rows = new ArrayList<>();
        Elements headerCells = table.select("tr").first() != null
                ? table.select("tr").first().select("th, td")
                : new Elements();

        List<String> headers = headerCells.stream()
                .map(cell -> toFieldName(cell.text()))
                .toList();

        Elements dataRows = table.select("tr");
        for (int rowIndex = 1; rowIndex < dataRows.size(); rowIndex++) {
            Elements cells = dataRows.get(rowIndex).select("th, td");
            if (cells.isEmpty()) {
                continue;
            }
            Map<String, Object> row = new LinkedHashMap<>();
            for (int cellIndex = 0; cellIndex < cells.size(); cellIndex++) {
                String key = cellIndex < headers.size() && !headers.get(cellIndex).isBlank()
                        ? headers.get(cellIndex)
                        : "col_" + cellIndex;
                row.put(key, parseCellValue(cells.get(cellIndex).text()));
            }
            rows.add(row);
        }
        return rows;
    }

    private List<String> extractListItems(List<Element> content) {
        List<String> items = new ArrayList<>();
        for (Element element : content) {
            if ("ul".equals(element.tagName()) || "ol".equals(element.tagName())) {
                element.select("li").forEach(li -> {
                    String item = textNormalizer.normalize(li.text());
                    if (!item.isBlank()) {
                        items.add(item);
                    }
                });
            }
        }
        return textNormalizer.normalizeLines(items);
    }

    private String extractText(List<Element> content) {
        StringBuilder builder = new StringBuilder();
        for (Element element : content) {
            if ("script".equals(element.tagName()) || "style".equals(element.tagName())) {
                continue;
            }
            if ("p".equals(element.tagName()) || element.tagName().startsWith("h")) {
                String text = textNormalizer.normalize(element.text());
                if (!text.isBlank()) {
                    if (!builder.isEmpty()) {
                        builder.append("\n");
                    }
                    builder.append(text);
                }
            }
        }

        if (builder.isEmpty()) {
            for (Element element : content) {
                String text = textNormalizer.normalize(element.text());
                if (!text.isBlank()) {
                    if (!builder.isEmpty()) {
                        builder.append("\n");
                    }
                    builder.append(text);
                }
            }
        }

        return builder.toString();
    }

    private static Object parseCellValue(String raw) {
        return parseNumber(raw) != null ? parseNumber(raw) : raw.trim();
    }

    private static Object parseNumber(String raw) {
        if (raw == null) {
            return null;
        }
        String digits = raw.replaceAll("[^\\d+-]", "");
        if (digits.isBlank()) {
            return null;
        }
        try {
            if (digits.contains(".")) {
                return Double.parseDouble(digits);
            }
            return Integer.parseInt(digits);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private static boolean containsMonth(String value) {
        return MONTHS.stream().anyMatch(value::contains);
    }

    private static String toFieldName(String header) {
        if (header == null || header.isBlank()) {
            return "";
        }
        return header.trim()
                .toLowerCase(Locale.ROOT)
                .replace('ё', 'е')
                .replaceAll("[^a-zа-я0-9]+", "_")
                .replaceAll("^_|_$", "");
    }

    private static boolean isContentElement(Element element) {
        String tag = element.tagName().toLowerCase(Locale.ROOT);
        return tag.equals("p")
                || tag.equals("ul")
                || tag.equals("ol")
                || tag.equals("table")
                || tag.equals("dl")
                || tag.equals("div");
    }

    private static List<CountryInfoSection> dedupeSections(List<CountryInfoSection> sections) {
        Map<String, CountryInfoSection> unique = new LinkedHashMap<>();
        for (CountryInfoSection section : sections) {
            String key = section.title() + "|" + section.type().getValue();
            unique.putIfAbsent(key, section);
        }
        return List.copyOf(unique.values());
    }

    private record HeadingBlock(String heading, List<Element> content) {
    }
}
