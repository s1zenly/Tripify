package com.tripify.info.weather;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class WmoWeatherCodeMapperTest {

    @Test
    void mapsCommonWmoCodes() {
        assertThat(WmoWeatherCodeMapper.fromWmoCode(0)).isEqualTo(WeatherCondition.CLEAR);
        assertThat(WmoWeatherCodeMapper.fromWmoCode(3)).isEqualTo(WeatherCondition.CLOUDY);
        assertThat(WmoWeatherCodeMapper.fromWmoCode(61)).isEqualTo(WeatherCondition.RAIN);
        assertThat(WmoWeatherCodeMapper.fromWmoCode(95)).isEqualTo(WeatherCondition.THUNDERSTORM);
    }
}
