package com.tripify.auth.service.Service;

import com.tripify.auth.service.exception.BadRequestException;
import org.springframework.stereotype.Service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

@Service
public class PhoneValidationService {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public String validateToE164(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new BadRequestException("Phone number is required");
        }

        try {
            var parsedNumber = phoneNumberUtil.parse(
                    phoneNumber.trim(),
                    null
            );

            if (!phoneNumberUtil.isValidNumber(parsedNumber)) {
                throw new BadRequestException("Invalid phone number");
            }

            return phoneNumberUtil.format(
                    parsedNumber,
                    PhoneNumberUtil.PhoneNumberFormat.E164
            );
        } catch (NumberParseException exception) {
            throw new BadRequestException("Invalid phone number format");
        }
    }
}
