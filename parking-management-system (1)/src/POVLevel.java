import java.util.ArrayList;
import java.util.List;

abstract class Level {

    private final int levelNumber;
    private final String levelLabel;                  // "POV" or "PUV"
    private final List<ParkingSpot> parkingSpots = new ArrayList<>();

    protected Level(int levelNumber, String levelLabel) {
        if (levelNumber < 1) {
            throw new IllegalArgumentException("Level number must be 1 or higher.");
        }
        if (levelLabel == null || levelLabel.trim().isEmpty()) {
            throw new IllegalArgumentException("Level label is required.");
        }
        this.levelNumber = levelNumber;
        this.levelLabel = levelLabel.trim();
    }

    // creates a spot like "POV-C1", adds it to this level, and returns it
    public ParkingSpot addParkingSpot(VehicleSize size) {
        if (size == null) {
            throw new IllegalArgumentException("Spot size is required.");
        }
        ParkingSpot spot = new ParkingSpot(generateSpotNumber(size), size);
        parkingSpots.add(spot);
        return spot;
    }

    // first unused number for this size, so duplicates are impossible
    private String generateSpotNumber(VehicleSize size) {
        int n = 1;
        String number = levelLabel + "-" + size.getCode() + n;
        while (findParkingSpot(number) != null) {
            n++;
            number = levelLabel + "-" + size.getCode() + n;
        }
        return number;
    }

    // returns false if no such spot; refuses to remove an occupied one
    public boolean removeParkingSpot(String spotNumber) {
        ParkingSpot spot = findParkingSpot(spotNumber);
        if (spot == null) {
            return false;
        }
        if (!spot.isAvailable()) {
            throw new IllegalStateException("Cannot remove occupied spot " + spotNumber);
        }
        return parkingSpots.remove(spot);
    }

    public ParkingSpot findParkingSpot(String spotNumber) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotNumber().equalsIgnoreCase(spotNumber)) {
                return spot;
            }
        }
        return null;
    }

    public List<ParkingSpot> getParkingSpots() {
        return new ArrayList<>(parkingSpots);         // copy, not the real list
    }

    public List<ParkingSpot> getAvailableSpots() {
        List<ParkingSpot> available = new ArrayList<>();
        for (ParkingSpot spot : parkingSpots) {
            if (spot.isAvailable()) {
                available.add(spot);
            }
        }
        return available;
    }

    public List<ParkingSpot> getOccupiedSpots() {
        List<ParkingSpot> occupied = new ArrayList<>();
        for (ParkingSpot spot : parkingSpots) {
            if (!spot.isAvailable()) {
                occupied.add(spot);
            }
        }
        return occupied;
    }

    public int getAvailableSpotCount() { return getAvailableSpots().size(); }
    public int getTotalSpotCount()     { return parkingSpots.size(); }

    // a level with no free spot is full (this includes a level with no spots yet)
    public boolean isFull() {
        return getAvailableSpotCount() == 0;
    }

    // Each child class decides how a spot is found for a vehicle.
    public abstract ParkingSpot findAvailableSpotFor(Vehicle vehicle);

    // finds a spot, parks the vehicle, and returns the spot used
    public ParkingSpot parkVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle is required.");
        }
        ParkingSpot spot = findAvailableSpotFor(vehicle);   // POV or PUV logic runs here
        if (spot == null) {
            if (isFull()) {
                throw new IllegalStateException(levelLabel + " level is full");
            }
            throw new IllegalStateException("No available spot on " + levelLabel
                    + " level fits this vehicle.");
        }
        spot.parkVehicle(vehicle);
        return spot;
    }

    public int getLevelNumber()    { return levelNumber; }
    public String getLevelLabel()  { return levelLabel; }
}

// Level for privately owned vehicles (cars and motorcycles).
// It accepts a vehicle by checking: is the level full? is there a free spot that fits?
public class POVLevel extends Level {

    public POVLevel(int levelNumber) {
        super(levelNumber, "POV");
    }

    // POV level only has motorcycle and car spots
    @Override
    public ParkingSpot addParkingSpot(VehicleSize size) {
        if (size != VehicleSize.MOTORCYCLE && size != VehicleSize.CAR) {
            throw new IllegalArgumentException(getLevelLabel()
                    + " level only accepts MOTORCYCLE or CAR spots.");
        }
        return super.addParkingSpot(size);
    }

    // returns a free spot that fits, or null (level full / nothing fits)
    @Override
    public ParkingSpot findAvailableSpotFor(Vehicle vehicle) {
        if (isFull()) {
            return null;
        }
        for (ParkingSpot spot : getAvailableSpots()) {
            if (spot.canFitVehicle(vehicle)) {
                return spot;
            }
        }
        return null;
    }
}