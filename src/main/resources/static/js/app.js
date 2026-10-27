"use strict";

const searchForm = document.getElementById("search-form");
const cityInput = document.getElementById("city-input");
const searchButton = document.getElementById("search-button");
const message = document.getElementById("message");
const weatherContent = document.getElementById("weather-content");
const forecastGrid = document.getElementById("forecast-grid");

function getWeatherIcon(condition) {
    const text = (condition || "").toLowerCase();

    if (text.includes("thunderstorm")) return "⛈️";
    if (text.includes("snow")) return "❄️";
    if (text.includes("rain") || text.includes("drizzle")) return "🌧️";
    if (text.includes("fog")) return "🌫️";
    if (text.includes("overcast")) return "☁️";
    if (text.includes("cloud")) return "⛅";
    if (text.includes("clear")) return "☀️";

    return "🌤️";
}

function formatDay(dateString) {
    const date = new Date(`${dateString}T12:00:00`);

    if (Number.isNaN(date.getTime())) {
        return dateString;
    }

    return new Intl.DateTimeFormat("en-ZA", {
        weekday: "short",
        day: "numeric"
    }).format(date);
}

function createTextElement(tag, className, text) {
    const element = document.createElement(tag);
    element.className = className;
    element.textContent = text;
    return element;
}

function showMessage(text, isError = false) {
    message.textContent = text;
    message.classList.toggle("error", isError);
}

function renderForecast(forecast) {
    forecastGrid.replaceChildren();

    forecast.forEach((day) => {
        const card = document.createElement("article");
        card.className = "forecast-card";

        card.appendChild(
            createTextElement("p", "forecast-day", formatDay(day.date))
        );

        card.appendChild(
            createTextElement(
                "p",
                "forecast-icon",
                getWeatherIcon(day.condition)
            )
        );

        card.appendChild(
            createTextElement("p", "forecast-condition", day.condition)
        );

        const temperatures = document.createElement("p");
        temperatures.className = "forecast-temps";

        temperatures.appendChild(
            document.createTextNode(
                `${Math.round(day.maxTemperature)}° `
            )
        );

        temperatures.appendChild(
            createTextElement(
                "span",
                "forecast-min",
                `${Math.round(day.minTemperature)}°`
            )
        );

        card.appendChild(temperatures);

        card.appendChild(
            createTextElement(
                "p",
                "forecast-rain",
                `Rain ${day.precipitationProbability}%`
            )
        );

        forecastGrid.appendChild(card);
    });
}

function renderWeather(data) {
    document.getElementById("city-name").textContent = data.city;
    document.getElementById("country-name").textContent = data.country
        ? data.country
        : "";

    document.getElementById("current-temperature").textContent =
        `${Math.round(data.currentTemperature)}°`;

    document.getElementById("current-condition").textContent =
        data.currentCondition;

    document.getElementById("current-icon").textContent =
        getWeatherIcon(data.currentCondition);

    document.getElementById("wind-speed").textContent =
        `${Math.round(data.windSpeed)} km/h`;

    const forecast = Array.isArray(data.forecast) ? data.forecast : [];

    document.getElementById("rain-chance").textContent =
        `${forecast.length ? forecast[0].precipitationProbability : 0}%`;

    renderForecast(forecast);
    weatherContent.hidden = false;
}

searchForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const city = cityInput.value.trim();

    if (!city) {
        showMessage("Please enter a city name.", true);
        return;
    }

    searchButton.disabled = true;
    searchButton.textContent = "Searching...";
    weatherContent.hidden = true;
    showMessage(`Getting weather for ${city}...`);

    try {
        const response = await fetch(
            `/api/weather?city=${encodeURIComponent(city)}`
        );

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.error || "Unable to retrieve weather.");
        }

        renderWeather(data);
        showMessage(`Weather updated for ${data.city}.`);
    } catch (error) {
        showMessage(
            error instanceof TypeError
                ? "Could not connect to the server. Check that the app is running."
                : error.message,
            true
        );
    } finally {
        searchButton.disabled = false;
        searchButton.textContent = "Search";
    }
});
