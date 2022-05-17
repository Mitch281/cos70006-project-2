import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class CreateActionInputPanels {

    public static final int NUM_ROWS_TEXT_AREA = 1;
    public static final int NUM_COLUMNS_TEXT_AREA = 5;

    public static JPanel createParkCarInputPanel(CarPark carPark) {
        final JPanel carParkInputPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        final JPanel carRegistrationInputPanel = new JPanel();
        carRegistrationInputPanel.add(new JLabel("Car registration: "));
        carRegistrationInputPanel.add(new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA));

        final JPanel carOwnerInputPanel = new JPanel();
        carOwnerInputPanel.add(new JLabel("Car owner: "));
        carOwnerInputPanel.add(new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA));

        final JPanel ownerTypeInputPanel = new JPanel();
        ownerTypeInputPanel.add(new JLabel("Select Owner Type: "));
        String[] ownerTypes = {"Staff", "Visitor"};
        ownerTypeInputPanel.add(new JComboBox<>(ownerTypes));

        final JPanel parkingSlotInputPanel = new JPanel();
        parkingSlotInputPanel.add(new JLabel("Select Parking Slot to Park Car in: "));
        final Set<String> parkingSlotIdentifiers = carPark.getParkingSlots().keySet();
        final String[] parkingSlotIdentifiersArray = new String[parkingSlotIdentifiers.size()];
        int i = 0;
        for (String parkingSlotIdentifier: parkingSlotIdentifiers) {
            parkingSlotIdentifiersArray[i] = parkingSlotIdentifier;
            i++;
        }
        parkingSlotInputPanel.add(new JComboBox<>(parkingSlotIdentifiersArray));

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
}
