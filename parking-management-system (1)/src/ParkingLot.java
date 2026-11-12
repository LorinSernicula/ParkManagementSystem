import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    private final String parkingLotName;
    private final List<Level> levels = new ArrayList<>();   // holds POVLevel and PUVLevel objects

    public ParkingLot(String parkingLotName) {
        if (parkingLotName == null || parkingLotName.trim().isEmpty()) {
            throw new IllegalArgumentException("Parking lot name is required.");
        }
        this.parkingLotName = parkingLotName.trim();
    }

    public void addLevel(Level level) {
        if (level == null) {
            throw new IllegalArgumentException("Level is required.");
        }
        if (findLevel(level.getLevelNumber()) != null) {
            throw new IllegalArgumentException("Duplicate level number: " + level.getLevelNumber());
        }
        levels.add(level);
    }

    // returns false if not found; refuses to remove a level with parked vehicles
    public boolean removeLevel(int levelNumber) {
        Level level = findLevel(levelNumber);
        if (level == null) {
            return false;
        }
        if (!level.getOccupiedSpots().isEmpty()) {
            throw new IllegalStateException("Cannot remove level " + levelNumber + ": vehicles are parked.");
        }
        return levels.remove(level);
    }

    public Level findLevel(int levelNumber) {
        for (Level level : levels) {
            if (level.getLevelNumber() == levelNumber) {
                return level;
            }
        }
        return null;
    }

    public Level findLevelByLabel(String label) {
        for (Level level : levels) {
            if (level.getLevelLabel().equalsIgnoreCase(label)) {
                return level;
            }
        }
        return null;
    }

    public List<Level> getLevels() {
        return new ArrayList<>(levels);               // copy, not the real list
    }

    public int getTotalParkingSpots() {
        int total = 0;
        for (Level level : levels) {
            total += level.getTotalSpotCount();       // each level does its own counting
        }
        return total;
    }

    public int getAvailableParkingSpots() {
        int available = 0;
        for (Level level : levels) {
            available += level.getAvailableSpotCount();
        }
        return available;
    }

    public String getParkingLotName() { return parkingLotName; }

    // ================= DEMO: remove when ParkingManagementSystem is ready =================

    private static void tryPark(Level level, Vehicle v) {
        try {
            ParkingSpot spot = level.parkVehicle(v);
            System.out.println(v.getPlateNumber() + " parked at "
                    + level.getLevelLabel() + " level, spot " + spot.getSpotNumber());
        } catch (RuntimeException e) {
            System.out.println("REJECTED " + v.getPlateNumber() + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot("Demo Parking");

        PUVLevel puv = new PUVLevel(1);
        ParkingSpot j1 = puv.addParkingSpot(VehicleSize.JEEPNEY);   // PUV-J1
        ParkingSpot b1 = puv.addParkingSpot(VehicleSize.BUS);       // PUV-B1

        POVLevel pov = new POVLevel(2);
        pov.addParkingSpot(VehicleSize.CAR);                        // POV-C1
        pov.addParkingSpot(VehicleSize.CAR);                        // POV-C2
        pov.addParkingSpot(VehicleSize.MOTORCYCLE);                 // POV-M1

        lot.addLevel(puv);
        lot.addLevel(pov);

        try {
            pov.addParkingSpot(VehicleSize.BUS);                    // not allowed on POV level
        } catch (IllegalArgumentException e) {
            System.out.println("REJECTED spot: " + e.getMessage());
        }

        Jeepney jeep = new Jeepney("JEP-111");
        Bus bus = new Bus("BUS-222");
        Car car1 = new Car("CAR-111");
        Car car2 = new Car("CAR-222");
        Car car3 = new Car("CAR-333");
        Motorcycle moto = new Motorcycle("MOT-111");

        puv.registerVehicle(jeep, j1);
        puv.registerVehicle(bus, b1);

        tryPark(puv, jeep);     // registered PUV: accepted
        tryPark(puv, car1);     // car on PUV level: not registered
        tryPark(puv, jeep);     // already parked
        tryPark(pov, car1);     // POV-C1
        tryPark(pov, car2);     // POV-C2
        tryPark(pov, car3);     // free spot exists but is too small
        tryPark(pov, moto);     // POV-M1
        tryPark(pov, car3);     // level full

        Vehicle removed = pov.findParkingSpot("POV-C1").removeVehicle();
        System.out.println("Removed " + removed.getPlateNumber() + " from POV-C1");
        tryPark(pov, car3);     // POV-C1 is free again

        System.out.println("POV full? " + pov.isFull());
        System.out.println("Lot total spots: " + lot.getTotalParkingSpots()
                + ", available: " + lot.getAvailableParkingSpots());
    }
}

// ===== TEMPORARY stand-ins for teammates' classes. DELETE everything below =====
// ===== once their real Vehicle, Car, Motorcycle, Bus and Jeepney files are added. =====
abstract class Vehicle {
    private final String plateNumber;
    Vehicle(String plateNumber) { this.plateNumber = plateNumber; }
    public String getPlateNumber() { return plateNumber; }
    public abstract VehicleSize getSize();
    public boolean isPublicUtility() { return false; }
}

class Car extends Vehicle {
    Car(String plate) { super(plate); }
    @Override public VehicleSize getSize() { return VehicleSize.CAR; }
}

class Motorcycle extends Vehicle {
    Motorcycle(String plate) { super(plate); }
    @Override public VehicleSize getSize() { return VehicleSize.MOTORCYCLE; }
}

class Jeepney extends Vehicle {
    Jeepney(String plate) { super(plate); }
    @Override public VehicleSize getSize() { return VehicleSize.JEEPNEY; }
    @Override public boolean isPublicUtility() { return true; }
}

class Bus extends Vehicle {
    Bus(String plate) { super(plate); }
    @Override public VehicleSize getSize() { return VehicleSize.BUS; }
    @Override public boolean isPublicUtility() { return true; }
}