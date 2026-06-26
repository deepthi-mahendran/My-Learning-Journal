# Weather App – Modern Desktop Weather Application

A sleek, feature-rich desktop weather application built with Python and **CustomTkinter**. The app allows users to search for any city worldwide and displays current weather conditions, detailed metrics, and a beautiful user interface that adapts to your system's appearance mode.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Requirements](#requirements)
- [Installation](#installation)
- [Running the App](#running-the-app)
- [Usage Guide](#usage-guide)
- [How It Works](#how-it-works)
  - [Geocoding](#geocoding)
  - [Weather Data Fetching](#weather-data-fetching)
  - [WMO Code Mapping](#wmo-code-mapping)
- [Key Components](#key-components)
- [Customization](#customization)
- [Extending the Application](#extending-the-application)
- [License](#license)

---

## Overview

**Weather App** is a modern desktop application that provides real-time weather information for any city in the world. It uses the **Open-Meteo API** for free, no‑key weather data and geocoding services. The app displays:

- Current temperature, "feels like" temperature, and humidity
- Wind speed, direction, and gusts
- Precipitation, rain, showers, and snowfall
- Cloud cover and atmospheric pressure
- Weather condition with descriptive text and emoji
- Day/night indicator and last updated timestamp

The interface is built with `customtkinter`, a modern library that extends Tkinter with stylish widgets and automatic dark/light mode support.

---

## Features

| Feature                     | Description                                                                                      |
|-----------------------------|--------------------------------------------------------------------------------------------------|
| **City Search**             | Enter any city name – the app geocodes it and fetches current weather.                          |
| **Real-Time Weather**       | Displays current conditions from the Open-Meteo API.                                            |
| **Comprehensive Metrics**   | Temperature, humidity, wind, precipitation, pressure, cloud cover, and more.                    |
| **Weather Condition Emojis**| Each weather condition is paired with a descriptive emoji for quick visual recognition.         |
| **Dark / Light Mode**       | Automatically follows your system appearance (can be overridden in code).                       |
| **Responsive Layout**       | Window resizes gracefully with a minimum size of 500×600.                                       |
| **Keyboard Shortcut**       | Press `Enter` in the search field to fetch weather.                                             |
| **Error Handling**          | Clear error messages for invalid city names, network issues, or API errors.                     |
| **User-Friendly Interface** | Clean, card‑based layout with well‑organized metrics in a grid.                                 |
| **No API Key Required**     | Uses free Open-Meteo APIs – no registration or key needed.                                      |

---

## Technology Stack

- **Python 3.7+** — Core language
- **CustomTkinter** — Modern GUI library (extends Tkinter)
- **Requests** — HTTP client for API calls
- **JSON** — Built‑in module for parsing API responses
- **Datetime** — Built‑in module for timestamp formatting

**External Libraries:**
- `customtkinter` – install via pip
- `requests` – install via pip

---

## Requirements

- **Python 3.7+**
- **customtkinter** library
- **requests** library

> These are not included in the standard Python distribution – install them using pip.

---

## Installation

### 1. Install Required Libraries

```bash
pip install customtkinter requests
```

### 2. Download the File

Save `Weather App.py` to your computer.

### 3. (Optional) Verify Installation

```bash
python -c "import customtkinter; import requests; print('All good!')"
```

---

## Running the App

Open a terminal/command prompt in the folder containing `Weather App.py` and run:

```bash
python "Weather App.py"
```

> **Note:** The filename contains a space. Use quotes or rename the file to avoid issues.

---

## Usage Guide

### Step 1: Search for a City

- Type a city name in the search bar (e.g., "London", "Tokyo", "New York").
- Click the **🔍 Search** button or press **Enter**.

### Step 2: View Weather Data

The app displays a weather card with:

- **Location** – City, country, and state/province (if available)
- **Weather Condition** – Description with a large emoji
- **Current Temperature** – In °C or °F (API returns °C by default)
- **Detailed Metrics** – Organized in a 3‑column grid:
  - Feels like, Humidity, Wind speed
  - Wind direction, Gusts, Cloud cover
  - Precipitation, Rain, Showers
  - Snowfall, Pressure (MSL), Surface pressure
- **Footer** – Last updated timestamp and day/night indicator

### Step 3: Try Another City

Simply enter a new city name and search again. The previous weather card is cleared and replaced with the new data.

---

## How It Works

### Geocoding

The app uses the **Open-Meteo Geocoding API** to convert a city name into latitude and longitude coordinates:

```
https://geocoding-api.open-meteo.com/v1/search?name=London&count=1&language=en&format=json
```

- Returns the first matching location with name, country, state, latitude, and longitude.

### Weather Data Fetching

The app uses the **Open-Meteo Forecast API** to fetch current weather:

```
https://api.open-meteo.com/v1/forecast?latitude=51.51&longitude=-0.13&current=...
```

- Requests 16 different current weather variables.
- Returns data in JSON format with units.

### WMO Code Mapping

The World Meteorological Organization (WMO) codes are mapped to human‑readable descriptions and emojis:

```python
WMO_CODES = {
    0: ("Clear sky", "☀️"),
    1: ("Mainly clear", "🌤"),
    2: ("Partly cloudy", "⛅"),
    3: ("Overcast", "☁️"),
    # ...
}
```

This makes the weather condition instantly recognizable.

---

## Key Components

| Component                | Description                                                                 |
|--------------------------|-----------------------------------------------------------------------------|
| `WeatherApp` class       | Main application class extending `ctk.CTk`.                                 |
| `fetch_weather()`        | Orchestrates geocoding, API calls, and UI updates.                         |
| `build_result_card()`    | Creates the detailed weather card with all metrics.                        |
| `show_error()` / `clear_error()` | Display and clear error messages.                                      |
| `clear_result_card()`    | Removes the current weather card from view.                                |
| `get_weather_data()`     | Helper function to map WMO codes to descriptions and emojis.               |
| `GEOCODING_URL`          | Open-Meteo geocoding API endpoint.                                          |
| `FORECAST_URL`           | Open-Meteo forecast API endpoint.                                           |

### Metrics Displayed

| Metric                   | Unit       | Description                                                       |
|--------------------------|------------|-------------------------------------------------------------------|
| Temperature 2m           | °C / °F    | Current air temperature at 2 meters above ground.                 |
| Apparent Temperature     | °C / °F    | "Feels like" temperature (wind chill / heat index).              |
| Relative Humidity        | %          | Percentage of moisture in the air.                                |
| Wind Speed 10m           | km/h       | Wind speed at 10 meters above ground.                             |
| Wind Direction 10m       | degrees    | Wind direction in degrees (0 = North, 90 = East).                |
| Wind Gusts 10m           | km/h       | Maximum wind gust speed.                                          |
| Cloud Cover              | %          | Percentage of sky covered by clouds.                              |
| Precipitation            | mm         | Total precipitation (rain + snow).                                |
| Rain                     | mm         | Rainfall amount.                                                  |
| Showers                  | mm         | Shower precipitation.                                             |
| Snowfall                 | cm         | Snowfall amount.                                                  |
| Pressure (MSL)           | hPa        | Mean sea level pressure.                                          |
| Surface Pressure         | hPa        | Pressure at ground level.                                         |
| Weather Code             | -          | WMO code mapped to description and emoji.                        |
| Is Day                   | boolean    | 1 = Day, 0 = Night.                                               |

---
## Screenshot
<img width="720" height="788" alt="image" src="https://github.com/user-attachments/assets/b12debd0-9c2f-46ea-8b34-2f7ac091b4c8" />

## Customization

### Change Appearance Mode

At the top of the file:

```python
ctk.set_appearance_mode("System")   # Options: "Light", "Dark", "System"
ctk.set_default_color_theme("blue") # Options: "blue", "green", "dark-blue"
```

### Change Temperature Unit

The API returns Celsius by default. To display Fahrenheit, modify the `params` dictionary:

```python
params = {
    "latitude": lat,
    "longitude": lon,
    "current": ",".join(current_vars),
    "timezone": "auto",
    "forecast_days": 1,
    "temperature_unit": "fahrenheit"   # Add this line
}
```

### Add More Weather Variables

Edit the `current_vars` list to include additional fields (see [Open-Meteo API docs](https://open-meteo.com/en/docs)):

```python
current_vars = [
    "temperature_2m",
    "relative_humidity_2m",
    # add more variables here
]
```

### Change Window Size

Modify the `self.geometry("600x650")` line in `__init__`.

---

## Extending the Application

The modular design makes it easy to add new features:

### Add 7-Day Forecast

- Add a new tab or section to display daily forecasts.
- Use the `daily` parameter in the API request.
- Parse and display min/max temperatures, weather codes, etc.

### Add Favorites / History

- Store recently searched cities in a list or `localStorage` equivalent.
- Display a dropdown or sidebar with saved cities.

### Add Unit Toggle

- Add a button to switch between °C and °F.
- Store the preference and adjust displayed values accordingly.

### Add Weather Alerts

- Check for severe weather warnings from a separate API.
- Display alerts prominently in the UI.

### Add Map Integration

- Embed a static map showing the city's location.
- Use OpenStreetMap or Google Maps Static API.

### Export Weather Data

- Add a button to save the current weather data as a JSON or CSV file.

---

## Error Handling

The app handles common errors gracefully:

| Scenario                                   | Response                                                       |
|--------------------------------------------|----------------------------------------------------------------|
| Empty search field                         | "Please enter a city name."                                    |
| City not found                             | "City 'XYZ' not found. Please check spelling."                 |
| Network / API error                        | "Geocoding error: [details]" or "Weather fetch error: [...]"   |
| Missing data fields                        | Displays "N/A" for missing values.                             |

All errors are displayed in red below the search bar, and the weather card is cleared.

---

## API Credits

This application uses the **Open-Meteo API**:
- **Geocoding**: https://open-meteo.com/en/docs/geocoding-api
- **Weather Forecast**: https://open-meteo.com/en/docs

Open-Meteo provides free, open‑source weather data with no API key required.
