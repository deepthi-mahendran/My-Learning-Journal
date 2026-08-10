# Weather Application (Java Swing)

A modern, feature-rich desktop weather application built with Java Swing. This application provides real-time weather information and 5-day forecasts for any city worldwide, with a dynamic background that changes based on the time of day.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Setup and Configuration](#setup-and-configuration)
- [Compilation and Execution](#compilation-and-execution)
- [Usage Guide](#usage-guide)
- [Key Components](#key-components)
- [Customization](#customization)
- [Extending the System](#extending-the-system)
- [Troubleshooting](#troubleshooting)
- [License](#license)

---

## Overview

The **Weather Application** is a Java Swing desktop application that provides:

- **Current Weather**: Temperature, humidity, wind speed, and weather conditions for any city.
- **5-Day Forecast**: Daily weather predictions with icons and temperature information.
- **Search History**: Tracks recently searched cities for quick access.
- **Dynamic Background**: Automatically changes based on the time of day (morning, afternoon, evening, night).
- **Unit Conversion**: Toggle between Metric (°C, m/s) and Imperial (°F, mph) units.

The application demonstrates key Java concepts including **Swing GUI development**, **HTTP client integration**, **JSON parsing with Gson**, and **MVC architecture**.

---

## Features

| Feature                     | Description                                                                                      |
|-----------------------------|--------------------------------------------------------------------------------------------------|
| **Current Weather**         | Displays temperature, weather description, humidity, wind speed, and weather icon.               |
| **5-Day Forecast**          | Shows daily forecast with temperature and weather conditions.                                    |
| **Search History**          | Stores up to 10 recent searches; click to re-query.                                              |
| **Dynamic Background**      | Background color changes based on time of day (morning, afternoon, evening, night).              |
| **Unit Toggle**             | Switch between Metric (°C, m/s) and Imperial (°F, mph) units.                                   |
| **Weather Icons**           | Fetches weather condition icons from OpenWeatherMap.                                             |
| **Error Handling**          | User-friendly error messages for invalid city names or API issues.                               |
| **Status Updates**          | Real-time status bar feedback during API calls.                                                 |
| **Responsive Layout**       | Clean, organized GUI with side panel for history.                                               |

---

## Technology Stack

| Technology                | Purpose                                                         |
|---------------------------|-----------------------------------------------------------------|
| **Java SE**               | Core language and standard library.                             |
| **Java Swing**            | GUI framework for the desktop interface.                        |
| **Java HTTP Client**      | HTTP client for making API requests.                            |
| **Gson**                  | JSON serialization/deserialization library.                     |
| **OpenWeatherMap API**    | Weather data provider (free tier).                              |
| **Java Time API**         | Date/time handling for timestamps and formatting.               |

**External Dependencies:**
- `gson` — Google's JSON library (included via Maven/Gradle or manual download).

---

## Project Structure

The project follows a Model-View-Controller (MVC) architecture with a clean package structure:

```
src/
└── weather/
    ├── Main.java                       # Entry point
    ├── controller/
    │   └── WeatherController.java      # API calls & business logic
    ├── model/
    │   ├── WeatherData.java            # Current weather model
    │   └── ForecastData.java           # Forecast model
    ├── util/
    │   ├── ApiConfig.java              # API key configuration
    │   ├── BackgroundManager.java      # Dynamic background logic
    │   └── IconLoader.java             # Weather icon loading
    └── view/
        └── WeatherAppGUI.java          # Swing GUI implementation
```

### Package Breakdown

| Package                 | Classes                                     | Purpose                                                       |
|-------------------------|---------------------------------------------|---------------------------------------------------------------|
| `weather`               | `Main`                                      | Application entry point.                                      |
| `weather.controller`    | `WeatherController`                         | Handles API calls and data retrieval.                        |
| `weather.model`         | `WeatherData`, `ForecastData`               | Data models representing API responses.                      |
| `weather.util`          | `ApiConfig`, `BackgroundManager`, `IconLoader` | Utility classes for configuration, backgrounds, and icons.   |
| `weather.view`          | `WeatherAppGUI`                             | Swing GUI implementation.                                     |

---

## Requirements

- **Java** — JDK 11 or higher (uses Java HTTP Client and `var` keyword).
- **Gson Library** — For JSON parsing.
- **Internet Connection** — Required for API calls to OpenWeatherMap.
- **OpenWeatherMap API Key** — Included in the code (free tier).

### Dependencies

If using **Maven**, add to `pom.xml`:

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

If using **Gradle**, add to `build.gradle`:

```gradle
dependencies {
    implementation 'com.google.code.gson:gson:2.10.1'
}
```

If not using a build tool, download `gson-2.10.1.jar` and add it to your classpath.

---

## Setup and Configuration

### API Key Configuration

The application uses the OpenWeatherMap API. An API key should be configured in `ApiConfig.java`:

```java
private static final String API_KEY = "Your_API_Key";
```

To use your own API key:

1. Sign up for a free API key at [OpenWeatherMap](https://openweathermap.org/api).
2. Replace the `API_KEY` value in `weather/util/ApiConfig.java`.

### Dynamic Background

The `BackgroundManager` class determines the background color based on the current time of day:

| Time Range      | Color          | Period      |
|-----------------|----------------|-------------|
| 5:00 AM – 11:59 AM | `#FFD194` (Golden) | Morning     |
| 12:00 PM – 4:59 PM | `#87CEEB` (Sky Blue) | Afternoon   |
| 5:00 PM – 7:59 PM | `#FF8C00` (Orange) | Evening     |
| 8:00 PM – 4:59 AM | `#191970` (Midnight Blue) | Night |

---

## Compilation and Execution

### 1. Compile with Gson

Make sure `gson-2.10.1.jar` is in your classpath:

```bash
# Using javac
javac -cp ".;gson-2.10.1.jar" src/weather/**/*.java src/weather/*.java

# Or if using an IDE, add the Gson JAR to the project library.
```

### 2. Run the Application

```bash
java -cp ".;gson-2.10.1.jar" weather.Main
```

### 3. Alternative: Using Maven

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="weather.Main"
```

### 4. Using IDE (IntelliJ IDEA, Eclipse, VS Code)

1. Open the project folder.
2. Add Gson as a dependency.
3. Run `weather.Main`.

---

## Usage Guide

### Step 1: Launch the Application

The main window appears with:
- **Search bar** at the top.
- **Current weather panel** in the centre.
- **Forecast panel** below.
- **Search history** on the right side.
- **Status bar** at the bottom.

### Step 2: Search for a City

1. Enter a city name in the search field (e.g., "London", "Tokyo", "New York").
2. Press **Enter** or click the **🔍 Get Weather** button.

### Step 3: View Current Weather

The current weather panel displays:
- City name and country
- Temperature (in selected unit)
- Weather description
- Weather icon
- Humidity percentage
- Wind speed (in selected unit)

### Step 4: View 5-Day Forecast

The forecast panel shows:
- Daily weather for the next 5 days
- Weather icons
- Temperature (in selected unit)
- Weather description

### Step 5: Unit Conversion

Use the dropdown in the search bar to switch between:
- **Metric (°C, m/s)**
- **Imperial (°F, mph)**

### Step 6: Search History

- Recent searches appear in the right panel.
- Click a history entry to quickly re-search that city.
- Use **Clear History** to remove all entries.

### Step 7: Dynamic Background

The background color automatically changes based on the time of day. The status bar also shows the current time period (e.g., "Morning", "Afternoon").

---

## Key Components

### Main (`weather.Main`)

The entry point of the application. Uses `SwingUtilities.invokeLater()` to ensure thread safety:

```java
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        WeatherAppGUI app = new WeatherAppGUI();
        app.setVisible(true);
    });
}
```

### WeatherController (`weather.controller.WeatherController`)

Handles all API communication:

| Method                         | Purpose                                                       |
|--------------------------------|---------------------------------------------------------------|
| `getCurrentWeather(String)`    | Fetches current weather data for a city.                     |
| `getForecast(String)`          | Fetches 5-day forecast data for a city.                      |
| `makeApiCall(String)`          | Performs the HTTP request and returns the response body.     |

**Key Features:**
- URL encoding for city names.
- Error handling for API responses.
- Uses `HttpClient` for HTTP requests.

### WeatherData (`weather.model.WeatherData`)

Represents the current weather API response with nested classes:

| Class                  | Fields                                                         |
|------------------------|----------------------------------------------------------------|
| `MainWeather`          | Temperature, feels-like, humidity, pressure.                   |
| `WeatherCondition`     | Weather ID, main condition, description, icon code.            |
| `Wind`                 | Speed and degree.                                              |
| `Sys`                  | Country code, sunrise, sunset.                                |

### ForecastData (`weather.model.ForecastData`)

Represents the 5-day forecast API response:

| Class                  | Purpose                                                       |
|------------------------|---------------------------------------------------------------|
| `City`                 | City name and country.                                        |
| `ForecastItem`         | Individual forecast entry with timestamp, main, weather, wind. |
| `getDailyForecast()`   | Returns one forecast per day (at 12:00) or first 5 entries.   |

### IconLoader (`weather.util.IconLoader`)

Loads weather icons from OpenWeatherMap:

```java
public static ImageIcon getIcon(String iconCode, int width, int height) {
    // Downloads icon from openweathermap.org
    // Falls back to a placeholder if download fails
}
```

### BackgroundManager (`weather.util.BackgroundManager`)

Determines the background color and time period:

```java
public static Color getBackgroundColor() {
    int hour = LocalTime.now().getHour();
    // Returns appropriate color based on time of day
}
```

---

## Customization

### Changing the API Key

Edit `weather/util/ApiConfig.java`:

```java
private static final String API_KEY = "YOUR_API_KEY_HERE";
```

### Modifying the Background Colors

Edit the `getBackgroundColor()` method in `BackgroundManager.java`:

```java
if (hour >= 5 && hour < 12) {
    return new Color(255, 209, 148);  // Custom morning colour
}
```

### Adjusting the Forecast Limit

Modify the `getDailyForecast()` method in `ForecastData.java`:

```java
return list.stream()
    .filter(item -> item.getDateTimeText().contains("12:00:00"))
    .limit(7)  // Change from 5 to 7 for a 7-day forecast
    .collect(Collectors.toList());
```

### Changing the Search History Limit

Edit the `addToHistory()` method in `WeatherAppGUI.java`:

```java
while (searchHistory.size() > 20) {  // Change from 10 to 20
    searchHistory.remove(searchHistory.size() - 1);
}
```

### Adding More Weather Metrics

Add fields to `WeatherData.java` and update the GUI to display them:

```java
// In WeatherData.java
@SerializedName("visibility") private int visibility;
public int getVisibility() { return visibility; }

// In WeatherAppGUI.java
visibilityLabel = new JLabel("👁️ Visibility: -- m");
```

---

## Extending the System

### Add a Map View

- Integrate a Java mapping library like `JXMapViewer` or `OpenStreetMap`.
- Display the city location on a map when weather data is fetched.

### Add Geolocation

- Use the Java `Geolocation` API or a third-party library to auto-detect the user's location.
- Automatically fetch weather for the current location on startup.

### Add Air Quality Data

- Integrate OpenWeatherMap's Air Pollution API.
- Display AQI (Air Quality Index) alongside weather data.

### Add Multiple Cities

- Allow users to save and manage multiple favourite cities.
- Display weather for all saved cities in a list.

### Export Weather Data

- Add functionality to export current weather and forecast data as CSV or JSON.
- Use `java.io.FileWriter` and `PrintWriter`.

### Add Notifications

- Use `java.awt.SystemTray` to display desktop notifications.
- Show alerts for severe weather conditions.

### Add Voice Search

- Integrate a speech recognition library like `CMU Sphinx`.
- Allow voice commands for searching weather.

---

## Troubleshooting

| Issue                        | Solution                                                                 |
|------------------------------|--------------------------------------------------------------------------|
| **"City not found" error**   | Check spelling. Use the full city name (e.g., "New York" instead of "NY"). |
| **"API key not valid" error** | Verify your API key in `ApiConfig.java`. Ensure it's active.            |
| **No internet connection**   | Check your network connection. The app requires internet for API calls.  |
| **Gson not found**           | Add `gson-2.10.1.jar` to your classpath.                                |
| **Forecast not showing**     | Ensure the city is valid. Some cities may not have forecast data.        |
| **Icons not loading**        | Check internet connection. Icons are fetched from OpenWeatherMap.        |
| **UI freezing**              | The app uses `SwingWorker` for background tasks. If freezing occurs, check for UI updates on the EDT. |
| **Dark background**          | The background is dynamic based on time of day. It's not a bug.          |

---

## API Credits

This application uses the **OpenWeatherMap API**:
- **Current Weather**: [OpenWeatherMap Current Weather API](https://openweathermap.org/current)
- **5-Day Forecast**: [OpenWeatherMap 5-Day Forecast API](https://openweathermap.org/forecast5)

OpenWeatherMap provides free tier access with limited requests per day.

---

## License

This project is created for **educational purposes** — to demonstrate Java GUI development, API integration, JSON parsing, and MVC architecture. You are free to use, modify, and distribute this code for learning.

---

**Author:** Student Developer  
**Version:** 1.0  
**Date:** June 2026
