package com.tripify.hotels.parser.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tripify.hotels.parser.models.City;
import com.tripify.hotels.parser.models.Country;
import com.tripify.hotels.parser.models.HotelsProvider;
import com.tripify.hotels.parser.models.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HotelsParserService {

    private static final Logger logger = LoggerFactory.getLogger(HotelsParserService.class);

    private final Map<Provider, HotelsProvider> providersByType;
    private final Map<Provider, ProviderExecutionContext> providersExecutors;
    private final HotelsKafkaProducer hotelsKafkaProducer;

    public HotelsParserService(
            List<HotelsProvider> providers,
            Map<Provider, ProviderExecutionContext> providersExecutors,
            HotelsKafkaProducer hotelsKafkaProducer
    ) {
        this.providersByType = providers.stream()
                .collect(Collectors.toMap(HotelsProvider::getProvider, Function.identity()));
        this.providersExecutors = providersExecutors;
        this.hotelsKafkaProducer = hotelsKafkaProducer;
    }

    /**
     * Парсит отели по стране: последовательно перебирает все города и для каждого
     * асинхронно запускает парсинг у всех подключённых провайдеров.
     */
    public void parseByCountry(Country country) {
        for (City city : country.getCities()) {
            parseByCity(city);
        }
    }

    private void parseByCity(City city) {
        Stream.of(Provider.values())
                .filter(providersByType::containsKey)
                .filter(providersExecutors::containsKey)
                .forEach(provider -> parseProvider(provider, city));
    }

    private void parseProvider(Provider provider, City city) {
        providersExecutors.get(provider)
                .submit(() -> providersByType.get(provider).supplyHotels(city))
                .orTimeout(60, TimeUnit.SECONDS)
                .thenAccept(hotelDto -> {
                    if (hotelDto == null) {
                        logger.warn("Provider returned null: provider={}, city={}, country={}",
                                provider, city.getDisplayName(), city.getCountry().getAlpha3());
                        return;
                    }

                    hotelsKafkaProducer.sendHotels(hotelDto);
                    logger.info("Parsed successfully: provider={}, city={}, country={}, hotels={}",
                            provider, city.getDisplayName(), city.getCountry().getAlpha3(),
                            hotelDto.getTotalHotels());
                })
                .exceptionally(exception -> {
                    logger.error("Failed to parse: provider={}, city={}, country={}",
                            provider, city.getDisplayName(), city.getCountry().getAlpha3(), exception);
                    return null;
                });
    }
}
