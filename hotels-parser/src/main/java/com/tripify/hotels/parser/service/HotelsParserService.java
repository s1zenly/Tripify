package com.tripify.hotels.parser.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tripify.hotels.parser.models.Country;
import com.tripify.hotels.parser.models.HotelsProvider;
import com.tripify.hotels.parser.models.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Смотрит всех подключённых провайдеров из enum, у каждого асинхронно вызывает parse по стране,
 * затем на результате вызывает adapt.
 */
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
     * По стране запускает у всех провайдеров из enum parse (асинхронно), затем adapt.
     *
     * @param country страна, по которой парсим отели
     */
    public void parseByCountry(Country country) {
        Stream.of(Provider.values())
                .filter(providersByType::containsKey)
                .filter(providersExecutors::containsKey)
                .forEach(provider -> parseProvider(provider, country));
    }

    private void parseProvider(Provider provider, Country country) {
        providersExecutors.get(provider)
                .submit(() -> providersByType.get(provider).supplyHotels(country))
                .orTimeout(60, TimeUnit.SECONDS)
                .thenAccept(hotelDto -> {
                    if (hotelDto == null) {
                        logger.warn("Provider returned null: provider={}, country={}", provider, country.getAlpha3());
                    }

                    hotelsKafkaProducer.sendHotels(hotelDto);
                    logger.info("Provider parsed hotel successfully: provider={}, country={}", provider, country.getAlpha3());
                })
                .exceptionally(exception -> {
                    logger.error("Failed to parse provider={}, country={}", provider, country.getAlpha3(), exception);
                    return null;
                });
    }
}
