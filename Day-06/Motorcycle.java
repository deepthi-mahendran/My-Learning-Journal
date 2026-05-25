/**
 * The Motorcycle class represents a motorcycle implementing both Vehicle and
 * MotorVehicle interfaces. It manages make, model, year, wheel count, and
 * motorcycle type information.
 *
 * @author Student - Developer
 * @version 1.0
 */
public class Motorcycle implements Vehicle, MotorVehicle {
    
    // Core vehicle attributes
    private String make;
    private String model;
    private int year;
    
    // Motorcycle-specific attributes
    private int numWheels;
    private String motorcycleType;  // sport, cruiser, off-road
    
    /**
     * Constructs a Motorcycle object with essential vehicle details.
     * @param make the manufacturer
     * @param model the motorcycle model
     * @param year the manufacturing year
     */
    public Motorcycle(String make, String model, int year) {
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
    
    // MotorVehicle interface implementation
    @Override
    public void setNumWheels(int wheels) { this.numWheels = wheels; }
    
    @Override
    public int getNumWheels() { return numWheels; }
    
    @Override
    public void setMotorcycleType(String type) { this.motorcycleType = type; }
    
    @Override
    public String getMotorcycleType() { return motorcycleType; }
    
    /**
     * Overrides toString() to provide a formatted representation of the motorcycle.
     * @return a formatted string containing all motorcycle details
     */
    @Override
    public String toString() {
        return String.format(
            "Motorcycle Details:\n" +
            "  Make: %s\n" +
            "  Model: %s\n" +
            "  Year: %d\n" +
            "  Number of Wheels: %d\n" +
            "  Motorcycle Type: %s\n",
            make, model, year, numWheels, motorcycleType
        );
    }
}