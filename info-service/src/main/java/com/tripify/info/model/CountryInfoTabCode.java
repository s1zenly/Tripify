package com.tripify.info.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CountryInfoTabCode {
    OVERVIEW("overview"),
    VISA("visa"),
    WHEN_TO_GO("when_to_go"),
    CURRENCY("currency"),
    PRICES("prices"),
    HOW_TO_GET("how_to_get"),
    WEATHER("weather");

    private final String code;

    CountryInfoTabCode(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String title() {
        return switch (this) {
            case OVERVIEW -> "Основное про страну";
            case VISA -> "Виза и въезд";
            case WHEN_TO_GO -> "Когда ехать";
            case CURRENCY -> "Валюта";
            case PRICES -> "Цены";
            case HOW_TO_GET -> "Как добраться";
            case WEATHER -> "Погода";
        };
    }

    public boolean scrapedFromSource() {
        return this != WEATHER;
    }
}
