import java.util.Scanner;
import java.util.ArrayList;

/**
 * CarRentalSystem is the main driver class that provides an interactive
 * console-based interface for managing vehicles in the rental agency.
 * 
 * Features:
 * - Create Car, Motorcycle, and Truck objects
 * - Capture all required vehicle attributes
 * - Display details of all created vehicles
 * - Robust input validation and exception handling
 *
 * @author Student - Developer
 * @version 1.0
 */
public class CarRentalSystem {
    
    private static Scanner scanner;
    private static ArrayList<Vehicle> vehicles;
    
    /**
     * The main entry point for the Car Rental Management System.
     * Demonstrates polymorphism by storing various vehicle types in a common
     * Vehicle array/list.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Initialize resources using try-with-resources for automatic cleanup
        try (Scanner sc = new Scanner(System.in)) {
            scanner = sc;
            vehicles = new ArrayList<>();
            
            System.out.println("=".repeat(60));
            System.out.println("     Welcome to the Car Rental Agency System");
            System.out.println("=".repeat(60));
            
            boolean running = true;
            while (running) {
                displayMainMenu();
                int choice = readIntInput("Enter your choice (1-4): ");
                
                switch (choice) {
                    case 1:
                        createCar();
                        break;
                    case 2:
                        createMotorcycle();
                        break;
                    case 3:
                        createTruck();
                        break;
                    case 4:
                        displayAllVehicles();
                        break;
                    case 5:
                        running = false;
                        System.out.println("\nThank you for using the Car Rental Agency System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select an option between 1 and 5.\n");
                }
            }
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Displays the main menu options to the user.
     */
    private static void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add a new Car");
        System.out.println("2. Add a new Motorcycle");
        System.out.println("3. Add a new Truck");
        System.out.println("4. Display all vehicles");
        System.out.println("5. Exit");
    }
    
    /**
     * Creates a Car object by prompting the user for input with validation.
     * Demonstrates polymorphism by storing the car in the vehicles list.
     */
    private static void createCar() {
        System.out.println("\n--- Add a New Car ---");
        
        String make = readStringInput("Enter car make: ");
        String model = readStringInput("Enter car model: ");
        int year = readYearInput("Enter manufacturing year: ");
        
        Car car = new Car(make, model, year);
        
        int doors = readDoorsInput("Enter number of doors (2-5): ");
        car.setNumDoors(doors);
        
        String fuelType = readFuelTypeInput();
        car.setFuelType(fuelType);
        
        vehicles.add(car);
        System.out.println("\nCar added successfully!\n");
    }
    
    /**
     * Creates a Motorcycle object with validated user input.
     * Demonstrates polymorphism by storing the motorcycle in the vehicles list.
     */
    private static void createMotorcycle() {
        System.out.println("\n--- Add a New Motorcycle ---");
        
        String make = readStringInput("Enter motorcycle make: ");
        String model = readStringInput("Enter motorcycle model: ");
        int year = readYearInput("Enter manufacturing year: ");
        
        Motorcycle bike = new Motorcycle(make, model, year);
        
        int wheels = readWheelsInput("Enter number of wheels (typically 2 or 3): ");
        bike.setNumWheels(wheels);
        
        String type = readMotorcycleTypeInput();
        bike.setMotorcycleType(type);
        
        vehicles.add(bike);
        System.out.println("\nMotorcycle added successfully!\n");
    }
    
    /**
     * Creates a Truck object with validated user input.
     * Demonstrates polymorphism by storing the truck in the vehicles list.
     */
    private static void createTruck() {
        System.out.println("\n--- Add a New Truck ---");
        
        String make = readStringInput("Enter truck make: ");
        String model = readStringInput("Enter truck model: ");
        int year = readYearInput("Enter manufacturing year: ");
        
        Truck truck = new Truck(make, model, year);
        
        double capacity = readCargoInput("Enter cargo capacity (in tons): ");
        truck.setCargoCapacity(capacity);
        
        String transmission = readTransmissionInput();
        truck.setTransmissionType(transmission);
        
        vehicles.add(truck);
        System.out.println("\nTruck added successfully!\n");
    }
    
    /**
     * Displays all vehicles currently stored in the system.
     * Leverages polymorphism by calling toString() on Vehicle references.
     */
    private static void displayAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles have been added yet. Please add vehicles first.\n");
            return;
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("     Complete Vehicle Inventory");
        System.out.println("=".repeat(60));
        
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.println("\nVehicle #" + (i + 1));
            System.out.println(vehicles.get(i).toString());
            System.out.println("-".repeat(40));
        }
    }
    
    // ================== Input Validation Methods ==================
    
    /**
     * Reads and validates a non-empty string input.
     * @param prompt the message to display to the user
     * @return the validated non-empty string
     */
    private static String readStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }
    
    /**
     * Reads and validates an integer input with exception handling.
     * @param prompt the message to display to the user
     * @return the validated integer value
     */
    private static int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    /**
     * Reads and validates a manufacturing year input.
     * Year must be between 1885 (first motorcycle) and current year.
     * @param prompt the message to display to the user
     * @return the validated year
     */
    private static int readYearInput(String prompt) {
        while (true) {
            int year = readIntInput(prompt);
            int currentYear = java.time.Year.now().getValue();
            if (year >= 1885 && year <= currentYear) {
                return year;
            }
            System.out.println("Invalid year. Please enter a year between 1885 and " + currentYear);
        }
    }
    
    /**
     * Reads and validates the number of doors for a car.
     * @param prompt the message to display to the user
     * @return validated door count (2, 3, 4, or 5)
     */
    private static int readDoorsInput(String prompt) {
        while (true) {
            int doors = readIntInput(prompt);
            if (doors >= 2 && doors <= 5) {
                return doors;
            }
            System.out.println("Invalid number of doors. Please enter a value between 2 and 5.");
        }
    }
    
    /**
     * Reads and validates the fuel type selection.
     * @return the selected fuel type ("petrol", "diesel", or "electric")
     */
    private static String readFuelTypeInput() {
        while (true) {
            System.out.println("\nSelect Fuel Type:");
            System.out.println("1. Petrol");
            System.out.println("2. Diesel");
            System.out.println("3. Electric");
            int choice = readIntInput("Enter your choice (1-3): ");
            
            switch (choice) {
                case 1: return "petrol";
                case 2: return "diesel";
                case 3: return "electric";
                default: System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }
    
    /**
     * Reads and validates the number of wheels for a motorcycle.
     * @param prompt the message to display to the user
     * @return validated wheel count (2 or 3)
     */
    private static int readWheelsInput(String prompt) {
        while (true) {
            int wheels = readIntInput(prompt);
            if (wheels == 2 || wheels == 3) {
                return wheels;
            }
            System.out.println("Invalid number of wheels. Motorcycles typically have 2 or 3 wheels.");
        }
    }
    
    /**
     * Reads and validates the motorcycle type selection.
     * @return the selected type ("sport", "cruiser", or "off-road")
     */
    private static String readMotorcycleTypeInput() {
        while (true) {
            System.out.println("\nSelect Motorcycle Type:");
            System.out.println("1. Sport");
            System.out.println("2. Cruiser");
            System.out.println("3. Off-Road");
            int choice = readIntInput("Enter your choice (1-3): ");
            
            switch (choice) {
                case 1: return "sport";
                case 2: return "cruiser";
                case 3: return "off-road";
                default: System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }
    
    /**
     * Reads and validates cargo capacity input.
     * @param prompt the message to display to the user
     * @return the validated cargo capacity (positive double)
     */
    private static double readCargoInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double capacity = Double.parseDouble(scanner.nextLine().trim());
                if (capacity > 0) {
                    return capacity;
                }
                System.out.println("Cargo capacity must be positive. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }
    
    /**
     * Reads and validates the transmission type selection.
     * @return the selected type ("manual" or "automatic")
     */
    private static String readTransmissionInput() {
        while (true) {
            System.out.println("\nSelect Transmission Type:");
            System.out.println("1. Manual");
            System.out.println("2. Automatic");
            int choice = readIntInput("Enter your choice (1-2): ");
            
            switch (choice) {
                case 1: return "manual";
                case 2: return "automatic";
                default: System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }
    }
}