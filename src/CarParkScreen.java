import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarParkScreen {

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

    // TODO: Handle screen resizing so that we can make border layout responsive.

}
