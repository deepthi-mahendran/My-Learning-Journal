/**
 * The Vehicle interface defines the core contract for all vehicle types.
 * Implementing classes must provide access to the vehicle's make, model,
 * and manufacturing year.
 *
 * @author Student - Developer
 * @version 1.0
 */
public interface Vehicle {
    
    /**
     * Retrieves the manufacturer of the vehicle.
     * @return the vehicle's make (e.g., "Toyota", "Harley-Davidson")
     */
    String getMake();
    
    /**
     * Retrieves the specific model name of the vehicle.
     * @return the vehicle's model
     */
    String getModel();
    
    /**
     * Retrieves the manufacturing year of the vehicle.
     * @return the vehicle's year of manufacture
     */
    int getYear();
}
