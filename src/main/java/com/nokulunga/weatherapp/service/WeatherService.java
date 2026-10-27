
package com.nokulunga.weatherapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nokulunga.weatherapp.model.WeatherResponse;
import com.nokulunga.weatherapp.model.WeatherResponse.DailyForecast;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Service
public class WeatherService {

    private final RestClient restClient;

    public WeatherService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public WeatherResponse getWeather(String city) {
        if (city == null || city.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please enter a city name."
            );
        }

        String cityName = city.trim();

        JsonNode geocoding = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("geocoding-api.open-meteo.com")
                        .path("/v1/search")
                        .queryParam("name", cityName)
                        .queryParam("count", 1)
                        .queryParam("language", "en")
                        .queryParam("format", "json")
                        .build())
                .retrieve()
                .body(JsonNode.class);

        JsonNode results = geocoding == null
                ? null
                : geocoding.path("results");

        if (results == null || !results.isArray()
                || results.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "City not found. Please try another city."
            );
        }

        JsonNode location = results.get(0);

        double latitude = location.path("latitude").asDouble();
        double longitude = location.path("longitude").asDouble();

        JsonNode weather = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.open-meteo.com")
                        .path("/v1/forecast")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam(
                                "current",
                                "temperature_2m,weather_code,wind_speed_10m"
                        )
                        .queryParam(
                                "daily",
                                "weather_code,temperature_2m_max,"
                                        + "temperature_2m_min,"
                                        + "precipitation_probability_max"
                        )
                        .queryParam("forecast_days", 5)
                        .queryParam("timezone", "auto")
                        .queryParam("temperature_unit", "celsius")
                        .queryParam("wind_speed_unit", "kmh")
                        .build())
                .retrieve()
                .body(JsonNode.class);

        if (weather == null || weather.path("current").isMissingNode()
                || weather.path("daily").isMissingNode()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Weather data could not be retrieved."
            );
        }

        JsonNode current = weather.path("current");
        JsonNode daily = weather.path("daily");

        WeatherResponse response = new WeatherResponse();

        response.setCity(location.path("name").asText(cityName));
        response.setCountry(location.path("country").asText(""));
        response.setCurrentTemperature(
                current.path("temperature_2m").asDouble()
        );
        response.setCurrentCondition(
                describeWeatherCode(
                        current.path("weather_code").asInt()
                )
        );
        response.setWindSpeed(
                current.path("wind_speed_10m").asDouble()
        );

        JsonNode dates = daily.path("time");
        JsonNode codes = daily.path("weather_code");
        JsonNode maxTemps = daily.path("temperature_2m_max");
        JsonNode minTemps = daily.path("temperature_2m_min");
        JsonNode rainProbabilities =
                daily.path("precipitation_probability_max");

        for (int i = 0; i < dates.size(); i++) {
            DailyForecast day = new DailyForecast();

            day.setDate(dates.get(i).asText());
            day.setCondition(
                    describeWeatherCode(codes.get(i).asInt())
            );
            day.setMaxTemperature(maxTemps.get(i).asDouble());
            day.setMinTemperature(minTemps.get(i).asDouble());

            int probability = i < rainProbabilities.size()
                    ? rainProbabilities.get(i).asInt()
                    : 0;

            day.setPrecipitationProbability(probability);
            response.getForecast().add(day);
        }

        return response;
    }

    public static String describeWeatherCode(int code) {
        return switch (code) {
            case 0 -> "Clear sky";
            case 1 -> "Mainly clear";
            case 2 -> "Partly cloudy";
            case 3 -> "Overcast";
            case 45, 48 -> "Fog";
            case 51, 53, 55 -> "Drizzle";
            case 56, 57 -> "Freezing drizzle";
            case 61, 63, 65 -> "Rain";
            case 66, 67 -> "Freezing rain";
            case 71, 73, 75, 77 -> "Snow";
            case 80, 81, 82 -> "Rain showers";
            case 85, 86 -> "Snow showers";
            case 95 -> "Thunderstorm";
            case 96, 99 -> "Thunderstorm with hail";
            default -> "Conditions unavailable";
        };
    }

    public static String normalizeCityName(String city) {
        if (city == null) {
            return "";
        }

        return city.trim().toLowerCase(Locale.ROOT);
    }
}
