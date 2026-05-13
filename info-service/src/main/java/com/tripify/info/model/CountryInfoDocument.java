package com.tripify.info.model;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "country_info")
public record CountryInfoDocument(
        @Id
        String countryId,
        String countryName,
        String localCurrency,
        String sourceUrl,
        List<CountryInfoTab> tabs,
        @Indexed
        Instant updatedAt,
        CountryInfoSource source
) {
    public CountryInfoDocument {
        if (source == null) {
            source = CountryInfoSource.TUTU;
        }
    }
}
