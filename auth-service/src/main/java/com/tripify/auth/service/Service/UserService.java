package com.tripify.auth.service.Service;

import com.tripify.auth.service.domain.model.User;
import com.tripify.auth.service.exception.ForbiddenException;
import com.tripify.auth.service.repository.contracts.UserRepository;
import com.tripify.auth.service.utils.Constants;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getOrRestore(String normalizedPhone) {
        User user = userRepository.upsertActiveUser(normalizedPhone);

        if (user.isBlocked()) {
            throw new ForbiddenException(Constants.FORBIDDEN_USER_BLOCKED_MESSAGE);
        }

        return user;
    }
}