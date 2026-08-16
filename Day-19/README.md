# Clock Application – Java Multithreading Demo

A modern desktop clock application that demonstrates Java multithreading with thread priorities. This application uses two threads—a background updater and a high‑priority display refresher—to show real‑time time and date updates in a sleek Swing GUI.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Requirements](#requirements)
- [Compilation and Execution](#compilation-and-execution)
- [Usage Guide](#usage-guide)
- [Key Components](#key-components)
- [How Thread Priorities Work](#how-thread-priorities-work)
- [Swing Thread Safety](#swing-thread-safety)
- [Extending the Application](#extending-the-application)
- [Troubleshooting](#troubleshooting)
- [License](#license)

---

## Overview

The **Clock Application** is a Java Swing desktop application that showcases the use of **multithreading** and **thread priorities** in a real‑world scenario. It consists of:

- A **background updater thread** (lower priority) that continuously updates the time and date strings.
- A **display thread** (higher priority) that refreshes the GUI to show the current time in real‑time.

This application is an excellent demonstration of:
- Java multithreading concepts.
- Thread priorities and their impact on scheduling.
- Swing threading best practices (`SwingUtilities.invokeLater()`).

---

## Features

| Feature                     | Description                                                                                      |
|-----------------------------|--------------------------------------------------------------------------------------------------|
| **Real‑Time Clock**         | Displays current time in `HH:mm:ss` format and date in `dd‑MM‑yyyy` format.                      |
| **Two Threads**             | Separate threads for background updates and GUI refresh with different priorities.                |
| **Thread Priority Demo**    | Display thread runs at `MAX_PRIORITY` (10); updater thread runs at `NORM_PRIORITY - 1` (4).       |
| **Clean GUI**               | Modern dark‑themed interface with large, readable fonts.                                         |
| **Status Information**      | Shows thread priorities and running status in the GUI.                                           |
| **Daemon Threads**          | Both threads are daemon threads that exit automatically when the application closes.             |
| **Graceful Shutdown**       | Threads are interrupted and cleaned up when the window is closed.                                |
| **Swing Thread Safety**     | All GUI updates are performed on the Event Dispatch Thread (EDT).                                |
| **System Look and Feel**    | Uses the system's native look and feel for better integration.                                   |

---

## Technology Stack

- **Java SE** — Core language and standard library.
- **Java Swing** — GUI framework for the desktop interface.
- **Java Threading** — `Thread`, `Runnable`, thread priorities, and `volatile` variables.
- **Java Time API** — `LocalDateTime` and `DateTimeFormatter` for time and date formatting.

**No external dependencies** — pure Java standard library.

---

## Requirements

- **Java** — JDK 8 or higher (uses `LocalDateTime`, Swing, and threading).

**No external dependencies** — pure Java SE.

---

## Compilation and Execution

### 1. Compile

```bash
javac ClockApplication.java
```

### 2. Run

```bash
java ClockApplication
```

### 3. Using an IDE (IntelliJ IDEA, Eclipse, VS Code)

1. Open the project folder.
2. Run `ClockApplication.main()`.

---

## Usage Guide

### Step 1: Launch the Application

The clock window appears with:

- **Time Display** — Large, bold, cyan‑coloured text showing the current time.
- **Date Display** — White text showing the current date.
- **Status Bar** — Shows:
  - **Running** indicator (green dot).
  - **Thread priorities** (Display: MAX (10) | Updater: NORM‑1 (4)).

### Step 2: Watch the Clock

- The time updates every 500 milliseconds (twice per second).
- The date updates automatically when the day changes.

### Step 3: Observe Thread Priorities

- The display thread runs at **priority 10** (`MAX_PRIORITY`).
- The updater thread runs at **priority 4** (`NORM_PRIORITY - 1`).
- The priority labels in the status bar reflect these settings.

### Step 4: Close the Application

- Click the window close button (✕).
- Both threads are interrupted and shut down gracefully.

---

## Key Components

### ClockApplication (Main Class)

```java
public class ClockApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Set look and feel, create and display the frame
            ClockFrame frame = new ClockFrame();
            frame.setVisible(true);
        });
    }
}
```

**Purpose:** Entry point of the application. Launches the GUI on the Event Dispatch Thread (EDT).

---

### ClockFrame (Main Window)

```java
class ClockFrame extends JFrame {
    private static final int DISPLAY_PRIORITY = Thread.MAX_PRIORITY;      // 10
    private static final int UPDATER_PRIORITY = Thread.NORM_PRIORITY - 1; // 4

    private volatile String currentTimeString;
    private volatile String currentDateString;
    private volatile boolean running = true;
}
```

**Key Features:**
- Manages the GUI components and threads.
- Shared `volatile` variables for thread‑safe communication.
- Starts both threads with their respective priorities.
- Cleans up threads on window close.

---

### TimeUpdater (Background Thread)

```java
private class TimeUpdater implements Runnable {
    @Override
    public void run() {
        while (running) {
            updateTimeStrings();  // Updates currentTimeString and currentDateString
            Thread.sleep(100);    // Runs every 100ms (lower priority)
        }
    }
}
```

**Purpose:**
- Runs at **priority 4** (`NORM_PRIORITY - 1`).
- Updates the time and date strings in the background.
- Sleeps for 100ms to reduce CPU usage.

---

### DisplayUpdater (GUI Thread)

```java
private class DisplayUpdater implements Runnable {
    @Override
    public void run() {
        while (running) {
            SwingUtilities.invokeLater(() -> {
                clockPanel.updateTime(currentTimeString, currentDateString);
            });
            Thread.sleep(500);  // Updates GUI twice per second
        }
    }
}
```

**Purpose:**
- Runs at **priority 10** (`MAX_PRIORITY`).
- Refreshes the GUI on the EDT.
- Sleeps for 500ms (updates twice per second).

---

### ClockPanel (Custom JPanel)

```java
class ClockPanel extends JPanel {
    private JLabel timeLabel;
    private JLabel dateLabel;
    private JLabel statusLabel;
    private JLabel priorityLabel;

    public void updateTime(String time, String date) {
        timeLabel.setText(time);
        dateLabel.setText(date);
    }
}
```

**Purpose:**
- Custom panel with a dark theme and large fonts.
- Displays time, date, and status information.
- Updates are called from the display thread via `SwingUtilities.invokeLater()`.

---

## How Thread Priorities Work

### Thread Priority Constants

| Priority Level          | Value | Purpose                                                                 |
|-------------------------|-------|-------------------------------------------------------------------------|
| `Thread.MAX_PRIORITY`   | 10    | Display thread — highest priority for smooth GUI updates.               |
| `Thread.NORM_PRIORITY`  | 5     | Default priority.                                                       |
| `Thread.NORM_PRIORITY - 1` | 4     | Updater thread — lower priority to yield CPU time to the display thread. |

### Priority Demonstration

```java
displayThread.setPriority(Thread.MAX_PRIORITY);   // Priority 10
updaterThread.setPriority(Thread.NORM_PRIORITY - 1); // Priority 4
```

### Why Use Different Priorities?

- **Display Thread (High Priority)**: Ensures that the GUI is updated promptly, providing a smooth user experience. This is critical because users perceive lag when the clock doesn't update on time.

- **Updater Thread (Low Priority)**: Performs background updates without competing heavily with the display thread. Since time updates don't need to be instant, a lower priority is acceptable.

### Priority Scheduling

The Java Virtual Machine (JVM) schedules threads based on their priorities. Higher priority threads are generally executed more frequently than lower priority threads. However, the exact behaviour depends on the operating system's thread scheduler.

---

## Swing Thread Safety

### The Event Dispatch Thread (EDT)

Swing is not thread‑safe. All GUI updates must be performed on the **Event Dispatch Thread (EDT)**.

### `SwingUtilities.invokeLater()`

```java
SwingUtilities.invokeLater(() -> {
    clockPanel.updateTime(currentTimeString, currentDateString);
});
```

**Purpose:** Queues the GUI update to be executed on the EDT, ensuring thread safety.

### Why Not Update GUI Directly?

```java
// ❌ WRONG — Direct GUI update from a background thread
clockPanel.updateTime(currentTimeString, currentDateString);
```

This would cause unpredictable behaviour, including GUI freezes, flickering, or exceptions.

---

## Extending the Application

### Add a Stopwatch Feature

- Add a new thread for the stopwatch.
- Use `System.nanoTime()` for accurate timing.
- Display elapsed time in `HH:mm:ss.SSS` format.

### Add an Alarm Feature

- Allow users to set a time for the alarm.
- Use a `Timer` or a dedicated thread to check the current time.
- Play a sound or show a notification when the alarm triggers.

### Add Multiple Time Zones

- Use `ZoneId` to display time in different time zones.
- Add a dropdown to select the time zone.

### Add a Digital / Analog Toggle

- Create an analog clock view using `Graphics2D`.
- Switch between digital and analog modes.

### Add a Countdown Timer

- Let users set a countdown duration.
- Display the remaining time and trigger an event when it reaches zero.

### Log Thread Activity

- Use `java.util.logging.Logger` to log thread start, stop, and update events.

### Make the Clock Resizable

- Modify `ClockPanel` to adjust font sizes based on the window size.
- Use `ComponentListener` to detect resize events.

---

## Troubleshooting

| Issue                        | Solution                                                                 |
|------------------------------|--------------------------------------------------------------------------|
| **Compilation errors**       | Ensure the file is named `ClockApplication.java` and compiled correctly. |
| **Clock not updating**       | Check that the threads are started. Look for exceptions in the console.  |
| **GUI flickering**           | Ensure all GUI updates are done on the EDT via `SwingUtilities.invokeLater()`. |
| **High CPU usage**           | The threads sleep for 100ms and 500ms respectively. If CPU usage is high, increase sleep durations. |
| **Thread not terminating**   | The window listener interrupts threads on close. Ensure `running` is set to `false`. |
| **Priority not visible**     | The priority labels are for display only. The actual priority is set in the code. |
| **ClassNotFoundException**   | This is a single‑file application. No external classes are required.    |

---

## Thread Priority in Action: Console Output

When the application starts, the following is printed to the console:

```
Display Thread Priority: 10 (MAX_PRIORITY)
Updater Thread Priority: 4 (NORM_PRIORITY - 1)
```

This confirms that the threads are running with their intended priorities.

---

## Best Practices Demonstrated

- **Thread Safety**: All GUI updates are performed on the EDT.
- **Daemon Threads**: Threads are set as daemon, so they don't prevent the JVM from exiting.
- **Graceful Shutdown**: Threads are interrupted and cleaned up when the window closes.
- **Volatile Variables**: `volatile` ensures visibility of `currentTimeString` and `currentDateString` across threads.
- **Priority Usage**: Demonstrates appropriate priority assignment based on task importance.
- **Sleeping**: Threads sleep to reduce CPU usage and prevent busy‑waiting.
- **Runnable Interface**: Both threads implement `Runnable` for better separation of concerns.

---

## License

This project is created for **educational purposes** — to demonstrate Java multithreading, thread priorities, and Swing threading best practices. You are free to use, modify, and distribute this code for learning.

---

**Author:** Student Developer  
**Version:** 1.0  
**Date:** June 2026
