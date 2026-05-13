package com.tripify.info.controller;

import com.tripify.info.generated.api.InfoAdminApi;
import com.tripify.info.generated.model.CountryInfoRefreshResponse;
import com.tripify.info.mapper.CountryInfoRefreshMapper;
import com.tripify.info.model.CountryInfoDocument;
import com.tripify.info.security.RequestContext;
import com.tripify.info.security.RequestContextResolver;
import com.tripify.info.service.CountryInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCountryInfoController implements InfoAdminApi {

    private final CountryInfoService countryInfoService;
    private final CountryInfoRefreshMapper refreshMapper;
    private final RequestContextResolver requestContextResolver;

    @Override
    public ResponseEntity<CountryInfoRefreshResponse> refreshCountryInfo(
            String xAnonymousId,
            String xRequestId,
            String countryId
    ) {
        RequestContext context = requestContextResolver.resolve(xAnonymousId, xRequestId);

        log.info(
                "Manual country info refresh country_id={} request_id={} actor_id={} actor_type={}",
                countryId,
                context.requestId(),
                context.actor().actorId(),
                context.actor().type()
        );

        CountryInfoDocument document = countryInfoService.refreshCountry(countryId);
        return ResponseEntity.ok(refreshMapper.toRefreshResponse(document));
    }
}
