/**
 * The TruckVehicle interface defines truck-specific attributes.
 * Implementing classes must manage cargo capacity and transmission type.
 *
 * @author Student - Developer
 * @version 1.0
 */
public interface TruckVehicle {
    
    /**
     * Sets the cargo capacity of the truck.
     * @param cargoCapacity capacity in tons
     */
    void setCargoCapacity(double cargoCapacity);
    
    /**
     * Retrieves the cargo capacity.
     * @return capacity in tons
     */
    double getCargoCapacity();
    
    /**
     * Sets the transmission type for the truck.
     * @param transmissionType must be "manual" or "automatic"
     */
    void setTransmissionType(String transmissionType);
    
    /**
     * Retrieves the transmission type.
     * @return "manual" or "automatic"
     */
    String getTransmissionType();
}

