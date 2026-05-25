/**
 * The Truck class represents a truck implementing both Vehicle and
 * TruckVehicle interfaces. It manages make, model, year, cargo capacity,
 * and transmission type.
 *
 * @author Student - Developer
 * @version 1.0
 */
public class Truck implements Vehicle, TruckVehicle {
    
    // Core vehicle attributes
    private String make;
    private String model;
    private int year;
    
    // Truck-specific attributes
    private double cargoCapacity;  // in tons
    private String transmissionType;  // manual or automatic
    
    /**
     * Constructs a Truck object with essential vehicle details.
     * @param make the manufacturer
     * @param model the truck model
     * @param year the manufacturing year
     */
    public Truck(String make, String model, int year) {
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
    
    // TruckVehicle interface implementation
    @Override
    public void setCargoCapacity(double capacity) { this.cargoCapacity = capacity; }
    
    @Override
    public double getCargoCapacity() { return cargoCapacity; }
    
    @Override
    public void setTransmissionType(String type) { this.transmissionType = type; }
    
    @Override
    public String getTransmissionType() { return transmissionType; }
    
    /**
     * Overrides toString() to provide a formatted representation of the truck.
     * @return a formatted string containing all truck details
     */
    @Override
    public String toString() {
        return String.format(
            "Truck Details:\n" +
            "  Make: %s\n" +
            "  Model: %s\n" +
            "  Year: %d\n" +
            "  Cargo Capacity: %.1f tons\n" +
            "  Transmission Type: %s\n",
            make, model, year, cargoCapacity, transmissionType
        );
    }
}