package com.tripify.info.parser;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class TextNormalizer {

    private static final int MAX_SECTION_LENGTH = 12_000;

    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    private static final List<Pattern> NOISE_PATTERNS = List.of(
            Pattern.compile("(?i)выбрать"),
            Pattern.compile("(?i)найти отели"),
            Pattern.compile("(?i)найти туры"),
            Pattern.compile("(?i)подписаться"),
            Pattern.compile("(?i)оставьте email"),
            Pattern.compile("(?i)реклама")
    );

    public String normalize(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }

        String normalized = text;
        for (Pattern pattern : NOISE_PATTERNS) {
            normalized = pattern.matcher(normalized).replaceAll(" ");
        }

        normalized = normalized.lines()
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .map(line -> WHITESPACE.matcher(line).replaceAll(" "))
                .collect(Collectors.joining("\n"));

        normalized = WHITESPACE.matcher(normalized).replaceAll(" ").trim();

        if (normalized.length() > MAX_SECTION_LENGTH) {
            normalized = normalized.substring(0, MAX_SECTION_LENGTH).trim() + "…";
        }

        return normalized;
    }

    public List<String> normalizeLines(List<String> lines) {
        Set<String> unique = new LinkedHashSet<>();
        for (String line : lines) {
            String normalized = normalize(line);
            if (!normalized.isBlank()) {
                unique.add(normalized);
            }
        }
        return new ArrayList<>(unique);
    }
}
