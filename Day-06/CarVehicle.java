/**
 * The CarVehicle interface defines car-specific attributes and behaviors.
 * Implementing classes must manage door count and fuel type information.
 *
 * @author Student - Developer
 * @version 1.0
 */
public interface CarVehicle {
    
    /**
     * Sets the number of doors for the car.
     * @param doors number of doors (valid range: 2-5)
     */
    void setNumDoors(int doors);
    
    /**
     * Retrieves the number of doors.
     * @return the current door count
     */
    int getNumDoors();
    
    /**
     * Sets the fuel type for the car.
     * @param fuelType must be one of: "petrol", "diesel", "electric"
     */
    void setFuelType(String fuelType);
    
    /**
     * Retrieves the current fuel type.
     * @return the fuel type (petrol/diesel/electric)
     */
    String getFuelType();
}

