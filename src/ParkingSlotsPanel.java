import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParkingSlotsPanel {
    private static final int NUM_SLOTS_PER_ROW = 15;

    private final JPanel parkingSlotsPanel = new JPanel();

    public ParkingSlotsPanel() {
        final double width = Screen.WIDTH * CarParkScreen.PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER;
        final int widthInt = (int) width;
        this.parkingSlotsPanel.setPreferredSize(new Dimension(widthInt, Screen.HEIGHT));
    }

    // Change width of parking slot panel whenever window is resized.
    public void setParkingSlotsPanelSize(JFrame window) {
        final int currentFrameWidth = window.getWidth();
        final int currentFrameHeight = window.getHeight();
        final double newPanelWidth = (Double) CarParkScreen.PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER * currentFrameWidth;
        final int newPanelWidthInt = (int) newPanelWidth;
        this.parkingSlotsPanel.setPreferredSize(new Dimension(newPanelWidthInt, currentFrameHeight));
    }

    public JPanel getParkingSlotsPanel() {
        return parkingSlotsPanel;
    }

    private void populateParkingSlotToButton(LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton, CarPark carPark, int numStaffSlots, int numStudentSlots) {
        carPark.createParkingSlots(numStaffSlots, numStudentSlots);
        for (ParkingSlot parkingSlot : carPark.getParkingSlots().values()) {
            final JButton parkingSlotButton = new JButton();
            final String parkingSlotIdentifier = parkingSlot.getIdentifier();
            parkingSlotButton.setName(parkingSlotIdentifier);
            parkingSlotToButton.put(parkingSlot, parkingSlotButton);
        }
    }

    public void paintParkingSlots(LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton, CarPark carPark, int numStaffSlots, int numStudentSlots) {
        this.populateParkingSlotToButton(parkingSlotToButton, carPark, numStaffSlots, numStudentSlots);
        for (Map.Entry<ParkingSlot, JButton> entry : parkingSlotToButton.entrySet()) {
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

    public void parkCar(ParkingSlot parkingSlotChanged) {
        final HashMap<String, Component> identifierToComponent = Util.createNamesToComponentsMap(new HashMap<String, Component>(), this.parkingSlotsPanel);
        JButton parkingSlotButton = (JButton) identifierToComponent.get(parkingSlotChanged.getIdentifier());
        parkingSlotButton.setBackground(Color.RED);
    }

    public void removeCar(ParkingSlot parkingSlotChanged) {
        final HashMap<String, Component> identifierToComponent = Util.createNamesToComponentsMap(new HashMap<String, Component>(), this.parkingSlotsPanel);
        JButton parkingSlotButton = (JButton) identifierToComponent.get(parkingSlotChanged.getIdentifier());
        parkingSlotButton.setBackground(Color.GREEN);
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

        for (Component component : parkingSlotPanelComponents) {
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
        for (JButton parkingSlotButton : parkingSlotToButton.values()) {
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
}

