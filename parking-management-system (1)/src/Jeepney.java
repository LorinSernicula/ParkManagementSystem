public class Jeepney extends Vehicle {
    // calls parent Vehicle to initialize plate number
    public Jeepney(String plateNumber) {
        super(plateNumber);
    }
    // identifies required parking space size
    @Override public VehicleSize getSize() {
        return VehicleSize.JEEPNEY;
    }
    // jeepneys  = public utility vehicles 
    @Override public boolean isPublicUtility() {
        return true;
    }
    // provides desc. when Jeepney is printed, displays details (plate #, size, type)
    @Override public String toString() {
        return "Jeepney [Plate Number " + getPlateNumber() + ", Size: " + getSize() + ", Type: Public Utility Vehicle]";
    }
}