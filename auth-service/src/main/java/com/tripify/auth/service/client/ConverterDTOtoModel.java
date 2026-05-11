package com.tripify.auth.service.client;

import java.util.UUID;

import com.tripify.auth.service.domain.model.LogoutRequest;

public class ConverterDTOtoModel {

    public static LogoutRequest createLogoutRequest(
            com.tripify.auth.generated.model.LogoutRequest logoutRequest,
            UUID userId
    ) {
        return new LogoutRequest(
                logoutRequest.getRefreshToken(),
                userId
        );
    }
}
