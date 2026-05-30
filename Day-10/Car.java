public class Car implements Vehicle, CarVehicle {
    private String make;
    private String model;
    private int year;
    private int numDoors;
    private String fuelType;

    public Car(String make, String model, int year, int numDoors, String fuelType) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.numDoors = numDoors;
        this.fuelType = fuelType;
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
    public int getNumDoors() { return numDoors; }
    @Override
    public void setNumDoors(int doors) { this.numDoors = doors; }
    @Override
    public String getFuelType() { return fuelType; }
    @Override
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    @Override
    public String toString() {
        return String.format("Car Details:\n  Make: %s\n  Model: %s\n  Year: %d\n  Number of Doors: %d\n  Fuel Type: %s",
                make, model, year, numDoors, fuelType);
    }
}