package com.tripify.info.controller;

import com.tripify.info.generated.api.InfoApi;
import com.tripify.info.generated.model.CountryInfoResponse;
import com.tripify.info.mapper.CountryInfoMapper;
import java.time.LocalDate;

import com.tripify.info.security.RequestContext;
import com.tripify.info.service.CountryInfoQueryResult;
import com.tripify.info.security.RequestContextResolver;
import com.tripify.info.service.CountryInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CountryInfoController implements InfoApi {

    private final CountryInfoService countryInfoService;
    private final CountryInfoMapper countryInfoMapper;
    private final RequestContextResolver requestContextResolver;

    @Override
    public ResponseEntity<CountryInfoResponse> getCountryInfo(
            String xAnonymousId,
            String xRequestId,
            String countryId,
            LocalDate dateFrom,
            LocalDate dateTo
    ) {
        RequestContext context = requestContextResolver.resolve(xAnonymousId, xRequestId);

        log.info(
                "Get country info country_id={} date_from={} date_to={} request_id={} actor_id={} actor_type={}",
                countryId,
                dateFrom,
                dateTo,
                context.requestId(),
                context.actor().actorId(),
                context.actor().type()
        );

        CountryInfoQueryResult result = countryInfoService.getCountryInfo(countryId, dateFrom, dateTo);
        return ResponseEntity.ok(countryInfoMapper.toApiResponse(result));
    }
}
