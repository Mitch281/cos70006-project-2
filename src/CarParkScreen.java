import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CarParkScreen {

    private static final String PARK_CAR_DIALOG_HEADER = "Park Car";
    private static final String FIND_CAR_DIALOG_HEADER = "Find Car";
    private static final String REMOVE_CAR_DIALOG_HEADER  = "Remove Car";
    private static final String ADD_PARKING_SLOT_DIALOG_HEADER  = "Add Parking Slot";
    private static final String DELETE_PARKING_SLOT_DIALOG_HEADER  = "Remove Parking Slot";

    // TODO: Add action for unpark car!!!!!!
    public static final String[] ACTIONS = {"park car", "find car", "remove car", "add parking slot",
            "delete parking slot"};

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

            parkingSlotButton.addActionListener(e -> handleParkingSlotButtonClick(parkingSlot));
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

    private void openDialogInput(JPanel inputPanel, String header, String action) {
        final int result = JOptionPane.showConfirmDialog(null, inputPanel, header, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            switch (action) {
                case "park car" -> {
                    this.handleParkCar(inputPanel);
                }
            }
        }
    }

    private void handleParkCar(JPanel inputPanel) {
        final HashMap<String, Component> namesToComponents = Util.createNamesToComponentsMap(new HashMap<>(), inputPanel);

        final JTextArea carRegistrationTextArea = (JTextArea) namesToComponents.get(Util.CAR_REG_TEXT_AREA_NAME);
        final String carRegistration = carRegistrationTextArea.getText();

        final JTextArea carOwnerTextArea = (JTextArea) namesToComponents.get(Util.CAR_OWNER_TEXT_AREA_NAME);
        final String carOwner = carOwnerTextArea.getText();

        final JComboBox ownerTypeComboBox = (JComboBox) namesToComponents.get(Util.CAR_OWNER_TYPE_COMBO_BOX_NAME);
        final String ownerType = ownerTypeComboBox.getSelectedItem().toString();

        final JComboBox parkingSlotComboBox = (JComboBox) namesToComponents.get(Util.PARKING_SLOT_COMBO_BOX_NAME);
        final String parkingSlotIdentifier = parkingSlotComboBox.getSelectedItem().toString();

        final ParkingSlot parkingSlot = this.carPark.getParkingSlots().get(parkingSlotIdentifier);

        try {
            final Car carToBeParked = new Car(carRegistration, carOwner, ownerType);
            parkingSlot.parkCar(carToBeParked);
            parkingSlotsPanel.handleAction(ACTIONS[0], parkingSlotToButton, parkingSlot);
        } catch (Exception e) {
            // handle exception appropriately.
        }
    }

    private void addButtonActionListeners() {
        this.optionsPanel.getParkCarButton().addActionListener(e -> {
            final ParkingSlot parkingSlotInFocus = this.carPark.getParkingSlots().get(this.parkingSlotInFocusID);
            final JPanel parkCarInputPanel = Util.createParkCarInputPanel(carPark, parkingSlotInFocus);
            this.openDialogInput(parkCarInputPanel, PARK_CAR_DIALOG_HEADER, ACTIONS[0]);
        });

        this.optionsPanel.getFindCarButton().addActionListener(e -> {
            final JPanel findCarInputPanel = Util.createFindCarInputPanel();
            this.openDialogInput(findCarInputPanel, FIND_CAR_DIALOG_HEADER, ACTIONS[1]);
        });

        this.optionsPanel.getRemoveCarButton().addActionListener(e -> {
            final JPanel removeCarInputPanel = Util.createRemoveCarInputPanel();
            this.openDialogInput(removeCarInputPanel, REMOVE_CAR_DIALOG_HEADER, ACTIONS[2]);
        });

        this.optionsPanel.getAddParkingSlotButton().addActionListener(e -> {
            final JPanel addParkingSlotInputPanel = Util.createAddParkingSlotInputPanel();
            this.openDialogInput(addParkingSlotInputPanel, ADD_PARKING_SLOT_DIALOG_HEADER, ACTIONS[3]);
        });

        this.optionsPanel.getDeleteParkingSlotButton().addActionListener(e -> {
            final JPanel deleteParkingSlotInputPanel = Util.createDeleteParkingSlotInputPanel(carPark);
            this.openDialogInput(deleteParkingSlotInputPanel, DELETE_PARKING_SLOT_DIALOG_HEADER, ACTIONS[4]);
        });
    }

    // TODO: Handle screen resizing so that we can make border layout responsive.

}
