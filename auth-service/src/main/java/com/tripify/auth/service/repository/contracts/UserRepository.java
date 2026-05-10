package com.tripify.auth.service.repository.contracts;

import java.time.Instant;
import java.util.Optional;

import com.tripify.auth.service.domain.model.User;

public interface UserRepository {

    Optional<User> findByPhone(String phone);

    User createOrActivateUser(String phone, Instant now);
}
