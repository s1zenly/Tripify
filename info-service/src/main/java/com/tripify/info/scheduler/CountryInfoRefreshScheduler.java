package com.tripify.info.scheduler;

import com.tripify.info.config.CountryInfoProperties;
import com.tripify.info.service.CountryInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountryInfoRefreshScheduler {

    private final CountryInfoService countryInfoService;
    private final CountryInfoProperties properties;

    @Scheduled(cron = "${tripify.info.scraping.cron}")
    public void refreshAllCountries() {
        if (!properties.scraping().enabled()) {
            return;
        }

        countryInfoService.refreshAllCountries();
    }
}
