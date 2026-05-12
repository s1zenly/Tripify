package com.tripify.notification.service.utils;

public final class SecureUtils {

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) {
            return "***";
        }

        return "***" + phone.substring(phone.length() - 4);
    }
}
