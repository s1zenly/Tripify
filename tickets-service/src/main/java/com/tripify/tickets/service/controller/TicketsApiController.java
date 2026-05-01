package com.tripify.tickets.service.controller;

import com.tripify.tickets.generated.api.TicketsApi;
import com.tripify.tickets.generated.model.Currency;
import com.tripify.tickets.generated.model.TicketDetailResponse;
import com.tripify.tickets.generated.model.TicketFiltersResponse;
import com.tripify.tickets.generated.model.TicketsResponse;
import com.tripify.tickets.service.kafka.model.UserType;
import com.tripify.tickets.service.mapper.TicketApiMapper;
import com.tripify.tickets.service.mapper.TicketDetailApiMapper;
import com.tripify.tickets.service.model.search.TicketSearchRequest;
import com.tripify.tickets.service.model.unified.Passengers;
import com.tripify.tickets.service.service.TicketFilterQueryService;
import com.tripify.tickets.service.service.TicketPackViewPublisher;
import com.tripify.tickets.service.service.TicketsSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketsApiController implements TicketsApi {

    private final TicketsSearchService ticketsSearchService;
    private final TicketFilterQueryService ticketFilterQueryService;
    private final TicketApiMapper ticketApiMapper;
    private final TicketDetailApiMapper ticketDetailApiMapper;
    private final TicketPackViewPublisher ticketPackViewPublisher;

    @Override
    public ResponseEntity<TicketsResponse> searchTickets(
            String originCityCode,
            String destinationCityCode,
            LocalDate departureDate,
            LocalDate returnDate,
            Currency currency,
            Integer adults,
            @Nullable Long budgetMaxAmount,
            Integer children,
            Integer infants,
            @Nullable List<String> filters
    ) {
        TicketSearchRequest request = buildSearchRequest(
                originCityCode,
                destinationCityCode,
                departureDate,
                returnDate,
                adults,
                budgetMaxAmount,
                currency,
                children,
                infants,
                filters
        );

        var result = ticketsSearchService.search(request);
        return ResponseEntity.ok(ticketApiMapper.toTicketsResponse(result.offers(), currency.getValue()));
    }

    @Override
    public ResponseEntity<TicketFiltersResponse> getTicketFilters() {
        return ResponseEntity.ok(ticketFilterQueryService.getFiltersCatalog());
    }

    @Override
    public ResponseEntity<TicketDetailResponse> getTicketById(
            String xAnonymousId,
            String xGenerationId,
            Integer xPackRevision,
            String xGenerationMode,
            String xRequestId,
            String id,
            String originCityCode,
            String destinationCityCode,
            LocalDate departureDate,
            LocalDate returnDate,
            Currency currency,
            Integer adults,
            @Nullable Integer xTicketsRevision,
            @Nullable Long budgetMaxAmount,
            Integer children,
            Integer infants
    ) {
        var offer = ticketsSearchService.getById(id);
        TicketDetailResponse response = ticketDetailApiMapper.toTicketDetailResponse(offer, currency.getValue());

        String userId = extractUserIdOrNull();
        UserType userType = userId != null ? UserType.AUTH : UserType.ANONYMOUS;

        ticketPackViewPublisher.publishTicketDetailView(
                TicketPackViewPublisher.toHeaders(
                        userType,
                        userId,
                        xAnonymousId,
                        xGenerationId,
                        xPackRevision,
                        xGenerationMode,
                        xRequestId,
                        xTicketsRevision
                ),
                TicketPackViewPublisher.toSearch(
                        originCityCode,
                        destinationCityCode,
                        departureDate,
                        returnDate,
                        currency.getValue(),
                        adults,
                        children,
                        infants,
                        budgetMaxAmount
                ),
                response.getTicket()
        );

        return ResponseEntity.ok(response);
    }

    private static TicketSearchRequest buildSearchRequest(
            String originCityCode,
            String destinationCityCode,
            LocalDate departureDate,
            LocalDate returnDate,
            Integer adults,
            Long budgetMaxAmount,
            Currency currency,
            Integer children,
            Integer infants,
            List<String> filters
    ) {
        return TicketSearchRequest.builder()
                .originCityCode(originCityCode)
                .destinationCityCode(destinationCityCode)
                .departureDate(departureDate)
                .returnDate(returnDate)
                .budgetMaxAmount(budgetMaxAmount)
                .currency(com.tripify.tickets.service.model.unified.Currency.fromCode(currency.getValue()))
                .passengers(Passengers.builder()
                        .adults(adults)
                        .children(children)
                        .infants(infants)
                        .build())
                .filters(filters)
                .build();
    }

    private static String extractUserIdOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Jwt jwt)) {
            return null;
        }
        return jwt.getSubject();
    }
}
