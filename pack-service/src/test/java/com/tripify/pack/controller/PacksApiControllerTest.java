package com.tripify.pack.controller;

import com.tripify.pack.generated.model.PackCard;
import com.tripify.pack.generated.model.PackCardDate;
import com.tripify.pack.generated.model.PackCardHotel;
import com.tripify.pack.generated.model.PackCardPrice;
import com.tripify.pack.generated.model.PacksResponse;
import com.tripify.pack.service.PackQueryService;
import com.tripify.pack.service.model.PackSearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PacksApiControllerTest {

    @Mock
    private PackQueryService packQueryService;

    @InjectMocks
    private PacksApiController packsApiController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(packsApiController).build();
    }

    @Test
    void getPacksReturnsCompletedPackCards() throws Exception {
        UUID packId = UUID.randomUUID();
        PackCard card = samplePackCard(packId);
        when(packQueryService.getPacks(org.mockito.ArgumentMatchers.any(PackSearchCriteria.class)))
                .thenReturn(new PacksResponse().items(List.of(card)));

        mockMvc.perform(get("/v1/packs")
                        .header("X-Anonymous-Id", "anon-123")
                        .header("X-Request-Id", "req-1")
                        .queryParam("origin_country", "RU")
                        .queryParam("origin_city", "MOW")
                        .queryParam("destination_country", "TH")
                        .queryParam("destination_city", "KBV")
                        .queryParam("date_from", "2026-06-04")
                        .queryParam("date_to", "2026-06-26")
                        .queryParam("adults", "2")
                        .queryParam("children", "1")
                        .queryParam("budget", "50000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(packId.toString()));

        ArgumentCaptor<PackSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(PackSearchCriteria.class);
        verify(packQueryService).getPacks(criteriaCaptor.capture());
        assertThat(criteriaCaptor.getValue()).isEqualTo(new PackSearchCriteria(
                "RU", "MOW", "TH", "KBV",
                LocalDate.of(2026, 6, 4),
                LocalDate.of(2026, 6, 26),
                2, 1, 50_000L
        ));
    }

    @Test
    void getPacksUserViewUsesUserIdHeaderWhenPresent() throws Exception {
        UUID packId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        when(packQueryService.getPacksUserView(eq(userId.toString())))
                .thenReturn(new PacksResponse().items(List.of(samplePackCard(packId))));

        mockMvc.perform(get("/v1/packs/user-view")
                        .header("X-User-Id", userId.toString())
                        .header("X-Anonymous-Id", "anon-123")
                        .header("X-Request-Id", "req-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1));

        verify(packQueryService).getPacksUserView(eq(userId.toString()));
    }

    @Test
    void getPacksUserViewUsesAnonymousIdWhenJwtAbsent() throws Exception {
        UUID packId = UUID.randomUUID();
        when(packQueryService.getPacksUserView(eq("anon-123")))
                .thenReturn(new PacksResponse().items(List.of(samplePackCard(packId))));

        mockMvc.perform(get("/v1/packs/user-view")
                        .header("X-Anonymous-Id", "anon-123")
                        .header("X-Request-Id", "req-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.user_id").doesNotExist());

        verify(packQueryService).getPacksUserView(eq("anon-123"));
    }

    private static PackCard samplePackCard(UUID packId) {
        return new PackCard(
                packId,
                "RU",
                "MOW",
                new PackCardHotel("Switzerland Palace Hotel", 5)
                        .mealType("ALL_INCLUSIVE")
                        .imageUrl(URI.create("https://s3.example.com/hotel.jpg")),
                new PackCardDate(LocalDate.of(2026, 12, 12), LocalDate.of(2026, 12, 31)),
                19,
                new PackCardPrice(40000L, "RUB")
        ).imagePackUrl(URI.create("https://cdn.example.com/packs/preview.jpg"));
    }
}
