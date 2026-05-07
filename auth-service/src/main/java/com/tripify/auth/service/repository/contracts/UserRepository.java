package com.tripify.auth.service.repository.contracts;

import com.tripify.auth.service.domain.model.User;

public interface UserRepository {

    User upsertActiveUser(String phone);
}
