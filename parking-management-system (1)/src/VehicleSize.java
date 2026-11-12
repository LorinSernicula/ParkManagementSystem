public enum VehicleSize {
    // Keep this order: smallest to largest. canFitIn() depends on it.
    MOTORCYCLE('M'),
    CAR('C'),
    JEEPNEY('J'),
    BUS('B');

    private final char code;   // first letter, used in spot numbers

    VehicleSize(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    // ordinal() = position in the list above (0, 1, 2, 3)
    public boolean canFitIn(VehicleSize spotSize) {
        return spotSize.ordinal() >= this.ordinal();
    }
}