/**
 * Represents a car with a registration number, owner and owner type (Staff or Visitor).
 */
public class Car {
    private String registrationNumber;
    private String owner;
    private String ownerType;

    /**
     * Create an instance of Car with a registration number, owner and owner type.
     * @param registrationNumber: Registration number of car. Must be capital letter followed by 5 digits.
     * @param owner: Owner's name.
     * @param ownerType: Staff or Visitor.
     */
    public Car(String registrationNumber, String owner, String ownerType) {
        setRegistrationNumber(registrationNumber);
        this.owner = owner;
        setOwnerType(ownerType);
    }

    /**
     * Get registration number of car.
     * @return registration number of car.
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the registration number of the car.
     * @param registrationNumber: Registration number of car to be set.
     */
    public void setRegistrationNumber(String registrationNumber) {
        String registrationNumberRegex = "[A-Z]\\d{5}";
        if (registrationNumber.matches(registrationNumberRegex)) {
            this.registrationNumber = registrationNumber;
        } else {
            throw new IllegalArgumentException("Please enter a capital letter followed by 5 numbers from 0-9 when " +
                    "entering the car registration number!");
        }
    }

    /**
     * Get owner of the car.
     * @return owner of the car.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Get owner type of the car.
     * @return owner type of the car.
     */
    public String getOwnerType() {
        return ownerType;
    }

    /**
     * Set owner type of the car.
     * @param ownerType: The owner type of the car to be set.
     */
    public void setOwnerType(String ownerType) {
        if (ownerType.equals("Visitor") || ownerType.equals("Staff")) {
            this.ownerType = ownerType;
        } else {
            throw new IllegalArgumentException("Please enter Staff or Visitor for owner type!");
        }
    }
}
