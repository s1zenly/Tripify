package com.tripify.info.model;

import java.util.List;

public record CountryInfoTab(
        CountryInfoTabCode code,
        String title,
        List<CountryInfoSection> sections
) {
}
