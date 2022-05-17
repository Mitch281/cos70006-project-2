import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CarParkScreen {

    private static final String PARK_CAR_DIALOG_HEADER = "Park Car";
    private static final String FIND_CAR_DIALOG_HEADER = "Find Car";
    private static final String REMOVE_CAR_DIALOG_HEADER  = "Remove Car";
    private static final String ADD_PARKING_SLOT_DIALOG_HEADER  = "Add Parking Slot";
    private static final String DELETE_PARKING_SLOT_DIALOG_HEADER  = "Remove Parking Slot";

    private static final int NUM_ROWS_TEXT_AREA = 1;
    private static final int NUM_COLUMNS_TEXT_AREA = 5;

    private static final double PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER = 0.7;
    private static final double OPTIONS_PANEL_WIDTH_MULTIPLIER = 1 - PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER;

    private final JPanel carParkPanel = new JPanel();
    private final ParkingSlotsPanel parkingSlotsPanel = new ParkingSlotsPanel();
    private final CarParkOptionsPanel optionsPanel = new CarParkOptionsPanel();

    private final CarPark carPark = new CarPark();
    private final LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton = new LinkedHashMap<>();

    private String parkingSlotInFocusID = "";

    public CarParkScreen() {
        this.carParkPanel.setLayout(new BorderLayout());
        this.carParkPanel.add(this.parkingSlotsPanel.getParkingSlotsPanel(), BorderLayout.LINE_START);
        this.carParkPanel.add(this.optionsPanel.getOptionsPanel(), BorderLayout.LINE_END);
    }

    public JPanel getCarParkPanel() {
        return carParkPanel;
    }

    public void paintParkingSlots(int numStaffSlots, int numStudentSlots) {
        this.parkingSlotsPanel.paintParkingSlots(this.parkingSlotToButton, this.carPark, numStaffSlots, numStudentSlots);
        this.parkingSlotsPanel.setLayout(this.parkingSlotToButton, numStaffSlots, numStudentSlots);
        this.addClickListenersToParkingSlots();
    }

    // This is for the initial painting of the options panel.
    public void paintOptionsPanel() {
        this.optionsPanel.paintOptionsPanelHeader();
        this.optionsPanel.paintParkingSlotOptions();
        this.addButtonActionListeners();
    }

    private void addClickListenersToParkingSlots() {
        for (Map.Entry<ParkingSlot, JButton> entry: parkingSlotToButton.entrySet()) {
            final ParkingSlot parkingSlot = entry.getKey();
            final JButton parkingSlotButton = entry.getValue();

            parkingSlotButton.addActionListener(e -> {
                handleParkingSlotButtonClick(parkingSlot);
            });
        }
    }

    private void handleParkingSlotButtonClick(ParkingSlot parkingSlot) {
        boolean isParkingSlotInFocus = true;
        final String parkingSlotIdentifier = parkingSlot.getIdentifier();
        final ParkingSlot parkingSlotInFocus = this.carPark.getParkingSlots().get(parkingSlotIdentifier);
        if (parkingSlotIdentifier.equals(this.parkingSlotInFocusID)) {
            isParkingSlotInFocus = false;
            this.parkingSlotInFocusID = "";
        } else {
            this.parkingSlotInFocusID = parkingSlotIdentifier;
        }

        if (isParkingSlotInFocus) {
            this.optionsPanel.repaintParkingSlotOptions(parkingSlotInFocus);
        } else {
            this.optionsPanel.unpaintParkingSlotOptions();
        }
    }

    private ArrayList<String> openDialogInput(JPanel inputPanel, String instructions) {
        final int result = JOptionPane.showConfirmDialog(null, inputPanel, instructions, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return new ArrayList<String>();
        }

        return null;
    }

    private JPanel createParkCarInputPanel() {
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
        String ownerTypes[] = {"Staff", "Visitor"};
        ownerTypeInputPanel.add(new JComboBox<>(ownerTypes));

        final JPanel parkingSlotInputPanel = new JPanel();
        parkingSlotInputPanel.add(new JLabel("Select Parking Slot to Park Car in: "));
        final Set<String> parkingSlotIdentifiers = this.carPark.getParkingSlots().keySet();
        final String parkingSlotIdentifiersArray[] = new String[parkingSlotIdentifiers.size()];
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

    private JPanel createFindCarInputPanel() {
        final JPanel findCarPanel = new JPanel();
        findCarPanel.add(new JLabel("Enter Car Registration: "));
        findCarPanel.add(new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA));

        return findCarPanel;
    }

    private JPanel createRemoveCarInputPanel() {
        final JPanel removeCarInputPanel = new JPanel();
        removeCarInputPanel.add(new JLabel("Enter Car Registration: "));
        removeCarInputPanel.add(new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA));

        return removeCarInputPanel;
    }

    private JPanel createAddParkingSlotInputPanel() {
        final JPanel createParkingSlotInputPanel = new JPanel();
        createParkingSlotInputPanel.add(new JLabel("Parking Slot Identifier: "));
        createParkingSlotInputPanel.add(new JTextArea(NUM_ROWS_TEXT_AREA, NUM_COLUMNS_TEXT_AREA));

        return createParkingSlotInputPanel;
    }

    private JPanel createDeleteParkingSlotInputPanel() {
        final JPanel deleteParkingSlotInputPanel = new JPanel();

        final Set<String> parkingSlotIdentifiers = this.carPark.getParkingSlots().keySet();
        final String parkingSlotIdentifiersArray[] = new String[parkingSlotIdentifiers.size()];
        int i = 0;
        for (String parkingSlotIdentifier: parkingSlotIdentifiers) {
            parkingSlotIdentifiersArray[i] = parkingSlotIdentifier;
            i++;
        }

        deleteParkingSlotInputPanel.add(new JLabel("Select Parking Slot to Remove"));
        deleteParkingSlotInputPanel.add(new JComboBox<>(parkingSlotIdentifiersArray));

        return deleteParkingSlotInputPanel;
    }

    private void addButtonActionListeners() {
        this.optionsPanel.getParkCarButton().addActionListener(e -> {
            final JPanel parkCarInputPanel = this.createParkCarInputPanel();
            this.openDialogInput(parkCarInputPanel, PARK_CAR_DIALOG_HEADER);
        });

        this.optionsPanel.getFindCarButton().addActionListener(e -> {
            final JPanel findCarInputPanel = this.createFindCarInputPanel();
            this.openDialogInput(findCarInputPanel, FIND_CAR_DIALOG_HEADER);
        });

        this.optionsPanel.getRemoveCarButton().addActionListener(e -> {
            final JPanel removeCarInputPanel = this.createRemoveCarInputPanel();
            this.openDialogInput(removeCarInputPanel, REMOVE_CAR_DIALOG_HEADER);
        });

        this.optionsPanel.getAddParkingSlotButton().addActionListener(e -> {
            final JPanel addParkingSlotInputPanel = this.createAddParkingSlotInputPanel();
            this.openDialogInput(addParkingSlotInputPanel, ADD_PARKING_SLOT_DIALOG_HEADER);
        });

        this.optionsPanel.getDeleteParkingSlotButton().addActionListener(e -> {
            final JPanel deleteParkingSlotInputPanel = this.createDeleteParkingSlotInputPanel();
            this.openDialogInput(deleteParkingSlotInputPanel, DELETE_PARKING_SLOT_DIALOG_HEADER);
        });
    }

    // TODO: Handle screen resizing so that we can make border layout responsive.

}
