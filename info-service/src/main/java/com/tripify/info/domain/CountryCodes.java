package com.tripify.info.domain;

public final class CountryCodes {

    private CountryCodes() {
    }

    public static String normalize(String countryCode) {
        return Country.fromAlpha2(countryCode).alpha2();
    }
}
