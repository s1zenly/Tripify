package com.tripify.info.mapper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.tripify.info.generated.model.CountryInfoRefreshResponse;
import com.tripify.info.model.CountryInfoDocument;
import org.springframework.stereotype.Component;

@Component
public class CountryInfoRefreshMapper {

    public CountryInfoRefreshResponse toRefreshResponse(CountryInfoDocument document) {
        int sectionsCount = document.tabs().stream()
                .mapToInt(tab -> tab.sections().size())
                .sum();

        return new CountryInfoRefreshResponse(
                document.countryId(),
                document.countryName(),
                document.tabs().size(),
                sectionsCount,
                OffsetDateTime.ofInstant(document.updatedAt(), ZoneOffset.UTC)
        );
    }
}
