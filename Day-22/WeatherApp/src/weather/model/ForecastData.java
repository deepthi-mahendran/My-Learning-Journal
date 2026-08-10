package weather.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.stream.Collectors;

public class ForecastData {
    @SerializedName("city") private City city;
    @SerializedName("list") private List<ForecastItem> list;

    public static class City {
        @SerializedName("name")    private String name;
        @SerializedName("country") private String country;
        public String getName()    { return name; }
        public String getCountry() { return country; }
    }

    public static class ForecastItem {
        @SerializedName("dt")      private long timestamp;
        @SerializedName("main")    private WeatherData.MainWeather main;
        @SerializedName("weather") private WeatherData.WeatherCondition[] weather;
        @SerializedName("wind")    private WeatherData.Wind wind;
        @SerializedName("dt_txt")  private String dateTimeText;

        public long getTimestamp() { return timestamp; }
        public WeatherData.MainWeather getMain() { return main; }
        public WeatherData.WeatherCondition[] getWeather() { return weather; }
        public WeatherData.Wind getWind() { return wind; }
        public String getDateTimeText() { return dateTimeText; }

        public String getWeatherDescription() {
            if (weather != null && weather.length > 0) return weather[0].getDescription();
            return "Unknown";
        }
        public String getIconCode() {
            if (weather != null && weather.length > 0) return weather[0].getIcon();
            return "01d";
        }
    }

    public City getCity() { return city; }
    public List<ForecastItem> getList() { return list; }

    // Returns one forecast per day (at 12:00). If none, returns first 5 entries.
    public List<ForecastItem> getDailyForecast() {
        List<ForecastItem> noon = list.stream()
                .filter(item -> item.getDateTimeText().contains("12:00:00"))
                .limit(5)
                .collect(Collectors.toList());
        if (noon.isEmpty() && list.size() >= 5) {
            return list.subList(0, 5);
        }
        return noon;
    }
}
