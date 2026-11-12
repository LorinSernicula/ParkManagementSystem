public class ParkingSpot {

    private final String spotNumber;
    private final VehicleSize spotSize;
    private Vehicle parkedVehicle;      // null = available

    public ParkingSpot(String spotNumber, VehicleSize spotSize) {
        if (spotNumber == null || spotNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Spot number is required.");
        }
        if (spotSize == null) {
            throw new IllegalArgumentException("Spot size is required.");
        }
        this.spotNumber = spotNumber.trim();
        this.spotSize = spotSize;
    }

    public boolean isAvailable() {
        return parkedVehicle == null;
    }

    public boolean canFitVehicle(Vehicle vehicle) {
        return vehicle != null && vehicle.getSize().canFitIn(spotSize);
    }

    public void parkVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle is required.");
        }
        if (!isAvailable()) {
            throw new IllegalStateException("Spot " + spotNumber + " is already occupied.");
        }
        if (!canFitVehicle(vehicle)) {
            throw new IllegalArgumentException("Vehicle is too big for spot " + spotNumber + ".");
        }
        parkedVehicle = vehicle;
    }

    public Vehicle removeVehicle() {
        if (isAvailable()) {
            throw new IllegalStateException("Spot " + spotNumber + " is already empty.");
        }
        Vehicle removed = parkedVehicle;
        parkedVehicle = null;
        return removed;
    }

    public String getSpotNumber()      { return spotNumber; }
    public VehicleSize getSpotSize()   { return spotSize; }
    public Vehicle getParkedVehicle()  { return parkedVehicle; }
}