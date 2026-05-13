package com.tripify.users.service.model;

import java.util.UUID;
import java.util.regex.Pattern;

import com.tripify.users.generated.model.ErrorCode;
import com.tripify.users.service.exception.client.BadRequestException;

public record UpdateUserProfileRequest(
        UUID userId,
        String email,
        String firstName,
        String lastName,
        String city,
        String country,
        String currency,
        String citizenship
) {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public void validate() {
        validateUserId();
        validateEmail();
        validateLength(firstName, "firstName", 100);
        validateLength(lastName, "lastName", 100);
        validateLength(city, "city", 100);
        validateLength(country, "country", 100);
        validateLength(citizenship, "citizenship", 100);
        validateCurrency();
    }

    private void validateUserId() {
        if (userId == null) {
            throw new BadRequestException(ErrorCode.UPDATE_USER_PROFILE_ERROR, "User id must not be null");
        }
    }

    private void validateEmail() {
        if (email == null) {
            return;
        }

        if (email.isBlank()) {
            throw new BadRequestException(ErrorCode.UPDATE_USER_PROFILE_ERROR, "Email must not be blank");
        }

        if (email.length() > 255) {
            throw new BadRequestException(ErrorCode.UPDATE_USER_PROFILE_ERROR, "Email is too long");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new BadRequestException(ErrorCode.UPDATE_USER_PROFILE_ERROR, "Email has invalid format");
        }
    }

    private void validateCurrency() {
        if (currency == null) {
            return;
        }

        if (currency.length() != 3) {
            throw new BadRequestException(ErrorCode.UPDATE_USER_PROFILE_ERROR, "Currency must contain 3 letters");
        }
    }

    private void validateLength(String value, String field, int maxLength) {
        if (value == null) {
            return;
        }

        if (value.isBlank()) {
            throw new BadRequestException(ErrorCode.UPDATE_USER_PROFILE_ERROR, field + " must not be blank");
        }

        if (value.length() > maxLength) {
            throw new BadRequestException(ErrorCode.UPDATE_USER_PROFILE_ERROR, field + " is too long");
        }
    }
}