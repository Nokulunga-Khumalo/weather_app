
package com.nokulunga.weatherapp.controller;

import com.nokulunga.weatherapp.model.WeatherResponse;
import com.nokulunga.weatherapp.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    void shouldReturnWeatherForCity() throws Exception {
        WeatherResponse response = new WeatherResponse();
        response.setCity("Johannesburg");
        response.setCountry("South Africa");
        response.setCurrentTemperature(24.5);
        response.setCurrentCondition("Clear sky");
        response.setWindSpeed(12.0);

        when(weatherService.getWeather("Johannesburg"))
                .thenReturn(response);

        mockMvc.perform(get("/api/weather")
                        .param("city", "Johannesburg")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Johannesburg"))
                .andExpect(jsonPath("$.country").value("South Africa"))
                .andExpect(jsonPath("$.currentTemperature").value(24.5))
                .andExpect(jsonPath("$.currentCondition").value("Clear sky"))
                .andExpect(jsonPath("$.windSpeed").value(12.0));

        verify(weatherService).getWeather("Johannesburg");
    }

    @Test
    void shouldReturnNotFoundWhenCityDoesNotExist() throws Exception {
        when(weatherService.getWeather("UnknownCity"))
                .thenThrow(new ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "City not found."
                ));

        mockMvc.perform(get("/api/weather")
                        .param("city", "UnknownCity"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("City not found."));
    }
}
