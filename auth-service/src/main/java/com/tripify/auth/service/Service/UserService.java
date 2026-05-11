package com.tripify.auth.service.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.tripify.auth.generated.model.ErrorCode;
import com.tripify.auth.service.client.Messages;
import com.tripify.auth.service.domain.model.User;
import com.tripify.auth.service.exception.ForbiddenException;
import com.tripify.auth.service.repository.contracts.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> checkUserByBlocked(String phone) {
        Optional<User> user =  userRepository.findByPhone(phone);
        log.info("User - {}", user.orElse(null));

        if (user.isPresent() && user.get().isBlocked()) {
            throw new ForbiddenException(ErrorCode.USER_BLOCKED, Messages.FORBIDDEN_USER_BLOCKED_MESSAGE);
        }

        return user;
    }

    public User createOrActivateUser(String phone, Instant now) {
        checkUserByBlocked(phone);
        return userRepository.createOrActivateUser(phone, now);
    }

    public User getActiveUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ForbiddenException(ErrorCode.USER_INACTIVE, Messages.FORBIDDEN_USER_INACTIVE_MESSAGE));

        if (user.isBlocked() || user.isDeleted()) {
            throw new ForbiddenException(ErrorCode.USER_INACTIVE, Messages.FORBIDDEN_USER_INACTIVE_MESSAGE);
        }

        return user;
    }
}