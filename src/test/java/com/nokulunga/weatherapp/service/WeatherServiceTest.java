
package com.nokulunga.weatherapp.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherServiceTest {

    @Test
    void shouldDescribeClearSky() {
        assertEquals(
                "Clear sky",
                WeatherService.describeWeatherCode(0)
        );
    }

    @Test
    void shouldDescribeRain() {
        assertEquals(
                "Rain",
                WeatherService.describeWeatherCode(63)
        );
    }

    @Test
    void shouldDescribeThunderstorm() {
        assertEquals(
                "Thunderstorm",
                WeatherService.describeWeatherCode(95)
        );
    }

    @Test
    void shouldHandleUnknownWeatherCode() {
        assertEquals(
                "Conditions unavailable",
                WeatherService.describeWeatherCode(-1)
        );
    }

    @Test
    void shouldNormaliseCityName() {
        assertEquals(
                "johannesburg",
                WeatherService.normalizeCityName("  Johannesburg  ")
        );
    }

    @Test
    void shouldHandleNullCityName() {
        assertEquals("", WeatherService.normalizeCityName(null));
    }
}
