package com.tripify.info.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tripify.info.currency.ExchangeRateService;
import com.tripify.info.generated.model.CountryInfoFactItem;
import com.tripify.info.generated.model.CountryInfoResponse;
import com.tripify.info.generated.model.CountryInfoSection;
import com.tripify.info.generated.model.CountryInfoSectionType;
import com.tripify.info.generated.model.CountryInfoTab;
import com.tripify.info.generated.model.CurrencyRateItem;
import com.tripify.info.model.CountryInfoDocument;
import com.tripify.info.model.CountryInfoTabCode;
import com.tripify.info.service.CountryInfoQueryResult;
import com.tripify.info.weather.WeatherDayForecast;
import com.tripify.info.weather.WeatherForecast;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountryInfoMapper {

    private final ExchangeRateService exchangeRateService;

    public CountryInfoResponse toApiResponse(CountryInfoQueryResult result) {
        CountryInfoDocument document = result.document();
        List<CountryInfoTab> tabs = new ArrayList<>(
                document.tabs().stream().map(this::toApiTab).toList()
        );
        result.weather().ifPresent(forecast -> tabs.add(toWeatherTab(forecast)));

        return new CountryInfoResponse(
                document.countryId(),
                document.countryName(),
                document.localCurrency(),
                toApiRates(document.localCurrency()),
                tabs
        );
    }

    private List<CurrencyRateItem> toApiRates(String localCurrency) {
        return exchangeRateService.getRates(localCurrency).rates().stream()
                .map(rate -> new CurrencyRateItem(rate.currency(), rate.rate().doubleValue()))
                .toList();
    }

    private CountryInfoTab toApiTab(com.tripify.info.model.CountryInfoTab tab) {
        return new CountryInfoTab(
                com.tripify.info.generated.model.CountryInfoTabCode.fromValue(tab.code().getCode()),
                tab.title(),
                tab.sections().stream().map(this::toApiSection).toList()
        );
    }

    private CountryInfoTab toWeatherTab(WeatherForecast forecast) {
        return new CountryInfoTab(
                com.tripify.info.generated.model.CountryInfoTabCode.WEATHER,
                CountryInfoTabCode.WEATHER.title(),
                List.of(toApiSection(buildWeatherSection(forecast)))
        );
    }

    private com.tripify.info.model.CountryInfoSection buildWeatherSection(WeatherForecast forecast) {
        List<Map<String, Object>> rows = forecast.days().stream()
                .map(this::toWeatherDayRow)
                .toList();
        return com.tripify.info.model.CountryInfoSection.table(
                "Прогноз на период пребывания",
                rows
        );
    }

    private Map<String, Object> toWeatherDayRow(WeatherDayForecast day) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("date", day.date().toString());
        row.put("temperature_avg_c", day.temperatureAvgC());
        row.put("condition", day.condition().apiValue());
        row.put("precipitation_mm", day.precipitationMm());
        return row;
    }

    private CountryInfoSection toApiSection(com.tripify.info.model.CountryInfoSection section) {
        CountryInfoSection apiSection = new CountryInfoSection(
                section.title(),
                CountryInfoSectionType.fromValue(section.type().getValue())
        );

        if (section.content() != null) {
            apiSection.content(section.content());
        }
        if (section.items() != null) {
            apiSection.items(section.items().stream()
                    .map(item -> new CountryInfoFactItem(item.label(), item.value()))
                    .toList());
        }
        if (section.data() != null) {
            apiSection.data(copyData(section.data()));
        }

        return apiSection;
    }

    private List<Map<String, Object>> copyData(List<Map<String, Object>> data) {
        return data.stream()
                .map(row -> row.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .toList();
    }
}
