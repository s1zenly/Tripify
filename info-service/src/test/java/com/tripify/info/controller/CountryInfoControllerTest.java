package com.tripify.info.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import java.time.LocalDate;
import java.util.Optional;

import com.tripify.info.generated.model.CountryInfoResponse;
import com.tripify.info.generated.model.CountryInfoTab;
import com.tripify.info.generated.model.CountryInfoTabCode;
import com.tripify.info.generated.model.CurrencyRateItem;
import com.tripify.info.mapper.CountryInfoMapper;
import com.tripify.info.model.CountryInfoDocument;
import com.tripify.info.model.CountryInfoSource;
import com.tripify.info.security.RequestActor;
import com.tripify.info.service.CountryInfoQueryResult;
import com.tripify.info.weather.WeatherDayForecast;
import com.tripify.info.weather.WeatherForecast;
import com.tripify.info.security.RequestActorType;
import com.tripify.info.security.RequestContext;
import com.tripify.info.security.RequestContextResolver;
import com.tripify.info.service.CountryInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CountryInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CountryInfoService countryInfoService;

    @MockitoBean
    private CountryInfoMapper countryInfoMapper;

    @MockitoBean
    private RequestContextResolver requestContextResolver;

    @Test
    void returnsCountryInfoForSupportedAlpha2() throws Exception {
        CountryInfoDocument document = new CountryInfoDocument(
                "AE",
                "ОАЭ",
                "AED",
                "https://www.tutu.ru/geo/strana/united_arab_emirates/",
                List.of(),
                java.time.Instant.parse("2026-05-31T10:00:00Z"),
                CountryInfoSource.TUTU
        );

        LocalDate dateFrom = LocalDate.of(2026, 6, 15);
        LocalDate dateTo = LocalDate.of(2026, 6, 17);

        WeatherForecast weather = new WeatherForecast(
                dateFrom,
                dateTo,
                24.4539,
                54.3773,
                List.of(new WeatherDayForecast(
                        dateFrom,
                        35.0,
                        com.tripify.info.weather.WeatherCondition.CLEAR,
                        0.0
                ))
        );
        CountryInfoQueryResult queryResult = new CountryInfoQueryResult(document, Optional.of(weather));

        CountryInfoResponse response = new CountryInfoResponse(
                "AE",
                "ОАЭ",
                "AED",
                List.of(new CurrencyRateItem("USD", 3.67)),
                List.of(
                        new CountryInfoTab(CountryInfoTabCode.OVERVIEW, "Основное про страну", List.of()),
                        new CountryInfoTab(CountryInfoTabCode.WEATHER, "Погода", List.of())
                )
        );

        when(requestContextResolver.resolve("anon-123", "req-456"))
                .thenReturn(new RequestContext(new RequestActor("anon-123", RequestActorType.ANONYMOUS), "req-456"));
        when(countryInfoService.getCountryInfo("AE", dateFrom, dateTo)).thenReturn(queryResult);
        when(countryInfoMapper.toApiResponse(queryResult)).thenReturn(response);

        mockMvc.perform(get("/v1/countries/AE/info")
                        .header("X-Anonymous-Id", "anon-123")
                        .header("X-Request-Id", "req-456")
                        .queryParam("date_from", "2026-06-15")
                        .queryParam("date_to", "2026-06-17"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country_id").value("AE"))
                .andExpect(jsonPath("$.country_name").value("ОАЭ"))
                .andExpect(jsonPath("$.local_currency").value("AED"))
                .andExpect(jsonPath("$.tabs[?(@.code=='weather')]").exists())
                .andExpect(jsonPath("$.tabs[0].code").value("overview"));

        verify(countryInfoService).getCountryInfo(eq("AE"), eq(dateFrom), eq(dateTo));
    }
}
