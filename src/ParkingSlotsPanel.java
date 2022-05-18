import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
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
            final String parkingSlotIdentifier = parkingSlot.getIdentifier();
            parkingSlotButton.setName(parkingSlotIdentifier);
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

    // TODO: Improve how this is done!! It is very ugly atm.
    public void addParkingSlot(ParkingSlot newParkingSlot, LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton, JButton newParkingSlotButton) {
        final int currentNumParkingSlots = this.parkingSlotsPanel.getComponentCount();
        final int xPos = this.getNumRows(currentNumParkingSlots) - 1;
        final int yPos = currentNumParkingSlots % NUM_SLOTS_PER_ROW;

        final String newParkingSlotIdentifier = newParkingSlot.getIdentifier();
        newParkingSlotButton.setName(newParkingSlotIdentifier);
        newParkingSlotButton.setMargin(new Insets(0, 0, 0, 0));
        this.parkingSlotsPanel.add(newParkingSlotButton, xPos, yPos);
        parkingSlotToButton.put(newParkingSlot, newParkingSlotButton);

        final boolean isParkingSlotOccupied = newParkingSlot.isSlotOccupied();
        if (isParkingSlotOccupied) {
            newParkingSlotButton.setBackground(Color.RED);
        } else {
            newParkingSlotButton.setBackground(Color.GREEN);
        }

        this.parkingSlotsPanel.validate();
        this.parkingSlotsPanel.repaint();
    }

    public void deleteParkingSlot(String parkingSlotIdentifier) {
        final Component[] parkingSlotPanelComponents = this.parkingSlotsPanel.getComponents();

        for (Component component: parkingSlotPanelComponents) {
            if (component.getName().equals(parkingSlotIdentifier)) {
                this.parkingSlotsPanel.remove(component);
                this.parkingSlotsPanel.validate();
                this.parkingSlotsPanel.repaint();
                return;
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

    private int getNumRows(int totalNumSlots) {
        if (totalNumSlots % NUM_SLOTS_PER_ROW == 0) {
            return Math.floorDiv(totalNumSlots, NUM_SLOTS_PER_ROW);
        } else {
            return Math.floorDiv(totalNumSlots, NUM_SLOTS_PER_ROW) + 1;
        }
    }

    public void handleAction(String action, ParkingSlot parkingSlotChanged) {
        switch (action) {
            case "park car" -> {
                final HashMap<String, Component> identifierToComponent = Util.createNamesToComponentsMap(new HashMap<String, Component>(), this.parkingSlotsPanel);
                JButton parkingSlotButton = (JButton) identifierToComponent.get(parkingSlotChanged.getIdentifier());
                parkingSlotButton.setBackground(Color.RED);
            }

            case "remove car" -> {
                final HashMap<String, Component> identifierToComponent = Util.createNamesToComponentsMap(new HashMap<String, Component>(), this.parkingSlotsPanel);
                JButton parkingSlotButton = (JButton) identifierToComponent.get(parkingSlotChanged.getIdentifier());
                parkingSlotButton.setBackground(Color.GREEN);
            }
        }
    }
}
