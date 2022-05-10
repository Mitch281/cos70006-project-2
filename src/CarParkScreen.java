import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Map;

public class CarParkScreen {

    private static final int NUM_SLOTS_PER_ROW = 15;
    private static final double PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER = 0.7;
    private static final double OPTIONS_PANEL_WIDTH_MULTIPLIER = 1 - PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER;

    private final JPanel carParkPanel = new JPanel();
    private final JPanel parkingSlotsPanel = new JPanel();
    private final JPanel optionsPanel = new JPanel();
    private final CarPark carPark = new CarPark();
    private final LinkedHashMap<ParkingSlot, JButton> slotIdentifierToButton = new LinkedHashMap<>();

    public CarParkScreen() {
        this.carParkPanel.setLayout(new BorderLayout());
        this.carParkPanel.add(this.parkingSlotsPanel, BorderLayout.LINE_START);
        this.carParkPanel.add(this.optionsPanel, BorderLayout.LINE_END);

        this.parkingSlotsPanel.setPreferredSize(new Dimension(600, 500));
        this.optionsPanel.setPreferredSize(new Dimension(200, 500));
    }

    public JPanel getCarParkPanel() {
        return carParkPanel;
    }

    public JPanel getParkingSlotsPanel() {
        return parkingSlotsPanel;
    }

    public JPanel getOptionsPanel() {
        return optionsPanel;
    }

    public void createCarPark(int numStaffSlots, int numStudentSlots) {
        this.carPark.createParkingSlots(numStaffSlots, numStudentSlots);
        for (ParkingSlot parkingSlot: this.carPark.getParkingSlots().values()) {
            JButton parkingSlotButton = new JButton();
            this.slotIdentifierToButton.put(parkingSlot, parkingSlotButton);
            this.setParkingSlotButtonAppearance(parkingSlotButton, parkingSlot);
        }
    }

    private void setParkingSlotButtonAppearance(JButton parkingSlotButton, ParkingSlot parkingSlot) {
        final String parkingSlotIdentifier = parkingSlot.getIdentifier();
        final boolean isOccupied = parkingSlot.isSlotOccupied();

        parkingSlotButton.setMargin(new Insets(0, 0, 0, 0));
        parkingSlotButton.setText(parkingSlotIdentifier);

        // If parking slot is occupied, we set the colour of button to red. Otherwise, set colour of ,
        // button to green.
        if (isOccupied) {
            parkingSlotButton.setBackground(Color.RED);
        } else {
            parkingSlotButton.setBackground(Color.GREEN);
        }
    }

    public void setParkingSlotsPanel(int numStaffSlots, int numStudentSlots) {
        final int numRows = this.getNumRows(numStaffSlots, numStudentSlots);
        final int numColumns = NUM_SLOTS_PER_ROW;
        this.parkingSlotsPanel.setLayout(new GridLayout(numRows, numColumns));
        for (JButton parkingSlotButton: this.slotIdentifierToButton.values()) {
            this.parkingSlotsPanel.add(parkingSlotButton);
        }
    }

    private int getNumRows(int numStaffSlots, int numStudentSlots) {
        final int totalNumSlots = numStaffSlots + numStudentSlots;
        if (totalNumSlots % NUM_SLOTS_PER_ROW == 0) {
            return Math.floorDiv(totalNumSlots, NUM_SLOTS_PER_ROW);
        } else {
            return Math.floorDiv(totalNumSlots, NUM_SLOTS_PER_ROW) + 1;
        }
    }
}
