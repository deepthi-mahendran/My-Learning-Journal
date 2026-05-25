/**
 * The Car class represents a car vehicle implementing both Vehicle and
 * CarVehicle interfaces. It encapsulates make, model, year, number of doors,
 * and fuel type information.
 *
 * @author Student - Developer
 * @version 1.0
 */
public class Car implements Vehicle, CarVehicle {
    
    // Core vehicle attributes
    private String make;
    private String model;
    private int year;
    
    // Car-specific attributes
    private int numDoors;
    private String fuelType;  // petrol, diesel, or electric
    
    /**
     * Constructs a Car object with essential vehicle details.
     * @param make the manufacturer (e.g., "Toyota")
     * @param model the car model
     * @param year the manufacturing year
     */
    public Car(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }
    
    // Vehicle interface implementation
    @Override
    public String getMake() { return make; }
    
    @Override
    public String getModel() { return model; }
    
    @Override
    public int getYear() { return year; }
    
    // CarVehicle interface implementation
    @Override
    public void setNumDoors(int doors) { this.numDoors = doors; }
    
    @Override
    public int getNumDoors() { return numDoors; }
    
    @Override
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    
    @Override
    public String getFuelType() { return fuelType; }
    
    /**
     * Overrides toString() to provide a formatted representation of the car.
     * @return a formatted string containing all car details
     */
    @Override
    public String toString() {
        return String.format(
            "Car Details:\n" +
            "  Make: %s\n" +
            "  Model: %s\n" +
            "  Year: %d\n" +
            "  Number of Doors: %d\n" +
            "  Fuel Type: %s\n",
            make, model, year, numDoors, fuelType
        );
    }
}