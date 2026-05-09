package com.tripify.auth.service.otp;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpCodeGenerator {

    private static final int OTP_BOUND = 10_000;

    private final SecureRandom secureRandom = new SecureRandom();

    public String generate() {
        int code = secureRandom.nextInt(OTP_BOUND);

        return String.format("%04d", code);
    }
}
