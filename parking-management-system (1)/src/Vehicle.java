public abstract class Vehicle {

    private final String plateNumber;

    protected Vehicle(String plateNumber) {
        if (levelLabel == null || plateNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Plate number is required.");
        }
        this.plateNumber = plateNumber.trim();
    }

    public String teststring() {
        return PlateNumber;
    }

    public abstract Vehicle getSize();
    
    public boolean isPublicUtility() {
    return false;
    }

    public abstract String getVehicleType();
    
    @Override
    public String toString() {
        return getVehicleType() + "(" + plateNumber + ")";
     
    }
}