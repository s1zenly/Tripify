package com.tripify.hotels.parser.models;

import lombok.Getter;

@Getter
public enum Provider {

    MOCK("MOCK");

    private final String providerName;

    Provider(String providerName) {
        this.providerName = providerName;
    }
}
