# Car Rental Agency System

A console-based Java application that demonstrates object-oriented programming principles, including interfaces, polymorphism, and inheritance. The system allows users to manage a fleet of vehicles (cars, motorcycles, and trucks) with type-specific attributes and robust input validation.

## Overview

The **Car Rental Agency System** provides an interactive menu for rental agency staff to:

- Add new vehicles (Cars, Motorcycles, Trucks)
- Capture all relevant attributes for each vehicle type
- Display a complete inventory of all vehicles

The project illustrates key OOP concepts:

- **Interfaces**: `Vehicle` (core contract), plus `CarVehicle`, `MotorVehicle`, `TruckVehicle` for specialized behaviors.
- **Polymorphism**: All vehicle types are stored in a single `ArrayList<Vehicle>` and displayed via overridden `toString()` methods.
- **Encapsulation**: Private fields with public getters/setters.
- **Exception handling & input validation**: Robust user input parsing and range checking.

## Features

- **Add a Car**  
  - Make, Model, Year  
  - Number of doors (2–5)  
  - Fuel type (Petrol, Diesel, Electric)

- **Add a Motorcycle**  
  - Make, Model, Year  
  - Number of wheels (2 or 3)  
  - Motorcycle type (Sport, Cruiser, Off-Road)

- **Add a Truck**  
  - Make, Model, Year  
  - Cargo capacity in tons (positive number)  
  - Transmission type (Manual, Automatic)

- **Display All Vehicles**  
  - Lists every vehicle added, with formatted details for each type.

- **Input Validation**  
  - Non‑empty strings  
  - Numeric ranges (year, doors, wheels, cargo capacity)  
  - Menu choices and enumerated type selections

## Project Structure

```
.
├── CarRentalSystem.java      # Main driver class (console menu)
├── Vehicle.java              # Base interface (make, model, year)
├── CarVehicle.java           # Car-specific interface
├── MotorVehicle.java         # Motorcycle-specific interface
├── TruckVehicle.java         # Truck-specific interface
├── Car.java                  # Implements Vehicle & CarVehicle
├── Motorcycle.java           # Implements Vehicle & MotorVehicle
├── Truck.java                # Implements Vehicle & TruckVehicle
└── README.md                 # This file
```

## Class Diagram

```
          ┌─────────────────┐
          │    <<interface>> │
          │     Vehicle      │
          │ + getMake()      │
          │ + getModel()     │
          │ + getYear()      │
          └────────┬─────────┘
                   │
     ┌─────────────┼─────────────────────┐
     │             │                     │
┌────▼────┐   ┌─────▼──────┐        ┌─────▼──────┐
│  Car    │   │ Motorcycle │        │   Truck    │
│ implements│  │ implements │        │ implements │
│CarVehicle│  │MotorVehicle│        │TruckVehicle│
└─────────┘   └────────────┘        └────────────┘
```

**Interfaces details:**

- `CarVehicle` → `setNumDoors()`, `getNumDoors()`, `setFuelType()`, `getFuelType()`
- `MotorVehicle` → `setNumWheels()`, `getNumWheels()`, `setMotorcycleType()`, `getMotorcycleType()`
- `TruckVehicle` → `setCargoCapacity()`, `getCargoCapacity()`, `setTransmissionType()`, `getTransmissionType()`

## Requirements

- **Java**: JDK 11 or later (uses `java.time.Year` and `String.repeat()`)
- **No external libraries** – pure standard Java

## Compilation and Execution

### Compile all Java files:

```bash
javac *.java
```

### Run the application:

```bash
java CarRentalSystem
```

> **Note:** All files must be in the same directory because default (package‑less) visibility is used.

## Usage Guide

When you run the program, the main menu appears:

```
============================================================
     Welcome to the Car Rental Agency System
============================================================

--- Main Menu ---
1. Add a new Car
2. Add a new Motorcycle
3. Add a new Truck
4. Display all vehicles
5. Exit
Enter your choice (1-4): 
```

Follow the prompts to enter vehicle details. The system will validate inputs and re‑prompt if data is invalid. After adding a vehicle, a success message appears and you return to the main menu.

Select **4** at any time to view all vehicles added so far. The inventory shows each vehicle’s type‑specific fields.

Select **5** to exit.

## Input Validation

The system rigorously validates all user inputs:

| Field               | Validation Rules                                                                 |
|---------------------|----------------------------------------------------------------------------------|
| Make / Model        | Non‑empty string (leading/trailing spaces trimmed)                               |
| Year                | Integer between 1885 (first motorcycle) and current year                         |
| Number of doors     | Integer from 2 to 5                                                              |
| Fuel type           | One of: Petrol, Diesel, Electric (by menu selection)                            |
| Number of wheels    | 2 or 3                                                                           |
| Motorcycle type     | Sport, Cruiser, Off‑Road (by menu)                                               |
| Cargo capacity      | Positive double (e.g., 1.5, 10.0)                                                |
| Transmission type   | Manual or Automatic (by menu)                                                    |
| Menu choices        | Within the allowed numeric range                                                 |

All invalid entries trigger an error message and the prompt repeats until correct input is provided.

## Example Session

```
--- Add a New Car ---
Enter car make: Toyota
Enter car model: Camry
Enter manufacturing year: 2022

Select Fuel Type:
1. Petrol
2. Diesel
3. Electric
Enter your choice (1-3): 1
Enter number of doors (2-5): 4

Car added successfully!

--- Main Menu ---
1. Add a new Car
2. Add a new Motorcycle
3. Add a new Truck
4. Display all vehicles
5. Exit
Enter your choice (1-4): 4

============================================================
     Complete Vehicle Inventory
============================================================

Vehicle #1
Car Details:
  Make: Toyota
  Model: Camry
  Year: 2022
  Number of Doors: 4
  Fuel Type: petrol
----------------------------------------
```

## Extending the System

The interface‑based design makes it easy to add new vehicle types:

1. Create a new interface (e.g., `ElectricVehicle`) if needed, or reuse existing ones.
2. Create a new class that implements `Vehicle` and any additional interfaces.
3. Add a corresponding creation method in `CarRentalSystem` with its own input validation.
4. Update the main menu and switch statement.

For example, to add an `ElectricScooter` class, implement `Vehicle` and optionally `MotorVehicle`, then follow the same pattern as `Motorcycle`.

## License

This project is created for educational purposes as part of an object‑oriented programming assignment. Feel free to use and modify it for learning.

---
