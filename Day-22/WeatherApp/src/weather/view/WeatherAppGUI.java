package weather.view;

import weather.controller.WeatherController;
import weather.model.ForecastData;
import weather.model.WeatherData;
import weather.util.BackgroundManager;
import weather.util.IconLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeatherAppGUI extends JFrame {
    // UI components
    private JPanel mainPanel;
    private JPanel centerPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> unitCombo;
    private JLabel cityLabel, tempLabel, descLabel, humidityLabel, windLabel, iconLabel;
    private JPanel forecastPanel;
    private DefaultListModel<String> historyModel;
    private JList<String> historyList;
    private JButton clearHistoryBtn;
    private JLabel statusLabel;

    private WeatherController controller;
    private List<String> searchHistory;
    private boolean isMetric = true;

    public WeatherAppGUI() {
        controller = new WeatherController();
        searchHistory = new ArrayList<>();
        historyModel = new DefaultListModel<>();
        initUI();
        applyDynamicBackground();
    }

    private void initUI() {
        setTitle("🌤️ Weather Information App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(createCurrentWeatherPanel(), BorderLayout.NORTH);
        centerPanel.add(createForecastPanel(), BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(createSidePanel(), BorderLayout.EAST);
        mainPanel.add(createStatusBar(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setOpaque(false);

        JLabel title = new JLabel("🌤️ Weather Information");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setOpaque(false);

        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.addActionListener(e -> performSearch());

        searchButton = new JButton("🔍 Get Weather");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(76, 175, 80));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> performSearch());

        unitCombo = new JComboBox<>(new String[]{"Metric (°C, m/s)", "Imperial (°F, mph)"});
        unitCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        unitCombo.addActionListener(e -> {
            isMetric = unitCombo.getSelectedIndex() == 0;
            if (cityLabel != null && !cityLabel.getText().equals("Enter a city")) {
                performSearch();
            }
        });

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(unitCombo);

        header.add(title, BorderLayout.NORTH);
        header.add(searchPanel, BorderLayout.SOUTH);
        return header;
    }

    private JPanel createCurrentWeatherPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255,255,255,80), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(new Color(255,255,255,30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cityLabel = new JLabel("Enter a city to get weather");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 24));
        cityLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(cityLabel, gbc);

        iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(iconLabel, gbc);

        tempLabel = new JLabel("--°C");
        tempLabel.setFont(new Font("Arial", Font.BOLD, 48));
        tempLabel.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(tempLabel, gbc);

        descLabel = new JLabel("--");
        descLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        descLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(descLabel, gbc);

        humidityLabel = new JLabel("💧 Humidity: --%");
        humidityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        humidityLabel.setForeground(Color.WHITE);
        gbc.gridy = 3;
        panel.add(humidityLabel, gbc);

        windLabel = new JLabel("💨 Wind: -- m/s");
        windLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        windLabel.setForeground(Color.WHITE);
        gbc.gridy = 4;
        panel.add(windLabel, gbc);

        return panel;
    }

    private JPanel createForecastPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JLabel title = new JLabel("📊 5-Day Forecast");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);

        forecastPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        forecastPanel.setOpaque(false);
        JScrollPane scroll = new JScrollPane(forecastPanel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSidePanel() {
        JPanel side = new JPanel(new BorderLayout(5, 5));
        side.setOpaque(false);
        side.setBorder(new EmptyBorder(10, 10, 10, 10));
        side.setPreferredSize(new Dimension(200, 0));

        JLabel title = new JLabel("📜 Search History");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        side.add(title, BorderLayout.NORTH);

        historyList = new JList<>(historyModel);
        historyList.setFont(new Font("Arial", Font.PLAIN, 12));
        historyList.setBackground(new Color(255,255,255,80));
        historyList.setSelectionBackground(new Color(100, 149, 237));
        historyList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = historyList.getSelectedValue();
                if (selected != null) {
                    String city = selected.split(" - ")[0];
                    searchField.setText(city);
                    performSearch();
                }
            }
        });
        JScrollPane scroll = new JScrollPane(historyList);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        side.add(scroll, BorderLayout.CENTER);

        clearHistoryBtn = new JButton("Clear History");
        clearHistoryBtn.addActionListener(e -> {
            searchHistory.clear();
            historyModel.clear();
        });
        side.add(clearHistoryBtn, BorderLayout.SOUTH);
        return side;
    }

    private JPanel createStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);
        bar.setBorder(new EmptyBorder(10, 0, 0, 0));

        statusLabel = new JLabel("Ready");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bar.add(statusLabel, BorderLayout.WEST);

        JLabel timeLabel = new JLabel("🌅 " + BackgroundManager.getTimePeriod());
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bar.add(timeLabel, BorderLayout.EAST);
        return bar;
    }

    private void applyDynamicBackground() {
        Color bg = BackgroundManager.getBackgroundColor();
        mainPanel.setBackground(bg);
        getContentPane().setBackground(bg);
        mainPanel.setOpaque(true);
        centerPanel.setOpaque(false);
    }

    private void performSearch() {
        String city = searchField.getText().trim();
        if (city.isEmpty()) {
            showError("Please enter a city name.");
            return;
        }

        statusLabel.setText("Fetching weather for " + city + "...");
        statusLabel.setForeground(Color.YELLOW);
        searchButton.setEnabled(false);

        new SwingWorker<Void, Void>() {
            private WeatherData weather;
            private ForecastData forecast;
            private Exception error;

            @Override
            protected Void doInBackground() {
                try {
                    weather = controller.getCurrentWeather(city);
                    forecast = controller.getForecast(city);
                    System.out.println("✅ Weather data received: " + weather.getCityName());
                    System.out.println("✅ Forecast data received: " + (forecast != null ? forecast.getList().size() : 0) + " entries");
                } catch (Exception e) {
                    error = e;
                    System.err.println("❌ Error in doInBackground: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                searchButton.setEnabled(true);
                if (error != null) {
                    showError(error.getMessage());
                    return;
                }
                if (weather == null) {
                    showError("No weather data received.");
                    return;
                }
                System.out.println("🔄 Updating UI with weather data...");
                updateCurrentWeather(weather);
                updateForecast(forecast);
                addToHistory(city);
                statusLabel.setText("✅ Weather updated for " + city);
                statusLabel.setForeground(new Color(144, 238, 144));
            }
        }.execute();
    }

    private void updateCurrentWeather(WeatherData w) {
        if (w == null) {
            System.err.println("❌ updateCurrentWeather: weather is null");
            return;
        }
        System.out.println("🔄 updateCurrentWeather called for: " + w.getCityName());

        String country = w.getSys() != null ? w.getSys().getCountry() : "";
        cityLabel.setText(w.getCityName() + (country.isEmpty() ? "" : ", " + country));

        double temp = w.getMain().getTemperature();
        tempLabel.setText(formatTemperature(temp));

        descLabel.setText(w.getWeatherDescription());

        int humidity = w.getMain().getHumidity();
        humidityLabel.setText("💧 Humidity: " + humidity + "%");

        double wind = w.getWind() != null ? w.getWind().getSpeed() : 0;
        windLabel.setText("💨 Wind: " + formatWindSpeed(wind));

        String iconCode = w.getIconCode();
        System.out.println("🔄 Loading icon: " + iconCode);
        ImageIcon icon = IconLoader.getIcon(iconCode, 80, 80);
        iconLabel.setIcon(icon);
        iconLabel.repaint();

        // Force UI refresh
        cityLabel.revalidate();
        tempLabel.revalidate();
        descLabel.revalidate();
        humidityLabel.revalidate();
        windLabel.revalidate();
        System.out.println("✅ UI updated with weather data");
    }

    private void updateForecast(ForecastData f) {
        forecastPanel.removeAll();
        if (f == null || f.getList() == null || f.getList().isEmpty()) {
            JLabel empty = new JLabel("No forecast data");
            empty.setForeground(Color.WHITE);
            forecastPanel.add(empty);
            forecastPanel.revalidate();
            forecastPanel.repaint();
            return;
        }

        System.out.println("🔄 updateForecast called, entries: " + f.getList().size());

        List<ForecastData.ForecastItem> daily = f.getDailyForecast();
        System.out.println("🔄 Daily forecast entries: " + daily.size());

        for (ForecastData.ForecastItem item : daily) {
            forecastPanel.add(createForecastDayBox(item));
        }
        forecastPanel.revalidate();
        forecastPanel.repaint();
        System.out.println("✅ Forecast UI updated");
    }

    private JPanel createForecastDayBox(ForecastData.ForecastItem item) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(true);
        box.setBackground(new Color(255,255,255,50));
        box.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255,255,255,80), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        box.setPreferredSize(new Dimension(110, 160));

        LocalDateTime date = LocalDateTime.ofInstant(
            Instant.ofEpochSecond(item.getTimestamp()),
            ZoneId.systemDefault()
        );
        String day = date.format(DateTimeFormatter.ofPattern("EEE"));
        JLabel dayLabel = new JLabel(day);
        dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dayLabel.setForeground(Color.WHITE);
        dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon icon = IconLoader.getIcon(item.getIconCode(), 40, 40);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        double temp = item.getMain().getTemperature();
        JLabel tempLabel = new JLabel(formatTemperature(temp));
        tempLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tempLabel.setForeground(Color.WHITE);
        tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel(item.getWeatherDescription());
        descLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        descLabel.setForeground(Color.WHITE);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);

        box.add(dayLabel);
        box.add(Box.createVerticalStrut(5));
        box.add(iconLabel);
        box.add(Box.createVerticalStrut(5));
        box.add(tempLabel);
        box.add(Box.createVerticalStrut(2));
        box.add(descLabel);

        return box;
    }

    private void addToHistory(String city) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String entry = city + " - " + timestamp;
        searchHistory.removeIf(e -> e.startsWith(city + " -"));
        searchHistory.add(0, entry);
        while (searchHistory.size() > 10) searchHistory.remove(searchHistory.size() - 1);
        historyModel.clear();
        for (String s : searchHistory) historyModel.addElement(s);
    }

    private String formatTemperature(double celsius) {
        if (isMetric) return String.format("%.1f°C", celsius);
        return String.format("%.1f°F", celsius * 9.0/5.0 + 32);
    }

    private String formatWindSpeed(double mps) {
        if (isMetric) return String.format("%.1f m/s", mps);
        return String.format("%.1f mph", mps * 2.23694);
    }

    private void showError(String msg) {
        System.err.println("❌ Error: " + msg);
        statusLabel.setText("❌ " + msg);
        statusLabel.setForeground(Color.RED);
    }
}