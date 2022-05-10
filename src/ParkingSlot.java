/**
 * Represents a parking slot. These make up a car park. A parking slot has an identifier and type. It also has a
 * boolean value to represent if a car is parked in the parking slot. If a car is parked, the car parked in the
 * parking slot is stored.
 */
public class ParkingSlot {
    private String identifier;
    private Car carParked;
    private String type;

    /**
     * Create an instance of ParkingSlot based on the identifier.
     * @param identifier: The identifier of the parking slot.
     */
    public ParkingSlot(String identifier) {
        setIdentifier(identifier);
        if (identifier.charAt(0) == 'V') {
            this.type = "Visitor";
        } else {
            this.type = "Staff";
        }
    }

    /**
     * Gets the parking slot identifier.
     * @return identifier of parking slot.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier of the parking slot.
     * @param identifier: The identifier of the parking slot.
     */
    public void setIdentifier(String identifier) {
        String identifierRegex = "[SV]\\d{3}";
        if (identifier.matches(identifierRegex)) {
            this.identifier = identifier;
        } else {
            throw new IllegalArgumentException("Please make sure that your parking slot identifier starts with " +
                    "either a S for staff or V for visitor, followed by 3 numbers.");
        }
    }

    /**
     * Gets the car parked in the parking slot (or null if no car is parked).
     * @return the car parked in the parking slot.
     */
    public Car getCarParked() {
        return carParked;
    }

    /**
     * Sets the car parked in the parking slot.
     * @param carParked: The car parked in the parking slot.
     */
    public void setCarParked(Car carParked) {
        this.carParked = carParked;
    }

    /**
     * Gets the type of the parking slot (Staff or Visitor).
     * @return the type of the parking slot.
     */
    public String getType() {
        return type;
    }

    /**
     * Parks a car in a parking slot.
     * @param car: The car to be parked in the parking slot.
     */
    public void parkCar(Car car) {
        if (car.getOwnerType().equals(type)) {
            this.carParked = car;
        } else {
            throw new IllegalArgumentException("Please make sure the type of the car you are parking is the same as " +
                    "the parking slot type!");
        }
    }

    /**
     * Un-parks the car from the parking slot.
     */
    public void unparkCar() {
        this.carParked = null;
    }

    public boolean isSlotOccupied() {
        return this.carParked != null;
    }
}