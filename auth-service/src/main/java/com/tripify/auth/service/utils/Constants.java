package com.tripify.auth.service.utils;

public final class Constants {

    public static final String PREFIX_RATE_LIMITER_OTP_REQUEST_KEY = "rate-limit:otp:request:";
    public static final String PREFIX_RATE_LIMITER_OTP_VERIFY_KEY = "rate-limit:otp:verify:";

    public static final int MAX_OTP_REQUESTS = 5;
    public static final int MAX_OTP_VERIFY_REQUESTS = 20;

    public static final int MAX_OTP_VERIFY_ATTEMPTS = 5;
}
