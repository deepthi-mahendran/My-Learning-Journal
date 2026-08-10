package weather;

import javax.swing.SwingUtilities;
import weather.view.WeatherAppGUI;

/**
 * Entry point for the Weather App (Swing version).
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WeatherAppGUI app = new WeatherAppGUI();
            app.setVisible(true);
        });
    }
}