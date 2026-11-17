public abstract class Vehicle {

    private final String plateNumber;

    protected Vehicle(String plateNumber) {
        if (plateNumber == null || plateNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Plate number is required.");
        }
        this.plateNumber = plateNumber.trim();
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public abstract VehicleSize getSize();
    
    public boolean isPublicUtility() {
    return false;
    }

    public String getVehicleType() {
        return getClass().getSimpleName();
}
    @Override
    public String toString() {
        return getVehicleType() + "(" + plateNumber + ")";
     
    }
}
