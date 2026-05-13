package com.tripify.info.exception;

public class CountryInfoNotFoundException extends InfoServiceException {

    public CountryInfoNotFoundException(String countryId) {
        super(
                InfoErrorCode.COUNTRY_INFO_NOT_FOUND,
                "Country info not found for country_id=" + countryId
        );
    }
}
