package com.tripify.users.service.service;

import com.tripify.users.generated.model.ErrorCode;
import com.tripify.users.generated.model.UserProfileResponse;
import com.tripify.users.service.client.ConverterModelToDTO;
import com.tripify.users.service.exception.client.NotFoundException;
import com.tripify.users.service.model.UpdateUserProfileRequest;
import com.tripify.users.service.model.UserRegisteredEvent;
import com.tripify.users.service.repository.UserProfileRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepositoryImpl userProfileRepository;

    public UserProfileResponse getCurrentUserProfile(UUID userId) {
        return userProfileRepository.findById(userId)
                .map(ConverterModelToDTO::toResponse)
                .orElseThrow(() -> new NotFoundException(
                        ErrorCode.PROFILE_NOT_FOUND,
                        "User profile not found for user id: " + userId)
                );
    }

    public void createProfile(UserRegisteredEvent event) {
        event.validate();
        userProfileRepository.createProfile(event);
    }

    public UserProfileResponse updateProfile(UpdateUserProfileRequest updateUserProfileRequest) {
        return userProfileRepository.updateProfile(updateUserProfileRequest)
                .map(ConverterModelToDTO::toResponse)
                .orElseThrow(() -> new NotFoundException(
                        ErrorCode.PROFILE_NOT_FOUND,
                        "User profile not found for user id: " + updateUserProfileRequest.userId())
                );
    }
}
