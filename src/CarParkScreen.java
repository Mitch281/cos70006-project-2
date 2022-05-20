import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;

/**
 * Contains the car park panel and options panel. Responsible for performing car park logic and sending
 * relevant information to ParkingSlotsPanel and CarParkOptionsPanel so they can update the GUI appropriately.
 */
public class CarParkScreen {

    private static final String PARK_CAR_DIALOG_HEADER = "Park Car";
    private static final String FIND_CAR_DIALOG_HEADER = "Find Car";
    private static final String REMOVE_CAR_DIALOG_HEADER  = "Remove Car";
    private static final String ADD_PARKING_SLOT_DIALOG_HEADER  = "Add Parking Slot";
    private static final String DELETE_PARKING_SLOT_DIALOG_HEADER  = "Remove Parking Slot";

    public static final String[] ACTIONS = {"park car", "find car", "remove car", "add parking slot",
            "delete parking slot"};

    public static final double PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER = 0.7;
    public static final double OPTIONS_PANEL_WIDTH_MULTIPLIER = 1 - PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER;

    private final JPanel carParkPanel = new JPanel();
    private final ParkingSlotsPanel parkingSlotsPanel = new ParkingSlotsPanel();
    private final CarParkOptionsPanel optionsPanel = new CarParkOptionsPanel();

    private final CarPark carPark = new CarPark();
    private final LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton = new LinkedHashMap<>();

    private String parkingSlotInFocusID = "";

    /**
     * Create an instance of the CarParkScreen class. Also set the layout of the car park panel and add the
     * parking slot and options panels to the car park panel.
     */
    public CarParkScreen() {
        this.carParkPanel.setLayout(new BorderLayout());
        this.carParkPanel.add(this.parkingSlotsPanel.getParkingSlotsPanel(), BorderLayout.LINE_START);
        this.carParkPanel.add(this.optionsPanel.getOptionsPanel(), BorderLayout.LINE_END);
    }

    /**
     * Create a listener to resize the parking slot and options panels when the screen is resized by the user.
     */
    public void addScreenResizeListener() {
        final JFrame window = (JFrame) SwingUtilities.windowForComponent(this.optionsPanel.getOptionsPanel());

        // Resize listener for when user changes size of window by dragging.
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                parkingSlotsPanel.setParkingSlotsPanelSize(window);
                optionsPanel.setCarParkOptionsPanelSize(window);
            }
        });

        // Resize listener for when user maximises window.
        window.addWindowStateListener(e -> {
            parkingSlotsPanel.setParkingSlotsPanelSize(window);
            optionsPanel.setCarParkOptionsPanelSize(window);
        });
    }

    /**
     * Get car park panel.
     * @return car park panel.
     */
    public JPanel getCarParkPanel() {
        return carParkPanel;
    }

    /**
     * Paint parking slot buttons onto parking slots panel. Also add action listeners to parking slots to detect
     * clicks.
     * @param numStaffSlots: The number of staff slots the user entered.
     * @param numStudentSlots: The number of student slots the user entered.
     */
    public void paintParkingSlots(int numStaffSlots, int numStudentSlots) {
        this.parkingSlotsPanel.paintParkingSlots(this.parkingSlotToButton, this.carPark, numStaffSlots, numStudentSlots);
        this.parkingSlotsPanel.setLayout(this.parkingSlotToButton, numStaffSlots, numStudentSlots);
        this.addClickListenersToParkingSlots();
    }

    /**
     * Paint the options panel. This is for the initial painting. Also adds action listeners to the buttons in
     * the options panel.
     */
    public void initialPaintOptionsPanel() {
        this.optionsPanel.paintOptionsPanelHeader();
        this.optionsPanel.paintParkingSlotOptions();
        this.addButtonActionListeners();
    }

    /**
     * Add action listeners to every parking slot JButton.
     */
    private void addClickListenersToParkingSlots() {
        for (Map.Entry<ParkingSlot, JButton> entry: parkingSlotToButton.entrySet()) {
            final ParkingSlot parkingSlot = entry.getKey();
            final JButton parkingSlotButton = entry.getValue();
            this.addClickListenerToParkingSlot(parkingSlot, parkingSlotButton);
        }
    }

    /**
     * Add an action listener to a specified parking slot JButton.
     * @param parkingSlot: The parking slot corresponding to the JButton.
     * @param parkingSlotButton: The JButton to add the action listener to.
     */
    private void addClickListenerToParkingSlot(ParkingSlot parkingSlot, JButton parkingSlotButton) {
        parkingSlotButton.addActionListener(e -> handleParkingSlotButtonClick(parkingSlot));
    }

    /**
     * Sets the identifier of the parking slot in focus depending on which parking slot button clicked.
     * Then toggles the options panel based on this information.
     * @param parkingSlotClicked: The parking slot clicked.
     */
    private void handleParkingSlotButtonClick(ParkingSlot parkingSlotClicked) {
        boolean isParkingSlotClickedInFocus = this.isParkingSlotClickedInFocus(parkingSlotClicked);
        if (isParkingSlotClickedInFocus) {
            this.parkingSlotInFocusID = parkingSlotClicked.getIdentifier();
        } else {
            this.parkingSlotInFocusID = "";
        }

        this.toggleOptionsPanel(parkingSlotClicked, isParkingSlotClickedInFocus);
    }

    private boolean isParkingSlotClickedInFocus(ParkingSlot parkingSlotClicked) {
        boolean isParkingSlotInFocus = true;
        final String parkingSlotIdentifier = parkingSlotClicked.getIdentifier();
        if (parkingSlotIdentifier.equals(this.parkingSlotInFocusID)) {
            isParkingSlotInFocus = false;
        }

        return isParkingSlotInFocus;
    }

    private void toggleOptionsPanel(ParkingSlot parkingSlotClicked, boolean isParkingSlotClickedInFocus) {
        if (isParkingSlotClickedInFocus) {
            this.optionsPanel.repaintParkingSlotOptions(parkingSlotClicked);
        } else {
            this.optionsPanel.unpaintParkingSlotOptions();
        }
    }

    /**
     * Opens dialog based on buttons in options panel user has clicked. Also attaches relevant handler functions
     * once user confirms dialog.
     * @param inputPanel: The panel contained inside the dialog.
     * @param header: The title of the dialog.
     * @param action: The desired action the user wants to carry out.
     * @param carParked: The car parked in the parking slot that is currently in focus.
     */
    private void openDialogInput(JPanel inputPanel, String header, String action, Car carParked) {
        final int result = JOptionPane.showConfirmDialog(null, inputPanel, header, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            switch (action) {
                case "park car" -> this.handleParkCar(inputPanel);
                case "find car" -> this.handleFindCar(inputPanel);
                case "remove car" -> this.handleRemoveCar(carParked);
                case "add parking slot" -> this.handleAddParkingSlot(inputPanel);
                case "delete parking slot" -> this.handleDeleteParkingSlot();
            }
        }
    }

    /**
     * Parks a car given the information entered by user in the dialog.
     * @param inputPanel: The panel contained inside the dialog containing the use input.
     */
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
            parkingSlotsPanel.setParkingSlotBackgroundToRed(parkingSlot);
        } catch (Exception e) {
            Util.openErrorDialog(e.getMessage());
        }
    }

    /**
     * Finds a car given the information entered by the user in the dialog.
     * @param inputPanel: The panel contained inside the dialog containing the user input.
     */
    private void handleFindCar(JPanel inputPanel) {
        final HashMap<String, Component> namesToComponents = Util.createNamesToComponentsMap(new HashMap<>(), inputPanel);

        final JTextArea carRegistrationTextArea = (JTextArea) namesToComponents.get(Util.CAR_REG_TEXT_AREA_NAME);
        final String carRegistration = carRegistrationTextArea.getText();

        // Only find the car if a user has entered a car registration.
        if (carRegistration != null) {
            ParkingSlot parkingSlotCarIsParkedIn = carPark.findCar(carRegistration);
            if (parkingSlotCarIsParkedIn != null) {
                String parkingSlotIdCarIsParkedIn = parkingSlotCarIsParkedIn.getIdentifier();
                Util.openCarFoundDialog(carRegistration, parkingSlotIdCarIsParkedIn);
            } else {
                Util.openCarFoundDialog(carRegistration, null);
            }
        }
    }

    /**
     * Removes a car from a parking slot if a car is parked there. Otherwise, it does nothing.
     * @param carToRemove: The car to be removed from the parking slot (if it exists).
     */
    private void handleRemoveCar(Car carToRemove) {
        // Only need to remove car if the car we passed in is not null.
        if (carToRemove != null) {
            final String carRegistrationToRemove = carToRemove.getRegistrationNumber();
            carPark.removeCar(carRegistrationToRemove);

            final ParkingSlot parkingSlotInFocus = this.carPark.getParkingSlots().get(this.parkingSlotInFocusID);
            parkingSlotsPanel.setParkingSlotBackgroundToGreen(parkingSlotInFocus);
        }
    }

    /**
     * Adds a parking slot based on the information entered by the user into the dialog.
     * @param inputPanel: The panel contained in the dialog containing the user input.
     */
    private void handleAddParkingSlot(JPanel inputPanel) {
        final HashMap<String, Component> namesToComponents = Util.createNamesToComponentsMap(new HashMap<>(), inputPanel);

        final JTextArea parkingSlotIDTextArea = (JTextArea) namesToComponents.get(Util.PARKING_SLOT_TEXT_AREA_NAME);
        final String parkingSlotIDEntered = parkingSlotIDTextArea.getText();

        try {
            carPark.addParkingSlot(parkingSlotIDEntered);
            ParkingSlot parkingSlotJustAdded = carPark.getParkingSlots().get(parkingSlotIDEntered);
            final JButton parkingSlotAddedButton = new JButton(parkingSlotIDEntered);
            parkingSlotAddedButton.setName(parkingSlotIDEntered);
            parkingSlotsPanel.paintNewParkingSlot(parkingSlotJustAdded, this.parkingSlotToButton, parkingSlotAddedButton);
            this.addClickListenerToParkingSlot(parkingSlotJustAdded, parkingSlotAddedButton);
        } catch (Exception e) {
            Util.openErrorDialog(e.getMessage());
        }
    }

    /**
     * Deletes the parking slot currently in focus.
     */
    private void handleDeleteParkingSlot() {
        carPark.deleteParkingSlot(this.parkingSlotInFocusID);
        this.parkingSlotsPanel.removeParkingSlotButton(this.parkingSlotInFocusID);
        this.parkingSlotInFocusID = "";
        this.optionsPanel.unpaintParkingSlotOptions();
    }

    /**
     * Adds action listeners to the buttons in the option panel to open the relevant dialogs when the buttons
     * are clicked.
     */
    private void addButtonActionListeners() {
        this.optionsPanel.getParkCarButton().addActionListener(e -> {
            final ParkingSlot parkingSlotInFocus = this.carPark.getParkingSlots().get(this.parkingSlotInFocusID);
            final JPanel parkCarInputPanel = Util.createParkCarInputPanel(parkingSlotInFocus);
            this.openDialogInput(parkCarInputPanel, PARK_CAR_DIALOG_HEADER, ACTIONS[0], null);
        });

        this.optionsPanel.getFindCarButton().addActionListener(e -> {
            final JPanel findCarInputPanel = Util.createFindCarInputPanel();
            this.openDialogInput(findCarInputPanel, FIND_CAR_DIALOG_HEADER, ACTIONS[1], null);
        });

        this.optionsPanel.getRemoveCarButton().addActionListener(e -> {
            final ParkingSlot parkingSlotInFocus = carPark.getParkingSlots().get(this.parkingSlotInFocusID);
            final Car carParked = parkingSlotInFocus.getCarParked();
            final JPanel removeCarInputPanel = Util.createRemoveCarInputPanel(parkingSlotInFocus);
            this.openDialogInput(removeCarInputPanel, REMOVE_CAR_DIALOG_HEADER, ACTIONS[2], carParked);
        });

        this.optionsPanel.getAddParkingSlotButton().addActionListener(e -> {
            final JPanel addParkingSlotInputPanel = Util.createAddParkingSlotInputPanel();
            this.openDialogInput(addParkingSlotInputPanel, ADD_PARKING_SLOT_DIALOG_HEADER, ACTIONS[3], null);
        });

        this.optionsPanel.getDeleteParkingSlotButton().addActionListener(e -> {
            final ParkingSlot parkingSlotInFocus = carPark.getParkingSlots().get(this.parkingSlotInFocusID);
            final JPanel deleteParkingSlotInputPanel = Util.createDeleteParkingSlotInputPanel(parkingSlotInFocus);
            this.openDialogInput(deleteParkingSlotInputPanel, DELETE_PARKING_SLOT_DIALOG_HEADER, ACTIONS[4], null);
        });
    }
}
