import java.util.HashMap;
import java.util.Map;

public class PUVLevel extends Level {

    // plate number -> the spot reserved for that registered PUV
    private final Map<String, ParkingSpot> assignedSpots = new HashMap<>();

    public PUVLevel(int levelNumber) {
        super(levelNumber, "PUV");
    }

    // PUV level only has jeepney and bus spots
    @Override
    public ParkingSpot addParkingSpot(VehicleSize size) {
        if (size != VehicleSize.JEEPNEY && size != VehicleSize.BUS) {
            throw new IllegalArgumentException(getLevelLabel()
                    + " level only accepts JEEPNEY or BUS spots.");
        }
        return super.addParkingSpot(size);
    }

    public void registerVehicle(Vehicle vehicle, ParkingSpot spot) {
        if (vehicle == null || spot == null) {
            throw new IllegalArgumentException("Vehicle and spot are required.");
        }
        if (!vehicle.isPublicUtility()) {
            throw new IllegalArgumentException("Only public utility vehicles can register on "
                    + getLevelLabel() + " level.");
        }
        if (findParkingSpot(spot.getSpotNumber()) != spot) {   // same object, not just same number
            throw new IllegalArgumentException("Spot does not belong to this level.");
        }
        if (!spot.canFitVehicle(vehicle)) {
            throw new IllegalArgumentException("Spot " + spot.getSpotNumber()
                    + " is too small for this vehicle.");
        }
        if (assignedSpots.containsKey(vehicle.getPlateNumber())) {
            throw new IllegalArgumentException("Vehicle " + vehicle.getPlateNumber()
                    + " is already registered.");
        }
        if (assignedSpots.containsValue(spot)) {
            throw new IllegalArgumentException("Spot " + spot.getSpotNumber()
                    + " is already assigned.");
        }
        assignedSpots.put(vehicle.getPlateNumber(), spot);
    }

    // returns the vehicle's reserved spot if it is registered and not already parked
    @Override
    public ParkingSpot findAvailableSpotFor(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        ParkingSpot spot = assignedSpots.get(vehicle.getPlateNumber());
        if (spot == null) {
            throw new IllegalStateException("Vehicle " + vehicle.getPlateNumber()
                    + " is not registered on " + getLevelLabel() + " level.");
        }
        if (!spot.isAvailable()) {
            throw new IllegalStateException("Vehicle " + vehicle.getPlateNumber()
                    + " is already parked.");
        }
        return spot;
    }
}