import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.LinkedHashMap;

/**
 * Represents a car park, which is a list of parking slots.
 */
public class CarPark {

    public static final int MAX_NUMBER_PARKING_SLOTS = 999;

    private final LinkedHashMap<String, ParkingSlot> parkingSlots = new LinkedHashMap<>();

    /**
     * Created an instance of CarPark.
     * @return instance of CarPark.
     */
    public LinkedHashMap<String, ParkingSlot> getParkingSlots() {
        return parkingSlots;
    }

    /**
     * Creates parking slots for the car park based on user inputted information about the number of staff slots
     * and the number of student slots.
     * @param numStaffSlots: The number of staff parking slots to create.
     * @param numVisitorSlots: The number of student parking slots to create.
     */
    public void createParkingSlots(int numStaffSlots, int numVisitorSlots) {

        // Create the staff slots.
        for (int i = 1; i < numStaffSlots + 1; i++) {
            int numDigits = String.valueOf(i).length();
            int numZeroesInFront = 3 - numDigits;
            String parkingSlotIdentifier = "S" + "0".repeat(numZeroesInFront) + i;
            this.parkingSlots.put(parkingSlotIdentifier, new ParkingSlot(parkingSlotIdentifier));
        }

        // Create visitor slots.
        for (int i = 1; i < numVisitorSlots + 1; i++) {
            int numDigits = String.valueOf(i).length();
            int numZeroesInFront = 3 - numDigits;
            String parkingSlotIdentifier = "V" + "0".repeat(numZeroesInFront) + i;
            this.parkingSlots.put(parkingSlotIdentifier, new ParkingSlot(parkingSlotIdentifier));
        }
    }

    /**
     * Finds the parking slot identifier of the parking slot the inputted car is parked in.
     * @param carRegistration: The registration number of the car
     * @return the parking slot identifier of the parking slot the inputted car is parked in.
     */
    public ParkingSlot findCar(String carRegistration) {
        for (ParkingSlot parkingSlot: parkingSlots.values()) {
            if (parkingSlot.getCarParked() != null) {
                Car carParked = parkingSlot.getCarParked();
                if (carParked.getRegistrationNumber().equals(carRegistration)) {
                    return parkingSlot;
                }
            }
        }
        return null;
    }

    /**
     * Removes a car from a parking slot based on the car registration.
     * @param carRegistration: The car registration of the car.
     */
    public void removeCar(String carRegistration) {
        ParkingSlot parkingSlot = this.findCar(carRegistration);
        parkingSlot.unparkCar();
    }

    /**
     * Puts information of the car park into a readable format.
     * @return information of car park.
     */
    public String toString() {
        if (this.parkingSlots.size() == 0) {
            return "There are no parking slots to list!";
        }
        StringBuilder resultString = new StringBuilder();
        for (ParkingSlot parkingSlot: this.parkingSlots.values()) {
            String parkingSlotIdentifier = parkingSlot.getIdentifier();
            String parkingSlotType = parkingSlot.getType();
            String occupiedStatus = parkingSlot.getCarParked() != null ? "occupied" : "not occupied";
            String template = "SlotID is: %s, is for %s, and is %s\n";
            resultString.append(String.format(template, parkingSlotIdentifier, parkingSlotType, occupiedStatus));
        }
        return resultString.toString();
    }

    /**
     * Adds parking slot to a car park.
     * @param parkingSlotIdentifier: The identifier of the parking slot to be added.
     */
    public void addParkingSlot(String parkingSlotIdentifier)  {
        if (this.parkingSlots.containsKey(parkingSlotIdentifier)) {
            throw new KeyAlreadyExistsException("There is a parking slot in this car park with the same ID " +
                    "already!");
        }
        if (this.parkingSlots.size() == MAX_NUMBER_PARKING_SLOTS) {
            throw new IndexOutOfBoundsException("Car park is full!");
        }
        ParkingSlot newParkingSlot = new ParkingSlot(parkingSlotIdentifier);
        this.parkingSlots.put(parkingSlotIdentifier, newParkingSlot);
    }

    /**
     * Delete a parking slot based on the inputted parking slot identifier.
     * @param parkingSlotToDeleteIdentifier: The identifier of the parking slot to be deleted.
     */
    public void deleteParkingSlot(String parkingSlotToDeleteIdentifier) {
        this.parkingSlots.remove(parkingSlotToDeleteIdentifier);
    }
}
