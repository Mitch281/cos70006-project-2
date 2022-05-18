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
        findCarPanel.add(new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA));

        return findCarPanel;
    }

    public static JPanel createRemoveCarInputPanel() {
        final JPanel removeCarInputPanel = new JPanel();
        removeCarInputPanel.add(new JLabel("Enter Car Registration: "));
        removeCarInputPanel.add(new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA));

        return removeCarInputPanel;
    }

    public static JPanel createAddParkingSlotInputPanel() {
        final JPanel createParkingSlotInputPanel = new JPanel();
        createParkingSlotInputPanel.add(new JLabel("Parking Slot Identifier: "));
        createParkingSlotInputPanel.add(new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA));

        return createParkingSlotInputPanel;
    }

    public static JPanel createDeleteParkingSlotInputPanel(CarPark carPark) {
        final JPanel deleteParkingSlotInputPanel = new JPanel();

        final Set<String> parkingSlotIdentifiers = carPark.getParkingSlots().keySet();
        final String[] parkingSlotIdentifiersArray = new String[parkingSlotIdentifiers.size()];
        int i = 0;
        for (String parkingSlotIdentifier: parkingSlotIdentifiers) {
            parkingSlotIdentifiersArray[i] = parkingSlotIdentifier;
            i++;
        }

        deleteParkingSlotInputPanel.add(new JLabel("Select Parking Slot to Remove"));
        deleteParkingSlotInputPanel.add(new JComboBox<>(parkingSlotIdentifiersArray));

        return deleteParkingSlotInputPanel;
    }

    // Helper function that maps names of components to the component.
    public static HashMap<String, Component> createNamesToComponentsMap(HashMap<String, Component> namesToComponents, JPanel panel) {
        Component[] components = panel.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i].getClass() == JPanel.class) {
                createNamesToComponentsMap(namesToComponents, (JPanel) components[i]);
            } else if (components[i].getName() != null){
                namesToComponents.put(components[i].getName(), components[i]);
            }
        }

        return namesToComponents;
    }
}