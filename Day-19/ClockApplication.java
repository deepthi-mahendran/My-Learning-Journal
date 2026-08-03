import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ClockApplication - A simple clock application that demonstrates Java
 * multithreading with thread priorities.
 * 
 * <p>This application uses two threads: a background updater thread and a
 * display thread with higher priority. The display thread refreshes the
 * GUI to show the current time and date in real-time.</p>
 * 
 * <p>This implementation follows Swing threading best practices by using
 * SwingUtilities.invokeLater() for GUI updates.</p>
 * 
 * @author Student
 * @version 1.0
 * @since 2026-06-22
 */
public class ClockApplication {
    
    /**
     * The main method launches the clock application.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Launch the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for better appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Fall back to default look and feel
            }
            
            // Create and display the main frame
            ClockFrame frame = new ClockFrame();
            frame.setVisible(true);
        });
    }
}

/**
 * ClockFrame - The main application window containing the clock display.
 * 
 * <p>This class manages the GUI components and starts the background threads
 * for time updating and display refreshing.</p>
 */
class ClockFrame extends JFrame {
    
    // Constants for thread priorities
    private static final int DISPLAY_PRIORITY = Thread.MAX_PRIORITY;      // Priority 10
    private static final int UPDATER_PRIORITY = Thread.NORM_PRIORITY - 1; // Priority 4
    
    // GUI components
    private ClockPanel clockPanel;
    
    // Threads
    private Thread updaterThread;
    private Thread displayThread;
    
    // Shared data
    private volatile String currentTimeString;
    private volatile String currentDateString;
    private volatile boolean running = true;
    
    /**
     * Constructs the main clock frame.
     */
    public ClockFrame() {
        setTitle("Simple Clock Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize the clock panel
        clockPanel = new ClockPanel();
        add(clockPanel, BorderLayout.CENTER);
        
        // Initialize time strings
        updateTimeStrings();
        
        // Start the threads
        startThreads();
        
        // Add window listener to clean up threads on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                running = false;
                if (updaterThread != null) {
                    updaterThread.interrupt();
                }
                if (displayThread != null) {
                    displayThread.interrupt();
                }
            }
        });
    }
    
    /**
     * Updates the current time and date strings from LocalDateTime.
     */
    private void updateTimeStrings() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        currentTimeString = now.format(timeFormatter);
        currentDateString = now.format(dateFormatter);
    }
    
    /**
     * Starts the background updater and display threads.
     */
    private void startThreads() {
        // Create and start the updater thread (lower priority)
        updaterThread = new Thread(new TimeUpdater(), "TimeUpdater");
        updaterThread.setPriority(UPDATER_PRIORITY);
        updaterThread.setDaemon(true);
        
        // Create and start the display thread (higher priority)
        displayThread = new Thread(new DisplayUpdater(), "DisplayUpdater");
        displayThread.setPriority(DISPLAY_PRIORITY);
        displayThread.setDaemon(true);
        
        // Log thread priorities
        System.out.println("Display Thread Priority: " + displayThread.getPriority() + 
                          " (MAX_PRIORITY)");
        System.out.println("Updater Thread Priority: " + updaterThread.getPriority() + 
                          " (NORM_PRIORITY - 1)");
        
        updaterThread.start();
        displayThread.start();
    }
    
    /**
     * Inner class representing the background time updater thread.
     * 
     * <p>This thread runs at a lower priority and continuously updates
     * the time and date strings in the background.</p>
     */
    private class TimeUpdater implements Runnable {
        @Override
        public void run() {
            while (running) {
                try {
                    // Update time strings
                    updateTimeStrings();
                    
                    // Sleep for 100ms to reduce CPU usage
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Thread was interrupted, exit gracefully
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
    
    /**
     * Inner class representing the display updater thread.
     * 
     * <p>This thread runs at a higher priority and refreshes the GUI
     * display at regular intervals to show the current time.</p>
     */
    private class DisplayUpdater implements Runnable {
        @Override
        public void run() {
            while (running) {
                try {
                    // Update the GUI on the Event Dispatch Thread
                    SwingUtilities.invokeLater(() -> {
                        clockPanel.updateTime(currentTimeString, currentDateString);
                    });
                    
                    // Sleep for 500ms (updates twice per second)
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // Thread was interrupted, exit gracefully
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}

/**
 * ClockPanel - A custom JPanel that displays the current time and date.
 * 
 * <p>This panel uses a modern, clean design with large, readable fonts
 * for the time display and a smaller font for the date.</p>
 */
class ClockPanel extends JPanel {
    
    // GUI components
    private JLabel timeLabel;
    private JLabel dateLabel;
    private JLabel statusLabel;
    private JLabel priorityLabel;
    
    /**
     * Constructs the clock panel with initial display values.
     */
    public ClockPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Time label (large, bold, centered)
        timeLabel = new JLabel("--:--:--", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 72));
        timeLabel.setForeground(new Color(0, 200, 255));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(timeLabel, gbc);
        
        // Date label
        dateLabel = new JLabel("-- -- ----", SwingConstants.CENTER);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        dateLabel.setForeground(new Color(200, 200, 200));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 10, 5);
        add(dateLabel, gbc);
        
        // Status panel (shows thread priorities)
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        statusPanel.setOpaque(false);
        
        statusLabel = new JLabel("● Running");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(0, 255, 100));
        statusPanel.add(statusLabel);
        
        priorityLabel = new JLabel("Display: MAX (10) | Updater: NORM-1 (4)");
        priorityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        priorityLabel.setForeground(new Color(150, 150, 150));
        statusPanel.add(priorityLabel);
        
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 5, 5);
        add(statusPanel, gbc);
        
        // Set initial values
        updateTime("--:--:--", "-- -- ----");
    }
    
    /**
     * Updates the displayed time and date.
     * 
     * @param time The time string in "HH:mm:ss" format
     * @param date The date string in "dd-MM-yyyy" format
     */
    public void updateTime(String time, String date) {
        timeLabel.setText(time);
        dateLabel.setText(date);
        repaint();
    }
}