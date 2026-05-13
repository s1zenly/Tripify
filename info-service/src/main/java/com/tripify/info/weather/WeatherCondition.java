package com.tripify.info.weather;

public enum WeatherCondition {
    CLEAR,
    PARTLY_CLOUDY,
    CLOUDY,
    FOG,
    DRIZZLE,
    RAIN,
    SHOWERS,
    SNOW,
    THUNDERSTORM;

    public String apiValue() {
        return name().toLowerCase();
    }
}
