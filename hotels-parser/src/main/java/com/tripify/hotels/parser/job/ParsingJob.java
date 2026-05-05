package com.tripify.hotels.parser.job;

import java.util.concurrent.atomic.AtomicInteger;

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
    public void parseNextCountry() {
        int index = countryIndex.getAndUpdate(i -> (i + 1) % countries.length);
        Country country = countries[index];

        logger.info("Start parsing country {} ({}) — {} cities",
                country.getDisplayName(), country.getAlpha2(), country.getCities().size());

        hotelsParserService.parseByCountry(country);

        logger.info("Submitted all cities for country {} ({})",
                country.getDisplayName(), country.getAlpha2());
    }
}
