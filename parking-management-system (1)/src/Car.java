public class Car extends Vehicle {
public Car(String getPlateNumber) {
    super(getplateNumber);
}
@Override public Vehicle getSize() {
    return Vehicle.CAR;
}
@Override public boolean isPublicUtility() {
    return false;
}
@Override public String teststring() {
    return "Car [Plate number" + getplateNumber() + ", size: " + getSize() + ", Type: Private Vehicle]"; 
}
}