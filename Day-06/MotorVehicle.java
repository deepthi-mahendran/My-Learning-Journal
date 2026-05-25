/**
 * The MotorVehicle interface defines motorcycle-specific attributes.
 * Implementing classes must manage wheel count and motorcycle type.
 *
 * @author Student - Developer
 * @version 1.0
 */
public interface MotorVehicle {
    
    /**
     * Sets the number of wheels for the motorcycle.
     * @param wheels typically 2 or 3
     */
    void setNumWheels(int wheels);
    
    /**
     * Retrieves the number of wheels.
     * @return the current wheel count
     */
    int getNumWheels();
    
    /**
     * Sets the type of motorcycle.
     * @param type must be one of: "sport", "cruiser", "off-road"
     */
    void setMotorcycleType(String type);
    
    /**
     * Retrieves the motorcycle type.
     * @return the motorcycle classification
     */
    String getMotorcycleType();
}

