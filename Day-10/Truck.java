public class Truck implements Vehicle, TruckVehicle {
    private String make;
    private String model;
    private int year;
    private double cargoCapacity;
    private String transmissionType;

    public Truck(String make, String model, int year, double cargoCapacity, String transmissionType) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.cargoCapacity = cargoCapacity;
        this.transmissionType = transmissionType;
    }

    @Override
    public String getMake() { return make; }
    @Override
    public void setMake(String make) { this.make = make; }
    @Override
    public String getModel() { return model; }
    @Override
    public void setModel(String model) { this.model = model; }
    @Override
    public int getYear() { return year; }
    @Override
    public void setYear(int year) { this.year = year; }
    @Override
    public double getCargoCapacity() { return cargoCapacity; }
    @Override
    public void setCargoCapacity(double capacity) { this.cargoCapacity = capacity; }
    @Override
    public String getTransmissionType() { return transmissionType; }
    @Override
    public void setTransmissionType(String transmission) { this.transmissionType = transmission; }

    @Override
    public String toString() {
        return String.format("Truck Details:\n  Make: %s\n  Model: %s\n  Year: %d\n  Cargo Capacity: %.1f tons\n  Transmission: %s",
                make, model, year, cargoCapacity, transmissionType);
    }
}
