package com.tripify.hotels.parser.service;

import com.tripify.hotels.parser.dto.HotelsResponseDto;
import com.tripify.hotels.parser.models.Country;
import com.tripify.hotels.parser.models.Provider;
import com.tripify.hotels.parser.models.HotelsProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Смотрит всех подключённых провайдеров из enum, у каждого асинхронно вызывает parse по стране,
 * затем на результате вызывает adapt.
 */
@Service
public class HotelsParserService {

    private final Map<Provider, HotelsProvider> providersByType;

    public HotelsParserService(List<HotelsProvider> providers) {
        this.providersByType = providers.stream()
                .collect(Collectors.toMap(HotelsProvider::getProvider, Function.identity()));
    }

    /**
     * По стране запускает у всех провайдеров из enum parse (асинхронно), затем adapt.
     *
     * @param country страна, по которой парсим отели
     * @return список унифицированного формата отелей для отправки в Kafka
     */
    public List<HotelsResponseDto> parseByCountry(Country country) {
        List<CompletableFuture<HotelsResponseDto>> futures = Stream.of(Provider.values())
                .filter(providersByType::containsKey)
                .map(provider -> CompletableFuture.supplyAsync(
                        () -> providersByType.get(provider).supplyHotels(country)
                ))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }
}
