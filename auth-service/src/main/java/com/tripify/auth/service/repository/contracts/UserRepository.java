package com.tripify.auth.service.repository.contracts;

import java.util.Optional;
import java.util.UUID;

import com.tripify.auth.service.domain.model.User;

public interface UserRepository {

    Optional<User> findById(UUID id);

    Optional<User> findByPhone(String phone);

    void save(User user);

    void update(User user);
}
