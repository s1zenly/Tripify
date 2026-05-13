package com.tripify.info.model;

import java.util.List;
import java.util.Map;

public record CountryInfoSection(
        String title,
        CountryInfoSectionType type,
        String content,
        List<CountryInfoFactItem> items,
        List<Map<String, Object>> data
) {
    public static CountryInfoSection text(String title, String content) {
        return new CountryInfoSection(title, CountryInfoSectionType.TEXT, content, null, null);
    }

    public static CountryInfoSection facts(String title, List<CountryInfoFactItem> items) {
        return new CountryInfoSection(title, CountryInfoSectionType.FACTS, null, items, null);
    }

    public static CountryInfoSection table(String title, List<Map<String, Object>> data) {
        return new CountryInfoSection(title, CountryInfoSectionType.TABLE, null, null, data);
    }

    public static CountryInfoSection list(String title, String content) {
        return new CountryInfoSection(title, CountryInfoSectionType.LIST, content, null, null);
    }
}
