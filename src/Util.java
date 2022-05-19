import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Set;

public class Util {

    private static final int NUM_ROWS_TEXT_AREA = 1;
    private static final int NUM_COLUMNS_TEXT_AREA = 5;

    public static final String CAR_REG_TEXT_AREA_NAME = "carRegistrationInput";
    public static final String CAR_OWNER_TEXT_AREA_NAME = "carOwnerInput";
    public static final String CAR_OWNER_TYPE_COMBO_BOX_NAME = "selectOwnerType";
    public static final String PARKING_SLOT_COMBO_BOX_NAME = "parkingSlotSelect";
    public static final String PARKING_SLOT_TEXT_AREA_NAME = "parkingSlotIdentifierInput";

    public static JPanel createParkCarInputPanel(CarPark carPark, ParkingSlot parkingSlotInFocus) {
        final JPanel carParkInputPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        final JPanel carRegistrationInputPanel = new JPanel();
        carRegistrationInputPanel.add(new JLabel("Car registration: "));
        final JTextArea carRegistrationTextArea = new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA);
        carRegistrationTextArea.setName(CAR_REG_TEXT_AREA_NAME);
        carRegistrationInputPanel.add(carRegistrationTextArea);

        final JPanel carOwnerInputPanel = new JPanel();
        carOwnerInputPanel.add(new JLabel("Car owner: "));
        final JTextArea carOwnerTextArea = new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA);
        carOwnerTextArea.setName(CAR_OWNER_TEXT_AREA_NAME);
        carOwnerInputPanel.add(carOwnerTextArea);

        final JPanel ownerTypeInputPanel = new JPanel();
        ownerTypeInputPanel.add(new JLabel("Select Owner Type: "));
        String[] ownerTypes = {parkingSlotInFocus.getType()};
        final JComboBox ownerTypesComboBox = new JComboBox<>(ownerTypes);
        ownerTypesComboBox.setName(CAR_OWNER_TYPE_COMBO_BOX_NAME);
        ownerTypeInputPanel.add(ownerTypesComboBox);

        final JPanel parkingSlotInputPanel = new JPanel();
        parkingSlotInputPanel.add(new JLabel("Select Parking Slot to Park Car in: "));
        final String[] parkingSlotIdentifiers = {parkingSlotInFocus.getIdentifier()};
        final JComboBox parkingSlotIDsComboBox = new JComboBox(parkingSlotIdentifiers);
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

    public static JPanel createFindCarInputPanel() {
        final JPanel findCarPanel = new JPanel();
        findCarPanel.add(new JLabel("Enter Car Registration: "));
        final JTextArea carRegistrationTextArea = new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA);
        carRegistrationTextArea.setName(CAR_REG_TEXT_AREA_NAME);
        findCarPanel.add(carRegistrationTextArea);
        findCarPanel.add(carRegistrationTextArea);

        return findCarPanel;
    }

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

    public static JPanel createAddParkingSlotInputPanel() {
        final JPanel createParkingSlotInputPanel = new JPanel();
        createParkingSlotInputPanel.add(new JLabel("Parking Slot Identifier: "));
        final JTextArea addParkingSlotTextArea = new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA);
        addParkingSlotTextArea.setName(PARKING_SLOT_TEXT_AREA_NAME);
        createParkingSlotInputPanel.add(addParkingSlotTextArea);

        return createParkingSlotInputPanel;
    }

    public static JPanel createDeleteParkingSlotInputPanel(ParkingSlot parkingSlotInFocus) {
        final JPanel removeParkingSlotInputPanel = new JPanel();
        final String parkingSlotIdentifier = parkingSlotInFocus.getIdentifier();
        final String labelText = String.format("Are you sure you want to remove parking slot %s?", parkingSlotIdentifier);
        removeParkingSlotInputPanel.add(new JLabel(labelText));
        return removeParkingSlotInputPanel;
    }

    // Helper function that maps names of components to the component.
    public static HashMap<String, Component> createNamesToComponentsMap(HashMap<String, Component> namesToComponents, JPanel panel) {
        Component[] components = panel.getComponents();;
        for (Component component : components) {
            if (component.getClass() == JPanel.class) {
                createNamesToComponentsMap(namesToComponents, (JPanel) component);
            } else if (component.getName() != null) {
                namesToComponents.put(component.getName(), component);
            }
        }

        return namesToComponents;
    }

    public static void openCarFoundDialog(String carRegoToBeFound, String parkingSlotIdCarFoundIn) {
        final String failMessage = "Car not found.";
        final String successMessage = String.format("The car %s was found in %s", carRegoToBeFound, parkingSlotIdCarFoundIn);
        if (parkingSlotIdCarFoundIn == null) {
            JOptionPane.showMessageDialog(new JFrame(), failMessage);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), successMessage);
        }
    }

    public static void openErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(new JFrame(), errorMessage);
    }
}
