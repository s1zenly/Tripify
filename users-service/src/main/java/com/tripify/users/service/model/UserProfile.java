package com.tripify.users.service.model;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfile {

    private UUID userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String city;
    private String country;
    private String currency;
    private String citizenship;
    private Instant createdAt;
    private Instant updatedAt;
}