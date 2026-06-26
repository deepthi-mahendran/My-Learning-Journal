import customtkinter as ctk
import requests
import json
from datetime import datetime

# --- CustomTkinter appearance settings ---
ctk.set_appearance_mode("System")   # "Light", "Dark", "System"
ctk.set_default_color_theme("blue")

# --- API endpoints ---
GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search"
FORECAST_URL = "https://api.open-meteo.com/v1/forecast"

# --- WMO code mapping with emojis ---
WMO_CODES = {
    0: ("Clear sky", "☀️"),
    1: ("Mainly clear", "🌤"),
    2: ("Partly cloudy", "⛅"),
    3: ("Overcast", "☁️"),
    45: ("Fog", "🌫"),
    48: ("Depositing rime fog", "🌫"),
    51: ("Light drizzle", "🌦"),
    53: ("Moderate drizzle", "🌦"),
    55: ("Dense drizzle", "🌧"),
    56: ("Light freezing drizzle", "🌧"),
    57: ("Dense freezing drizzle", "🌧"),
    61: ("Slight rain", "🌧"),
    63: ("Moderate rain", "🌧"),
    65: ("Heavy rain", "🌧"),
    66: ("Light freezing rain", "🌧"),
    67: ("Heavy freezing rain", "🌧"),
    71: ("Slight snow fall", "🌨"),
    73: ("Moderate snow fall", "🌨"),
    75: ("Heavy snow fall", "❄️"),
    77: ("Snow grains", "🌨"),
    80: ("Slight rain showers", "🌦"),
    81: ("Moderate rain showers", "🌦"),
    82: ("Violent rain showers", "⛈"),
    85: ("Slight snow showers", "🌨"),
    86: ("Heavy snow showers", "❄️"),
    95: ("Thunderstorm", "⛈"),
    96: ("Thunderstorm with slight hail", "⛈"),
    99: ("Thunderstorm with heavy hail", "⛈"),
}

def get_weather_data(code):
    """Return description and emoji for a WMO code."""
    return WMO_CODES.get(code, ("Unknown", "❓"))

class WeatherApp(ctk.CTk):
    def __init__(self):
        super().__init__()

        self.title("Weather App")
        self.geometry("600x650")
        self.minsize(500, 600)
        self.resizable(True, True)

        # --- Main container with padding ---
        self.main_frame = ctk.CTkFrame(self, fg_color="transparent")
        self.main_frame.pack(fill="both", expand=True, padx=20, pady=20)

        # --- Header ---
        self.header_label = ctk.CTkLabel(
            self.main_frame,
            text="🌤 Weather",
            font=ctk.CTkFont(size=34, weight="bold")
        )
        self.header_label.pack(pady=(0, 15))

        # --- Search frame (input + button) ---
        self.search_frame = ctk.CTkFrame(self.main_frame, fg_color="transparent")
        self.search_frame.pack(fill="x", pady=(0, 20))

        self.city_entry = ctk.CTkEntry(
            self.search_frame,
            placeholder_text="Enter city name...",
            width=400,
            height=45,
            font=ctk.CTkFont(size=16),
            corner_radius=25,
            border_width=2
        )
        self.city_entry.pack(side="left", fill="x", expand=True, padx=(0, 10))

        self.get_btn = ctk.CTkButton(
            self.search_frame,
            text="🔍 Search",
            command=self.fetch_weather,
            width=100,
            height=45,
            corner_radius=25,
            font=ctk.CTkFont(size=16, weight="bold")
        )
        self.get_btn.pack(side="right")

        # Bind Enter key
        self.city_entry.bind("<Return>", lambda event: self.fetch_weather())

        # --- Weather result card (initially hidden) ---
        self.result_card = ctk.CTkFrame(
            self.main_frame,
            corner_radius=20,
            border_width=2,
            border_color=("gray70", "gray30")
        )
        # We'll pack it when data arrives

        # --- Error label (for messages) ---
        self.error_label = ctk.CTkLabel(
            self.main_frame,
            text="",
            font=ctk.CTkFont(size=14),
            text_color="red"
        )
        self.error_label.pack(pady=(5, 0))

    def fetch_weather(self):
        """Geocode city, fetch current weather, and update the UI."""
        city = self.city_entry.get().strip()
        if not city:
            self.show_error("Please enter a city name.")
            return

        # Hide previous result and clear error
        self.clear_error()
        self.clear_result_card()

        # --- Geocoding ---
        try:
            geo_params = {"name": city, "count": 1, "language": "en", "format": "json"}
            geo_resp = requests.get(GEOCODING_URL, params=geo_params, timeout=10)
            geo_resp.raise_for_status()
            geo_data = geo_resp.json()

            if not geo_data.get("results"):
                self.show_error(f"City '{city}' not found. Please check spelling.")
                return

            loc = geo_data["results"][0]
            lat, lon = loc["latitude"], loc["longitude"]
            city_name = loc.get("name", city)
            country = loc.get("country", "")
            state = loc.get("admin1", "")

        except Exception as e:
            self.show_error(f"Geocoding error: {str(e)}")
            return

        # --- Weather forecast ---
        current_vars = [
            "temperature_2m", "relative_humidity_2m", "apparent_temperature",
            "precipitation", "rain", "showers", "snowfall",
            "weather_code", "cloud_cover", "pressure_msl", "surface_pressure",
            "wind_speed_10m", "wind_direction_10m", "wind_gusts_10m", "is_day"
        ]
        params = {
            "latitude": lat,
            "longitude": lon,
            "current": ",".join(current_vars),
            "timezone": "auto",
            "forecast_days": 1
        }

        try:
            resp = requests.get(FORECAST_URL, params=params, timeout=10)
            resp.raise_for_status()
            data = resp.json()
            current = data.get("current", {})
            if not current:
                self.show_error("No current weather data available.")
                return

            # Parse values
            temp = current.get("temperature_2m")
            humidity = current.get("relative_humidity_2m")
            apparent = current.get("apparent_temperature")
            precip = current.get("precipitation")
            rain = current.get("rain")
            showers = current.get("showers")
            snowfall = current.get("snowfall")
            weather_code = current.get("weather_code")
            cloud_cover = current.get("cloud_cover")
            pressure_msl = current.get("pressure_msl")
            surface_pressure = current.get("surface_pressure")
            wind_speed = current.get("wind_speed_10m")
            wind_dir = current.get("wind_direction_10m")
            wind_gust = current.get("wind_gusts_10m")
            is_day = current.get("is_day")
            time_str = current.get("time")

            # Units
            units = data.get("current_units", {})
            temp_unit = units.get("temperature_2m", "°C")
            speed_unit = units.get("wind_speed_10m", "km/h")
            precip_unit = units.get("precipitation", "mm")
            pressure_unit = units.get("pressure_msl", "hPa")

            # Weather description
            desc, emoji = get_weather_data(weather_code)
            day_night = "☀️ Day" if is_day == 1 else "🌙 Night"

            # Format time
            if time_str:
                try:
                    dt = datetime.fromisoformat(time_str.replace("Z", "+00:00"))
                    formatted_time = dt.strftime("%Y-%m-%d %H:%M")
                except:
                    formatted_time = time_str
            else:
                formatted_time = "N/A"

            # Build result card
            self.build_result_card(
                city_name, country, state,
                emoji, desc,
                temp, temp_unit,
                apparent, humidity,
                wind_speed, speed_unit, wind_dir, wind_gust,
                pressure_msl, surface_pressure, pressure_unit,
                cloud_cover, precip, precip_unit, rain, showers, snowfall,
                day_night, formatted_time
            )

        except Exception as e:
            self.show_error(f"Weather fetch error: {str(e)}")

    def build_result_card(self, city, country, state, emoji, desc,
                          temp, temp_unit, apparent, humidity,
                          wind_speed, speed_unit, wind_dir, wind_gust,
                          pressure_msl, surface_pressure, pressure_unit,
                          cloud_cover, precip, precip_unit, rain, showers, snowfall,
                          day_night, time_str):
        """Construct the weather card with all data."""
        # Create card if not already existing
        if not hasattr(self, 'result_card') or self.result_card.winfo_manager() == '':
            self.result_card = ctk.CTkFrame(
                self.main_frame,
                corner_radius=20,
                border_width=2,
                border_color=("gray70", "gray30")
            )
        self.result_card.pack(fill="both", expand=True, pady=10)
        self.result_card.pack_propagate(False)  # Allow internal widgets to set size

        # Clear any previous content
        for widget in self.result_card.winfo_children():
            widget.destroy()

        # --- Top row: location and weather icon ---
        top_frame = ctk.CTkFrame(self.result_card, fg_color="transparent")
        top_frame.pack(fill="x", padx=20, pady=(15, 5))

        # Location
        location_text = f"{city}, {country}"
        if state:
            location_text += f"\n{state}"
        loc_label = ctk.CTkLabel(
            top_frame,
            text=location_text,
            font=ctk.CTkFont(size=20, weight="bold"),
            justify="left"
        )
        loc_label.pack(side="left")

        # Big emoji
        emoji_label = ctk.CTkLabel(
            top_frame,
            text=emoji,
            font=ctk.CTkFont(size=50)
        )
        emoji_label.pack(side="right")

        # --- Weather condition and temperature ---
        condition_frame = ctk.CTkFrame(self.result_card, fg_color="transparent")
        condition_frame.pack(fill="x", padx=20, pady=(5, 10))

        desc_label = ctk.CTkLabel(
            condition_frame,
            text=desc,
            font=ctk.CTkFont(size=18)
        )
        desc_label.pack(side="left")

        temp_label = ctk.CTkLabel(
            condition_frame,
            text=f"{temp}{temp_unit}",
            font=ctk.CTkFont(size=36, weight="bold")
        )
        temp_label.pack(side="right")

        # --- Grid of metrics ---
        metrics_frame = ctk.CTkFrame(self.result_card, fg_color="transparent")
        metrics_frame.pack(fill="both", expand=True, padx=20, pady=(5, 15))

        # Define rows of metrics (label, value, unit)
        metrics = [
            ("Feels like", f"{apparent}{temp_unit}"),
            ("Humidity", f"{humidity}%"),
            ("Wind", f"{wind_speed} {speed_unit}"),
            ("Direction", f"{wind_dir}°"),
            ("Gusts", f"{wind_gust} {speed_unit}"),
            ("Cloud Cover", f"{cloud_cover}%"),
            ("Precipitation", f"{precip} {precip_unit}"),
            ("Rain", f"{rain} {precip_unit}" if rain is not None else "N/A"),
            ("Showers", f"{showers} {precip_unit}" if showers is not None else "N/A"),
            ("Snowfall", f"{snowfall} cm" if snowfall is not None else "N/A"),
            ("Pressure (MSL)", f"{pressure_msl} {pressure_unit}"),
            ("Surface Pressure", f"{surface_pressure} {pressure_unit}"),
        ]

        # Arrange in a grid (3 columns)
        row_count = (len(metrics) + 2) // 3
        for row in range(row_count):
            row_frame = ctk.CTkFrame(metrics_frame, fg_color="transparent")
            row_frame.pack(fill="x", pady=3)
            for col in range(3):
                idx = row * 3 + col
                if idx < len(metrics):
                    label, value = metrics[idx]
                    # Label + value in one frame
                    item_frame = ctk.CTkFrame(row_frame, fg_color="transparent")
                    item_frame.pack(side="left", fill="both", expand=True, padx=5)

                    label_w = ctk.CTkLabel(
                        item_frame,
                        text=label + ":",
                        font=ctk.CTkFont(size=13, weight="bold"),
                        anchor="w"
                    )
                    label_w.pack(anchor="w")
                    value_w = ctk.CTkLabel(
                        item_frame,
                        text=value,
                        font=ctk.CTkFont(size=13),
                        anchor="w"
                    )
                    value_w.pack(anchor="w")

        # --- Footer: timestamp and day/night ---
        footer_frame = ctk.CTkFrame(self.result_card, fg_color="transparent")
        footer_frame.pack(fill="x", padx=20, pady=(0, 15))

        time_label = ctk.CTkLabel(
            footer_frame,
            text=f"Updated: {time_str}",
            font=ctk.CTkFont(size=12),
            text_color="gray"
        )
        time_label.pack(side="left")

        daynight_label = ctk.CTkLabel(
            footer_frame,
            text=day_night,
            font=ctk.CTkFont(size=14)
        )
        daynight_label.pack(side="right")

    def show_error(self, msg):
        """Display an error message."""
        self.error_label.configure(text=f"⚠️ {msg}", text_color="red")
        self.clear_result_card()

    def clear_error(self):
        self.error_label.configure(text="")

    def clear_result_card(self):
        if hasattr(self, 'result_card') and self.result_card.winfo_manager() != '':
            self.result_card.pack_forget()
            # Destroy children? They'll be recreated.

if __name__ == "__main__":
    app = WeatherApp()
    app.mainloop()
