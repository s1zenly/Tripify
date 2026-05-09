package com.tripify.auth.service.Service;

import java.util.Optional;

import com.tripify.auth.service.domain.model.User;
import com.tripify.auth.service.exception.ForbiddenException;
import com.tripify.auth.service.repository.contracts.UserRepository;
import com.tripify.auth.service.utils.Constants;
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
            throw new ForbiddenException(Constants.FORBIDDEN_USER_BLOCKED_MESSAGE);
        }

        return user;
    }

    public User getOrRestore(String phone) {
        User user = userRepository.upsertActiveUser(phone);

        if (user.isBlocked()) {
            throw new ForbiddenException(Constants.FORBIDDEN_USER_BLOCKED_MESSAGE);
        }

        return user;
    }
}