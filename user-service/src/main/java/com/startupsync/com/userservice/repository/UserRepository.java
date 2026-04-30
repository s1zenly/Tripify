package com.startupsync.com.userservice.repository;


import com.startupsync.com.userservice.entity.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}