package weather.controller;

import com.google.gson.Gson;
import weather.model.WeatherData;
import weather.model.ForecastData;
import weather.util.ApiConfig;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class WeatherController {
    private final HttpClient httpClient;
    private final Gson gson;
    private final String apiKey;

    public WeatherController() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
        this.apiKey = ApiConfig.getApiKey();
        System.out.println("🔑 Using API key: " + apiKey.substring(0, 4) + "****");
    }

    public WeatherData getCurrentWeather(String cityName) throws Exception {
        if (cityName == null || cityName.trim().isEmpty())
            throw new Exception("Please enter a city name.");

        String encoded = URLEncoder.encode(cityName.trim(), StandardCharsets.UTF_8);
        String url = String.format(
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
            encoded, apiKey
        );
        System.out.println("🌐 Request: " + url);

        String response = makeApiCall(url);
        System.out.println("📨 Response: " + response);

        // Parse response to check for error
        try {
            Map<?, ?> map = gson.fromJson(response, Map.class);
            Object codObj = map.get("cod");
            if (codObj != null) {
                // Convert cod to string properly - handles both number and string
                String code = String.valueOf(codObj);
                // Remove .0 if it's a whole number (e.g., 200.0 -> 200)
                if (code.endsWith(".0")) {
                    code = code.substring(0, code.length() - 2);
                }
                if (!"200".equals(code)) {
                    String msg = (String) map.get("message");
                    throw new Exception("API error: " + (msg != null ? msg : code));
                }
            }
        } catch (Exception e) {
            // If parsing fails, rethrow as error
            throw new Exception("Failed to parse weather response: " + e.getMessage());
        }

        // Success - parse to WeatherData
        return gson.fromJson(response, WeatherData.class);
    }

    public ForecastData getForecast(String cityName) throws Exception {
        if (cityName == null || cityName.trim().isEmpty())
            throw new Exception("Please enter a city name.");

        String encoded = URLEncoder.encode(cityName.trim(), StandardCharsets.UTF_8);
        String url = String.format(
            "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
            encoded, apiKey
        );
        System.out.println("🌐 Forecast URL: " + url);

        String response = makeApiCall(url);
        System.out.println("📨 Forecast response: " + response);

        // Parse response to check for error
        try {
            Map<?, ?> map = gson.fromJson(response, Map.class);
            Object codObj = map.get("cod");
            if (codObj != null) {
                // Convert cod to string properly - handles both number and string
                String code = String.valueOf(codObj);
                // Remove .0 if it's a whole number (e.g., 200.0 -> 200)
                if (code.endsWith(".0")) {
                    code = code.substring(0, code.length() - 2);
                }
                if (!"200".equals(code)) {
                    String msg = (String) map.get("message");
                    throw new Exception("Forecast API error: " + (msg != null ? msg : code));
                }
            }
        } catch (Exception e) {
            // If parsing fails, rethrow as error
            throw new Exception("Failed to parse forecast response: " + e.getMessage());
        }

        return gson.fromJson(response, ForecastData.class);
    }

    private String makeApiCall(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(java.time.Duration.ofSeconds(10))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}