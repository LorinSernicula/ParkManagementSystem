import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class VehicleFactory {
    // lists all vehicle types supported by the parking system
    public enum VehicleType {
        MOTORCYCLE,
        CAR,
        JEEPNEY,
        BUS
    }
    // prevents creation of unnecessary VehicleFactory objects, all methods are static
    private VehicleFactory() {
    }
    // creates a vehicle using a String input
    public static Vehicle createVehicle(String type, String plateNumber) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle type is required.");
        }
        if (plateNumber == null || plateNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Plate number is required.");
        }
        VehicleType vehicleType;
        try {
            // converts String input into a VehicleType enum
            vehicleType = VehicleType.valueOf(type.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported vehicle type: " + type);
        }
        // calls overloaded method to create the vehicle
        return createVehicle(vehicleType, plateNumber);
    }
    // creates vehicle using a VehicleType enum
    public static Vehicle createVehicle(VehicleType type, String plateNumber) {
        if (type == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null.");
        }
        if (plateNumber == null || plateNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Plate number is required.");
        }
        // creates appropriate vehicle subclass
        switch (type) {
            case MOTORCYCLE:
                return new Motorcycle(plateNumber);
            case CAR:
                return new Car(plateNumber);
            case JEEPNEY:
                return new Jeepney(plateNumber);
            case BUS:
                return new Bus(plateNumber);
            default:
                throw new IllegalArgumentException("Unable to create vehicle.");
        }
    }
    // returns all supported vehicle types
    public static List<String> getSupportedTypes() {
        List<String> types = new ArrayList<>();
        // retrieves all constants declared in VehicleType
        for (VehicleType type : VehicleType.values()) {
            types.add(type.name());
        }
        // prevents external classes from modifying the returned list
        return Collections.unmodifiableList(types);
    }
    // checks whether vehicle type is supported
    public static boolean isSupportedType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return false;
        }
        try {
            VehicleType.valueOf(type.trim().toUpperCase(Locale.ROOT));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}