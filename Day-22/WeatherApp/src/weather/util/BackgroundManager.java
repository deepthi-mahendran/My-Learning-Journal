package weather.util;

import java.awt.Color;
import java.time.LocalTime;

public class BackgroundManager {
    public static Color getBackgroundColor() {
        int hour = LocalTime.now().getHour();
        if (hour >= 5 && hour < 12)  return new Color(255, 209, 148);  // morning
        if (hour >= 12 && hour < 17) return new Color(135, 206, 235);  // afternoon
        if (hour >= 17 && hour < 20) return new Color(255, 140, 0);    // evening
        return new Color(25, 25, 112);                                 // night
    }

    public static String getTimePeriod() {
        int hour = LocalTime.now().getHour();
        if (hour >= 5 && hour < 12)  return "Morning";
        if (hour >= 12 && hour < 17) return "Afternoon";
        if (hour >= 17 && hour < 20) return "Evening";
        return "Night";
    }
}