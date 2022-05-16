import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParkingSlotsPanel {
    private static final int NUM_SLOTS_PER_ROW = 15;

    private final JPanel parkingSlotsPanel = new JPanel();

    public ParkingSlotsPanel() {
        this.parkingSlotsPanel.setPreferredSize(new Dimension(600, 500));
    }

    public JPanel getParkingSlotsPanel() {
        return parkingSlotsPanel;
    }

    private void populateParkingSlotToButton(LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton, CarPark carPark, int numStaffSlots, int numStudentSlots) {
        carPark.createParkingSlots(numStaffSlots, numStudentSlots);
        for (ParkingSlot parkingSlot: carPark.getParkingSlots().values()) {
            final JButton parkingSlotButton = new JButton();
            parkingSlotToButton.put(parkingSlot, parkingSlotButton);
        }
    }

    public void paintParkingSlots(LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton, CarPark carPark, int numStaffSlots, int numStudentSlots) {
        this.populateParkingSlotToButton(parkingSlotToButton, carPark, numStaffSlots, numStudentSlots);
        for (Map.Entry<ParkingSlot, JButton> entry: parkingSlotToButton.entrySet()) {
            final ParkingSlot parkingSlot = entry.getKey();
            final JButton parkingSlotButton = entry.getValue();

            String parkingSlotIdentifier = parkingSlot.getIdentifier();
            boolean isParkingSlotOccupied = parkingSlot.isSlotOccupied();

            parkingSlotButton.setText(parkingSlotIdentifier);
            parkingSlotButton.setMargin(new Insets(0, 0, 0, 0));
            if (isParkingSlotOccupied) {
                parkingSlotButton.setBackground(Color.RED);
            } else {
                parkingSlotButton.setBackground(Color.GREEN);
            }
        }
    }

    public void setLayout(LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton, int numStaffSlots, int numStudentSlots) {
        final int numGridRows = this.getNumRows(numStaffSlots, numStudentSlots);
        final int numGridColumns = NUM_SLOTS_PER_ROW;
        this.parkingSlotsPanel.setLayout(new GridLayout(numGridRows, numGridColumns));
        for (JButton parkingSlotButton: parkingSlotToButton.values()) {
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
