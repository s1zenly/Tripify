package com.tripify.users.service.controller;

import java.util.Objects;
import java.util.UUID;

import com.tripify.users.generated.api.UserInfoApi;
import com.tripify.users.generated.model.UpdateUserProfileRequest;
import com.tripify.users.generated.model.UserProfileResponse;
import com.tripify.users.service.client.ConverterDTOtoModel;
import com.tripify.users.service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserProfileController implements UserInfoApi {

    private final UserProfileService userProfileService;

    @Override
    public ResponseEntity<UserProfileResponse> getCurrentUserProfile() {
        UserProfileResponse response = userProfileService.getCurrentUserProfile(fetchUserId());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserProfileResponse> updateCurrentUserProfile(UpdateUserProfileRequest updateUserProfileRequest) {
        UUID userId = fetchUserId();
        log.info("userId = {}", userId);
        log.info("updateRequest = {}", updateUserProfileRequest.toString());
        UserProfileResponse response = userProfileService
                .updateProfile(ConverterDTOtoModel.toUpdateUserProfileRequestModel(userId, updateUserProfileRequest));

        return ResponseEntity.ok(response);
    }

    private UUID fetchUserId() {
        Jwt jwt = (Jwt) Objects.requireNonNull(
                SecurityContextHolder.getContext()
                        .getAuthentication()
        ).getPrincipal();

        return UUID.fromString(jwt.getSubject());
    }
}
