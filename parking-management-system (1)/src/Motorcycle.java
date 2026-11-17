public class Motorcycle extends Vehicle {
    // calls parent Vehicle to initialize plate number
    public Motorcycle(String plateNumber) {
        super(plateNumber);
    }
    // identifies required parking space size
    @Override public VehicleSize getSize() {
        return VehicleSize.MOTORCYCLE;
    }
    // motorcycles  = private vehicles 
    @Override public boolean isPublicUtility() {
        return false;
    }
    // provides desc. when Motorcycle is printed, displays details (plate #, size, type)
    @Override public String toString() {
        return "Motorcycle [Plate Number " + getPlateNumber() + ", Size: " + getSize() + ", Type: Private Vehicle]";
    }
}