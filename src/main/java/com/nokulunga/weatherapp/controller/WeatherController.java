package com.nokulunga.weatherapp.controller;

import com.nokulunga.weatherapp.model.WeatherResponse;
import com.nokulunga.weatherapp.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public WeatherResponse getWeather(
            @RequestParam(name = "city") String city) {
        return weatherService.getWeather(city);
    }
}
