/**
 * Represents a parking slot. These make up a car park. A parking slot has an identifier and type. It also has a
 * boolean value to represent if a car is parked in the parking slot. If a car is parked, the car parked in the
 * parking slot is stored.
 */
public class ParkingSlot {
    private String identifier;
    private Car carParked;
    private String slotType;

    /**
     * Create an instance of ParkingSlot based on the identifier.
     * @param identifier: The identifier of the parking slot.
     */
    public ParkingSlot(String identifier) {
        setIdentifier(identifier);
        if (identifier.charAt(0) == 'V') {
            this.slotType = "Visitor";
        } else {
            this.slotType = "Staff";
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
     * Gets the type of the parking slot (Staff or Visitor).
     * @return the type of the parking slot.
     */
    public String getSlotType() {
        return slotType;
    }

    /**
     * Parks a car in a parking slot.
     * @param car: The car to be parked in the parking slot.
     */
    public void parkCar(Car car) {
        this.carParked = car;
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