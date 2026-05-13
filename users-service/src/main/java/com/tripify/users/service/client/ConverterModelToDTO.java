package com.tripify.users.service.client;

import com.tripify.users.generated.model.UserProfileResponse;
import com.tripify.users.service.model.UserProfile;

public class ConverterModelToDTO {

    public static UserProfileResponse toResponse(UserProfile profile) {
        return new UserProfileResponse()
                .userId(profile.getUserId())
                .email(profile.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phoneNumber(profile.getPhoneNumber())
                .city(profile.getCity())
                .country(profile.getCountry())
                .currency(profile.getCurrency())
                .citizenship(profile.getCitizenship());
    }
}
