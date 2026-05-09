package com.tripify.auth.service.otp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class OtpHasher {

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public String hash(String rawOtp) {
        return encoder.encode(rawOtp);
    }

    public boolean matches(String rawOtp, String hash) {
        return encoder.matches(rawOtp, hash);
    }
}
