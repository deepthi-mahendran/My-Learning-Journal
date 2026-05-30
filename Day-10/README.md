# Car Rental Agency System (GUI)

A complete Java Swing desktop application for managing a rental vehicle fleet. The system allows users to add cars, motorcycles, and trucks, capture all relevant attributes, and display the complete inventory – all through a clean, tabbed graphical interface.

---

## Overview

The **Car Rental Agency System (GUI)** provides rental agency staff with an intuitive windowed interface to:

- Add new vehicles (Cars, Motorcycles, Trucks)
- Capture all type‑specific attributes (e.g., doors, fuel type, wheels, cargo capacity, transmission)
- View a complete, formatted inventory of all vehicles added so far

The application is built with **Java Swing** and demonstrates key object‑oriented principles: interfaces, polymorphism, encapsulation, and clean separation of concerns.

---

## Features

- **Tabbed interface** – one tab per vehicle type for adding, plus a display tab.
- **Car** – make, model, year, number of doors (2–5), fuel type (Petrol/Diesel/Electric).
- **Motorcycle** – make, model, year, number of wheels (2 or 3), type (Sport/Cruiser/Off‑Road).
- **Truck** – make, model, year, cargo capacity (positive tons), transmission (Manual/Automatic).
- **Form validation** – ensures all inputs are correct (non‑empty, valid ranges, numeric types).
- **Polymorphic display** – all vehicles stored in a single `List<Vehicle>` and displayed via overridden `toString()`.
- **One‑click refresh** – inventory display updates instantly.
- **Success/error dialogs** – clear feedback for each action.

---

## Project Structure

All files are in the same package (default package). The structure follows Model‑View separation:

| File                    | Purpose                                                                 |
|-------------------------|-------------------------------------------------------------------------|
| `CarRentalSystemGUI.java` | Main JFrame – builds the GUI, handles events, manages the vehicle list |
| `Vehicle.java`            | Base interface – `getMake()`, `setMake()`, `getModel()`, `setModel()`, `getYear()`, `setYear()` |
| `CarVehicle.java`         | Car‑specific interface – doors, fuel type                              |
| `MotorVehicle.java`       | Motorcycle‑specific interface – wheels, type                           |
| `TruckVehicle.java`       | Truck‑specific interface – cargo capacity, transmission                |
| `Car.java`                | Implements `Vehicle` and `CarVehicle`                                  |
| `Motorcycle.java`         | Implements `Vehicle` and `MotorVehicle`                                |
| `Truck.java`              | Implements `Vehicle` and `TruckVehicle`                                |

No external libraries – only standard Java (Swing, AWT, `java.time` for year validation).

---

## Requirements

- **Java** – JDK 8 or higher (uses `java.time.Year` and Swing)
- **No additional dependencies**

---

## Compilation and Execution

### Compile all Java files

Make sure all `.java` files are in the same directory, then run:

```bash
javac *.java
```

### Run the application

```bash
java CarRentalSystemGUI
```

The main window will appear.

> **Note:** The GUI is started on the Event Dispatch Thread (EDT) using `SwingUtilities.invokeLater()`.

---

## Usage Guide

When the application starts, you see a window with four tabs:

- **Add Car**
- **Add Motorcycle**
- **Add Truck**
- **Display All Vehicles**

### Adding a Car

1. Go to the **Add Car** tab.
2. Enter:
   - **Make** (e.g., Toyota)
   - **Model** (e.g., Camry)
   - **Year** (between 1885 and current year)
3. Select from dropdowns:
   - **Number of Doors** (2, 3, 4, or 5)
   - **Fuel Type** (Petrol, Diesel, Electric)
4. Click **Add Car**.
5. A success dialog appears, and the form is cleared.

### Adding a Motorcycle

1. Go to the **Add Motorcycle** tab.
2. Enter make, model, and year.
3. Select **Number of Wheels** (2 or 3) and **Motorcycle Type** (Sport, Cruiser, Off‑Road).
4. Click **Add Motorcycle**.

### Adding a Truck

1. Go to the **Add Truck** tab.
2. Enter make, model, year, and **Cargo Capacity** (a positive number, e.g., `2.5` for 2.5 tons).
3. Select **Transmission Type** (Manual or Automatic).
4. Click **Add Truck**.

### Displaying All Vehicles

- Switch to the **Display All Vehicles** tab.
- Click **Refresh Display** to see a formatted list of every vehicle added so far.
- The display uses a monospaced font and separates each vehicle with dashed lines.

If no vehicles have been added, the display area shows `"No vehicles in inventory yet."`.

---

## Input Validation

The system validates all user input before creating a vehicle. Invalid entries trigger an error dialog and prevent addition.

| Field               | Validation Rule                                                                  |
|---------------------|----------------------------------------------------------------------------------|
| Make / Model        | Cannot be empty (trimmed whitespace)                                             |
| Year                | Integer between 1885 (first motorcycle) and the current year                     |
| Number of doors     | 2, 3, 4, or 5 (dropdown selection – always valid)                                |
| Fuel type           | One of Petrol, Diesel, Electric (dropdown)                                       |
| Number of wheels    | 2 or 3 (dropdown)                                                                |
| Motorcycle type     | Sport, Cruiser, Off‑Road (dropdown)                                              |
| Cargo capacity      | Positive double (e.g., `1.5`). Non‑numeric or ≤0 triggers an error.              |
| Transmission type   | Manual or Automatic (dropdown)                                                   |

All validation is handled in the event handlers (`addCar()`, `addMotorcycle()`, `addTruck()`) using helper methods (`validateNonEmpty`, `validateYear`, `validateCargoCapacity`).

---

## Object-Oriented Design

The system cleanly applies several OOP concepts:

- **Interfaces** – `Vehicle` defines the core contract; `CarVehicle`, `MotorVehicle`, and `TruckVehicle` extend behaviour for specific types.
- **Polymorphism** – The inventory is stored as `List<Vehicle>`. When displaying, the correct `toString()` method is called based on the actual object type (Car, Motorcycle, or Truck).
- **Encapsulation** – All fields are `private` with public getters/setters as required by the interfaces.
- **Separation of concerns** – GUI logic is isolated in `CarRentalSystemGUI`; vehicle models are independent.

This design makes it easy to add new vehicle types without modifying existing model classes.

---

## Screenshots (Conceptual)

The interface consists of:

- **Add Car tab** – 5 labelled fields and dropdowns, plus an "Add Car" button.
<img width="911" height="735" alt="Screenshot 2026-05-28 215955" src="https://github.com/user-attachments/assets/9cb0c718-6428-4953-96b7-78afd3f5453d" />

- **Add Motorcycle tab** – similar layout.
<img width="909" height="736" alt="Screenshot 2026-05-28 220007" src="https://github.com/user-attachments/assets/fe676fed-a8ef-4a60-967f-8d2cba5d9fd5" />

- **Add Truck tab** – similar layout.
<img width="916" height="740" alt="Screenshot 2026-05-28 220016" src="https://github.com/user-attachments/assets/9bf00fd3-66e5-428b-885e-eb0cadc7ae95" />

- **Display tab** – a large scrollable text area and a "Refresh Display" button.
<img width="909" height="746" alt="Screenshot 2026-05-28 220026" src="https://github.com/user-attachments/assets/7a40c8fa-6bb5-446b-b9ce-f7436620b69d" />


All components use `GridBagLayout` for consistent alignment and padding.

---

## Extending the System

The modular, interface‑based design allows easy extension:

### Add a new vehicle type (e.g., ElectricScooter)

1. Create a new interface `ElectricScooterVehicle` (if needed) or reuse existing ones.
2. Create a class `ElectricScooter` that implements `Vehicle` and any other interfaces.
3. In `CarRentalSystemGUI`:
   - Add a new tab (e.g., `"Add Electric Scooter"`).
   - Build a panel with specific fields (battery range, weight, etc.).
   - Write an `addElectricScooter()` method with validation.
   - Add the scooter to the `vehicles` list.
4. The existing `refreshDisplay()` will automatically show the scooter because it implements `Vehicle`.

### Add persistent storage

- Modify `CarRentalSystemGUI` to save/load the `vehicles` list using Java serialization (like the Library System) or a JSON library.
- Add a "Save" and "Load" menu.

### Improve the display

- Replace the `JTextArea` with a `JTable` for a sortable, column‑based inventory view.

### Add remove or edit functionality

- Add a delete button that removes the selected vehicle from the list.
- Provide an edit form that populates fields for the selected vehicle.

---

## License

This project is created for educational purposes – demonstrating Java Swing, GUI design, input validation, and object‑oriented programming. You are free to use, modify, and distribute it for learning.
