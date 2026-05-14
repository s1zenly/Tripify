package com.tripify.pack.controller;

import com.tripify.pack.generated.api.PacksApi;
import com.tripify.pack.generated.model.Pack;
import com.tripify.pack.generated.model.PacksResponse;
import com.tripify.pack.security.RequestSubjectResolver;
import com.tripify.pack.service.PackQueryService;
import com.tripify.pack.service.model.PackSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PacksApiController implements PacksApi {

    private final PackQueryService packQueryService;

    @Override
    public ResponseEntity<PacksResponse> getPacks(
            String xAnonymousId,
            String xRequestId,
            String originCountry,
            String originCity,
            String destinationCountry,
            String destinationCity,
            LocalDate dateFrom,
            LocalDate dateTo,
            Integer adults,
            Integer children,
            @Nullable Long budget
    ) {
        PackSearchCriteria criteria = new PackSearchCriteria(
                originCountry,
                originCity,
                destinationCountry,
                destinationCity,
                dateFrom,
                dateTo,
                adults,
                children,
                budget
        );
        return ResponseEntity.ok(packQueryService.getPacks(criteria));
    }

    @Override
    public ResponseEntity<Pack> getPackById(
            String xAnonymousId,
            String xRequestId,
            UUID packId
    ) {
        return ResponseEntity.ok(packQueryService.getPackById(packId));
    }

    @Override
    public ResponseEntity<PacksResponse> getPopularPacks(String xRequestId) {
        return ResponseEntity.ok(packQueryService.getPopularPacks());
    }

    @Override
    public ResponseEntity<PacksResponse> getPacksUserView(
            String xAnonymousId,
            String xRequestId
    ) {
        String subjectId = RequestSubjectResolver.resolveSubjectId(xAnonymousId);
        return ResponseEntity.ok(packQueryService.getPacksUserView(subjectId));
    }
}
