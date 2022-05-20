import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Helper methods to create input panels and open dialogs, as well as creating mappings from component names
 * to components.
 */
public class Util {

    private static final int NUM_ROWS_TEXT_AREA = 1;
    private static final int NUM_COLUMNS_TEXT_AREA = 5;

    // These constants are used to set the names of components.
    public static final String CAR_REG_TEXT_AREA_NAME = "carRegistrationInput";
    public static final String CAR_OWNER_TEXT_AREA_NAME = "carOwnerInput";
    public static final String CAR_OWNER_TYPE_COMBO_BOX_NAME = "selectOwnerType";
    public static final String PARKING_SLOT_COMBO_BOX_NAME = "parkingSlotSelect";
    public static final String PARKING_SLOT_TEXT_AREA_NAME = "parkingSlotIdentifierInput";

    /**
     * Creates a panel for the user to input a car to park using the parking slot in focus.
     * @param parkingSlotInFocus: The parking slot currently in focus.
     * @return JPanel for user input to park a car.
     */
    public static JPanel createParkCarInputPanel(ParkingSlot parkingSlotInFocus) {
        final JPanel carParkInputPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        final JPanel carRegistrationInputPanel = new JPanel();
        carRegistrationInputPanel.add(new JLabel("Car registration (capital letter followed by 5 digits)" ));
        final JTextArea carRegistrationTextArea = new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA);
        carRegistrationTextArea.setName(CAR_REG_TEXT_AREA_NAME);
        carRegistrationInputPanel.add(carRegistrationTextArea);

        final JPanel carOwnerInputPanel = new JPanel();
        carOwnerInputPanel.add(new JLabel("Car owner: "));
        final JTextArea carOwnerTextArea = new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA);
        carOwnerTextArea.setName(CAR_OWNER_TEXT_AREA_NAME);
        carOwnerInputPanel.add(carOwnerTextArea);

        final JPanel ownerTypeInputPanel = new JPanel();
        ownerTypeInputPanel.add(new JLabel("Owner Type: "));
        String[] ownerTypes = {parkingSlotInFocus.getType()};
        final JComboBox ownerTypesComboBox = new JComboBox<>(ownerTypes);
        ownerTypesComboBox.setName(CAR_OWNER_TYPE_COMBO_BOX_NAME);
        ownerTypeInputPanel.add(ownerTypesComboBox);

        final JPanel parkingSlotInputPanel = new JPanel();
        parkingSlotInputPanel.add(new JLabel("Parking Slot to Park Car in: "));
        final String[] parkingSlotIdentifiers = {parkingSlotInFocus.getIdentifier()};
        final JComboBox parkingSlotIDsComboBox = new JComboBox<>(parkingSlotIdentifiers);
        parkingSlotIDsComboBox.setName(PARKING_SLOT_COMBO_BOX_NAME);
        parkingSlotInputPanel.add(parkingSlotIDsComboBox);

        gbc.gridx = 0;
        gbc.gridy = 0;
        carParkInputPanel.add(carRegistrationInputPanel, gbc);

        gbc.gridy = 1;
        carParkInputPanel.add(carOwnerInputPanel, gbc);

        gbc.gridy = 2;
        carParkInputPanel.add(ownerTypeInputPanel, gbc);

        gbc.gridy = 3;
        carParkInputPanel.add(parkingSlotInputPanel, gbc);

        return carParkInputPanel;
    }

    /**
     * Creates a JPanel for user input to find a car.
     * @return JPanel for user input to find a car.
     */
    public static JPanel createFindCarInputPanel() {
        final JPanel findCarPanel = new JPanel();
        findCarPanel.add(new JLabel("Enter Car Registration (capital letter followed by 5 digits): "));
        final JTextArea carRegistrationTextArea = new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA);
        carRegistrationTextArea.setName(CAR_REG_TEXT_AREA_NAME);
        findCarPanel.add(carRegistrationTextArea);
        findCarPanel.add(carRegistrationTextArea);

        return findCarPanel;
    }

    /**
     * Creates a JPanel for user input to remove a car using the respective parking slot in focus.
     * @param parkingSlotInFocus: The parking slot in focus.
     * @return JPanel for user input to remove a car.
     */
    public static JPanel createRemoveCarInputPanel(ParkingSlot parkingSlotInFocus) {
        final JPanel removeCarInputPanel = new JPanel();
        final Car carParkedInParkingSlot = parkingSlotInFocus.getCarParked();
        if (carParkedInParkingSlot != null) {
            final String carParkedInParkingSlotRego = carParkedInParkingSlot.getRegistrationNumber();
            final String parkingSlotInFocusID = parkingSlotInFocus.getIdentifier();
            final String labelText = String.format("Are you sure you want to remove car %s from parking slot " +
                    "%s?", carParkedInParkingSlotRego, parkingSlotInFocusID);
            removeCarInputPanel.add(new JLabel(labelText));
        } else {
            removeCarInputPanel.add(new JLabel("There is no car parked here."));
        }

        return removeCarInputPanel;
    }

    /**
     * Creates a JPanel for user input to add a parking slot.
     * @return JPanel for user input to add a parking slot.
     */
    public static JPanel createAddParkingSlotInputPanel() {
        final JPanel createParkingSlotInputPanel = new JPanel();
        createParkingSlotInputPanel.add(new JLabel("Parking Slot Identifier"));
        final JTextArea addParkingSlotTextArea = new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA);
        addParkingSlotTextArea.setName(PARKING_SLOT_TEXT_AREA_NAME);
        createParkingSlotInputPanel.add(addParkingSlotTextArea);
        createParkingSlotInputPanel.add(new JLabel("(Please start the parking slot identifier with capital V " +
                "for visitor or capital S for student, followed by 3 digits. For example: S231.)"));

        return createParkingSlotInputPanel;
    }

    /**
     * Creates a JPanel for user input to delete a parking slot, using the respective parking slot in focus,
     * @param parkingSlotInFocus: The parking slot currently in focus.
     * @return JPanel for user input to delete a parking slot.
     */
    public static JPanel createDeleteParkingSlotInputPanel(ParkingSlot parkingSlotInFocus) {
        final JPanel removeParkingSlotInputPanel = new JPanel();
        final String parkingSlotIdentifier = parkingSlotInFocus.getIdentifier();
        final String labelText = String.format("Are you sure you want to remove parking slot %s?", parkingSlotIdentifier);
        removeParkingSlotInputPanel.add(new JLabel(labelText));
        return removeParkingSlotInputPanel;
    }

    /**
     * Maps names of components to the components.
     * @param namesToComponents: Initial names to components hash map, to be used to recursively build the names to
     *                         components hash map.
     * @param panel: The panel which contains the components we want to create a map from.
     * @return a hash map which maps component names to components.
     */
    public static HashMap<String, Component> createNamesToComponentsMap(HashMap<String, Component> namesToComponents, JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component.getClass() == JPanel.class) {
                createNamesToComponentsMap(namesToComponents, (JPanel) component);
            } else if (component.getName() != null) {
                namesToComponents.put(component.getName(), component);
            }
        }

        return namesToComponents;
    }

    /**
     * Opens a dialog telling the user parking slot the desired car was found in (or message telling user car wasn't
     * found if it wasn't found).
     * @param carRegoToBeFound: The registration of the car the user wants to find.
     * @param parkingSlotIdCarFoundIn: The parking slot the car was found in.
     */
    public static void openCarFoundDialog(String carRegoToBeFound, String parkingSlotIdCarFoundIn) {
        final String failMessage = "Car not found.";
        final String successMessage = String.format("The car %s was found in %s", carRegoToBeFound, parkingSlotIdCarFoundIn);
        if (parkingSlotIdCarFoundIn == null) {
            JOptionPane.showMessageDialog(new JFrame(), failMessage);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), successMessage);
        }
    }

    /**
     * Displays an error message in a dialog.
     * @param errorMessage: The error message to display.
     */
    public static void openErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(new JFrame(), errorMessage);
    }
}
