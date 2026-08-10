package weather.model;

import com.google.gson.annotations.SerializedName;

public class WeatherData {
    @SerializedName("name")
    private String cityName;

    @SerializedName("main")
    private MainWeather main;

    @SerializedName("weather")
    private WeatherCondition[] weather;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("sys")
    private Sys sys;

    @SerializedName("dt")
    private long timestamp;

    // ---------- Inner classes ----------
    public static class MainWeather {
        @SerializedName("temp")      private double temperature;
        @SerializedName("feels_like")private double feelsLike;
        @SerializedName("humidity")  private int humidity;
        @SerializedName("pressure")  private int pressure;

        public double getTemperature() { return temperature; }
        public double getFeelsLike()   { return feelsLike; }
        public int getHumidity()       { return humidity; }
        public int getPressure()       { return pressure; }
    }

    public static class WeatherCondition {
        @SerializedName("id")          private int id;
        @SerializedName("main")        private String main;
        @SerializedName("description") private String description;
        @SerializedName("icon")        private String icon;

        public int getId()          { return id; }
        public String getMain()     { return main; }
        public String getDescription() { return description; }
        public String getIcon()     { return icon; }
    }

    public static class Wind {
        @SerializedName("speed") private double speed;
        @SerializedName("deg")   private int degree;
        public double getSpeed() { return speed; }
        public int getDegree()   { return degree; }
    }

    public static class Sys {
        @SerializedName("country") private String country;
        @SerializedName("sunrise") private long sunrise;
        @SerializedName("sunset")  private long sunset;
        public String getCountry() { return country; }
        public long getSunrise()   { return sunrise; }
        public long getSunset()    { return sunset; }
    }

    // Getters
    public String getCityName() { return cityName; }
    public MainWeather getMain() { return main; }
    public WeatherCondition[] getWeather() { return weather; }
    public Wind getWind() { return wind; }
    public Sys getSys() { return sys; }
    public long getTimestamp() { return timestamp; }

    public String getWeatherDescription() {
        if (weather != null && weather.length > 0) return weather[0].getDescription();
        return "Unknown";
    }

    public String getIconCode() {
        if (weather != null && weather.length > 0) return weather[0].getIcon();
        return "01d";
    }
}