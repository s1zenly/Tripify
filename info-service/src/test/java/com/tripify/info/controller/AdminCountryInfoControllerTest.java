package com.tripify.info.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import com.tripify.info.model.CountryInfoDocument;
import com.tripify.info.model.CountryInfoSource;
import com.tripify.info.model.CountryInfoTab;
import com.tripify.info.model.CountryInfoTabCode;
import com.tripify.info.security.RequestActor;
import com.tripify.info.security.RequestActorType;
import com.tripify.info.security.RequestContext;
import com.tripify.info.security.RequestContextResolver;
import com.tripify.info.service.CountryInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AdminCountryInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CountryInfoService countryInfoService;

    @MockitoBean
    private RequestContextResolver requestContextResolver;

    @Test
    void refreshesCountryByAlpha2() throws Exception {
        CountryInfoDocument document = new CountryInfoDocument(
                "AE",
                "ОАЭ",
                "AED",
                "https://www.tutu.ru/geo/strana/united_arab_emirates/",
                List.of(new CountryInfoTab(CountryInfoTabCode.OVERVIEW, "Основное про страну", List.of())),
                Instant.parse("2026-05-31T10:00:00Z"),
                CountryInfoSource.TUTU
        );

        when(requestContextResolver.resolve("anon-123", "req-789"))
                .thenReturn(new RequestContext(new RequestActor("anon-123", RequestActorType.ANONYMOUS), "req-789"));
        when(countryInfoService.refreshCountry("AE")).thenReturn(document);

        mockMvc.perform(post("/v1/admin/countries/AE/refresh")
                        .header("X-Anonymous-Id", "anon-123")
                        .header("X-Request-Id", "req-789")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country_id").value("AE"))
                .andExpect(jsonPath("$.country_name").value("ОАЭ"))
                .andExpect(jsonPath("$.tabs_count").value(1));

        verify(countryInfoService).refreshCountry(eq("AE"));
    }
}
