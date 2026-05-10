package com.tripify.auth.service.helper;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Hasher {

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public String hashBCrypt(String rawOtp) {
        return encoder.encode(rawOtp);
    }

    public String hashSHA256(String rawToken) {
        return DigestUtils.sha256Hex(rawToken);
    }

    public boolean matchesBCrypt(String rawOtp, String hash) {
        return encoder.matches(rawOtp, hash);
    }
}
