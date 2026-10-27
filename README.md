
# Weather App

A Java Spring Boot weather application designed to provide current
weather conditions and multi-day forecasts through a responsive
web interface.

## Overview

This project is being developed as a portfolio project to demonstrate
Java development, Spring Boot application structure, REST API design,
automated testing, and front-end integration.

## Planned Features

- Search for weather by city.
- Display the current temperature in degrees Celsius.
- Show weather conditions and wind speed.
- Present a 5–7 day forecast.
- Provide a responsive interface for desktop and mobile devices.
- Handle invalid requests and errors gracefully.
- Include automated tests for key application behaviour.

## Technology Stack

- Java 17
- Spring Boot 3.5.6
- Maven
- Spring Web
- JUnit and Spring Boot Test
- HTML, CSS and JavaScript

## Project Structure

```text
src/
├── main/
│   ├── java/com/nokulunga/weatherapp/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── model/
│   │   ├── exception/
│   │   └── WeatherAppApplication.java
│   └── resources/
│       ├── application.properties
│       └── static/
│           ├── index.html
│           ├── css/style.css
│           └── js/app.js
└── test/
    └── java/com/nokulunga/weatherapp/
```

## Prerequisites

- JDK 17
- Apache Maven 3.9.6 or a compatible Maven installation
- An IDE such as IntelliJ IDEA

An external weather API and API key may be required for live
weather data, depending on the chosen provider and implementation.

## Running the Application

Clone the repository:

```bash
git clone <your-repository-url>
cd weather-app
```

Build and run the tests:

```bash
mvn clean verify
```

Start the application:

```bash
mvn spring-boot:run
```

Open the application at:

http://localhost:8081

The URL will serve the interface once the static page and application
routing are implemented.

## Testing

Run the automated test suite:

```bash
mvn test
```

Package the application:

```bash
mvn clean package
```

## Configuration and Security

Keep API keys and other credentials outside version control.
Do not commit `.env` files, passwords, or private API keys.
Use environment variables or an appropriate local configuration
mechanism for external service credentials.

## Development Status

In development. Features should be marked complete only after their
implementation and tests have been verified.

## Future Improvements

- Integrate a live weather data provider.
- Improve validation and error handling.
- Expand unit and integration test coverage.
- Deploy the application and document the deployment process.

## Author

Nokulunga Thapelo Khumalo


