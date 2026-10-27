
package com.nokulunga.weatherapp.model;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {

    private String city;
    private String country;
    private double currentTemperature;
    private String currentCondition;
    private double windSpeed;
    private List<DailyForecast> forecast = new ArrayList<>();

    public WeatherResponse() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public List<DailyForecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<DailyForecast> forecast) {
        this.forecast = forecast;
    }

    public static class DailyForecast {

        private String date;
        private String condition;
        private double minTemperature;
        private double maxTemperature;
        private int precipitationProbability;

        public DailyForecast() {
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public double getMinTemperature() {
            return minTemperature;
        }

        public void setMinTemperature(double minTemperature) {
            this.minTemperature = minTemperature;
        }

        public double getMaxTemperature() {
            return maxTemperature;
        }

        public void setMaxTemperature(double maxTemperature) {
            this.maxTemperature = maxTemperature;
        }

        public int getPrecipitationProbability() {
            return precipitationProbability;
        }

        public void setPrecipitationProbability(
                int precipitationProbability) {
            this.precipitationProbability = precipitationProbability;
        }
    }
}
