package com.tripify.info.weather;

public final class WmoWeatherCodeMapper {

    private WmoWeatherCodeMapper() {
    }

    public static WeatherCondition fromWmoCode(int code) {
        return switch (code) {
            case 0 -> WeatherCondition.CLEAR;
            case 1, 2 -> WeatherCondition.PARTLY_CLOUDY;
            case 3 -> WeatherCondition.CLOUDY;
            case 45, 48 -> WeatherCondition.FOG;
            case 51, 53, 55, 56, 57 -> WeatherCondition.DRIZZLE;
            case 61, 63, 65, 66, 67 -> WeatherCondition.RAIN;
            case 71, 73, 75, 77, 85, 86 -> WeatherCondition.SNOW;
            case 80, 81, 82 -> WeatherCondition.SHOWERS;
            case 95, 96, 99 -> WeatherCondition.THUNDERSTORM;
            default -> WeatherCondition.CLOUDY;
        };
    }
}
