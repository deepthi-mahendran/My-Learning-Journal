public class Motorcycle implements Vehicle, MotorVehicle {
    private String make;
    private String model;
    private int year;
    private int numWheels;
    private String motorcycleType;

    public Motorcycle(String make, String model, int year, int numWheels, String motorcycleType) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.numWheels = numWheels;
        this.motorcycleType = motorcycleType;
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
    public int getNumWheels() { return numWheels; }
    @Override
    public void setNumWheels(int wheels) { this.numWheels = wheels; }
    @Override
    public String getMotorcycleType() { return motorcycleType; }
    @Override
    public void setMotorcycleType(String type) { this.motorcycleType = type; }

    @Override
    public String toString() {
        return String.format("Motorcycle Details:\n  Make: %s\n  Model: %s\n  Year: %d\n  Number of Wheels: %d\n  Type: %s",
                make, model, year, numWheels, motorcycleType);
    }
}