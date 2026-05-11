package com.tripify.auth.service.repository.contracts;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.tripify.auth.service.domain.model.User;

public interface UserRepository {

    Optional<User> findByPhone(String phone);

    Optional<User> findById(UUID id);

    User createOrActivateUser(String phone, Instant now);
}
