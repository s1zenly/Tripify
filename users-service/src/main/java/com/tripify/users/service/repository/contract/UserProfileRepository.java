package com.tripify.users.service.repository.contract;

import java.util.Optional;
import java.util.UUID;

import com.tripify.users.service.model.UpdateUserProfileRequest;
import com.tripify.users.service.model.UserProfile;
import com.tripify.users.service.model.UserRegisteredEvent;

public interface UserProfileRepository {

    Optional<UserProfile> findById(UUID userId);

    void createProfile(UserRegisteredEvent userRegisteredEvent);

    Optional<UserProfile> updateProfile(UpdateUserProfileRequest updateUserProfileRequest);
}
