package com.tripify.hotels.parser.job;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.tripify.hotels.parser.dto.HotelsResponseDto;
import com.tripify.hotels.parser.models.Country;
import com.tripify.hotels.parser.service.HotelsParserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParsingJob {

    private static final Logger logger = LoggerFactory.getLogger(ParsingJob.class);

    private final HotelsParserService hotelsParserService;

    private final AtomicInteger countryIndex = new AtomicInteger(0);
    private final Country[] countries = Country.values();

    @Scheduled(fixedRate = 5000)
    public void parseByCountry() {
        int index = countryIndex.getAndUpdate(i -> (i + 1) % countries.length);
        Country country = countries[index];

        logger.info("Start parsing by country - {}", country.getAlpha3());
        List<HotelsResponseDto> result = hotelsParserService.parseByCountry(country);
        logger.info("Success parsing by country - {}", country.getAlpha3());
    }
}
