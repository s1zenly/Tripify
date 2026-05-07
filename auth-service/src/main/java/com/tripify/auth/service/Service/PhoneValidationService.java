package com.tripify.auth.service.Service;

import org.springframework.stereotype.Service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

@Service
public class PhoneValidationService {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public String normalizeToE164(String countryCode, String phoneNumber) {
        if (countryCode == null || countryCode.isBlank()) {
            throw new IllegalArgumentException("Country code is required");
        }

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }

        String normalizedCountryCode = normalizeCountryCode(countryCode);
        String normalizedPhoneNumber = normalizePhoneNumber(phoneNumber);

        String fullPhoneNumber = normalizedCountryCode + normalizedPhoneNumber;

        try {
            var parsedNumber = phoneNumberUtil.parse(fullPhoneNumber, null);

            if (!phoneNumberUtil.isValidNumber(parsedNumber)) {
                throw new IllegalArgumentException("Invalid phone number");
            }

            return phoneNumberUtil.format(
                    parsedNumber,
                    PhoneNumberUtil.PhoneNumberFormat.E164
            );
        } catch (NumberParseException exception) {
            throw new IllegalArgumentException("Invalid phone number format", exception);
        }
    }

    private String normalizeCountryCode(String countryCode) {
        String cleaned = countryCode.trim().replaceAll("\\s+", "");

        if (!cleaned.startsWith("+")) {
            cleaned = "+" + cleaned;
        }

        if (!cleaned.matches("^\\+\\d{1,4}$")) {
            throw new IllegalArgumentException("Invalid country code");
        }

        return cleaned;
    }

    private String normalizePhoneNumber(String phoneNumber) {
        String cleaned = phoneNumber.trim()
                .replaceAll("[\\s()\\-]", "");

        if (!cleaned.matches("^\\d{4,15}$")) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        return cleaned;
    }
}
