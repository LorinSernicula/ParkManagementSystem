public class Bus extends Vehicle {
public Bus(String getPlateNumber) {
    super(getplateNumber);
}
@Override public Vehicle getSize() {
    return Vehicle.BUS;
}
@Override public boolean isPublicUtility() {
    return false;
}
@Override public String teststring() {
    return "Bus [Plate number" + getplateNumber() + ", size: " + getSize() + ", Type: Private Vehicle]"; 
}
}