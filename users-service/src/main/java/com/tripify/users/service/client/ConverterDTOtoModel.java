package com.tripify.users.service.client;

import java.util.UUID;

import com.tripify.users.service.model.UpdateUserProfileRequest;

public final class ConverterDTOtoModel {

    public static UpdateUserProfileRequest toUpdateUserProfileRequestModel(
            UUID userId,
            com.tripify.users.generated.model.UpdateUserProfileRequest updateUserProfileRequest
    ) {
        return new UpdateUserProfileRequest(
                userId,
                updateUserProfileRequest.getEmail(),
                updateUserProfileRequest.getFirstName(),
                updateUserProfileRequest.getLastName(),
                updateUserProfileRequest.getCity(),
                updateUserProfileRequest.getCountry(),
                updateUserProfileRequest.getCurrency(),
                updateUserProfileRequest.getCitizenship()
        );
    }
}
