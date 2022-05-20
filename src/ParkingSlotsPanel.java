import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParkingSlotsPanel {
    private static final int NUM_SLOTS_PER_ROW = 15;

    private final JPanel parkingSlotsPanel = new JPanel();

    /**
     * Creates an instance of ParkingSlotsPanel. Also sets width and height of parking slots panel based on the
     * initial width and height of the JFrame.
     */
    public ParkingSlotsPanel() {
        final double width = Screen.WIDTH * CarParkScreen.PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER;
        final int widthInt = (int) width;
        this.parkingSlotsPanel.setPreferredSize(new Dimension(widthInt, Screen.HEIGHT));
    }

    /**
     * Changes the width of the panel whenever the JFrame is resized.
     * @param window: The JFrame that is resized.
     */
    public void setParkingSlotsPanelSize(JFrame window) {
        final int currentFrameWidth = window.getWidth();
        final int currentFrameHeight = window.getHeight();
        final double newPanelWidth = CarParkScreen.PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER * currentFrameWidth;
        final int newPanelWidthInt = (int) newPanelWidth;
        this.parkingSlotsPanel.setPreferredSize(new Dimension(newPanelWidthInt, currentFrameHeight));
    }

    /**
     * Gets the parking slots panel.
     * @return the parking slots panel.
     */
    public JPanel getParkingSlotsPanel() {
        return parkingSlotsPanel;
    }

    /**
     * Creates and names the JButtons for the car park. Puts the JButtons into a LinkedHashMap which maps every parking slot
     * to its corresponding JButton.
     * @param parkingSlotToButton: Maps parking slots to JButtons.
     * @param carPark: The car park used to create the JButtons.
     * @param numStaffSlots: The number of staff slots in the car park.
     * @param numStudentSlots: The number of student slots in the car park.
     */
    private void populateParkingSlotToButton(LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton, CarPark carPark, int numStaffSlots, int numStudentSlots) {
        carPark.createParkingSlots(numStaffSlots, numStudentSlots);
        for (ParkingSlot parkingSlot : carPark.getParkingSlots().values()) {
            final JButton parkingSlotButton = new JButton();
            final String parkingSlotIdentifier = parkingSlot.getIdentifier();
            parkingSlotButton.setName(parkingSlotIdentifier);
            parkingSlotToButton.put(parkingSlot, parkingSlotButton);
        }
    }

    /**
     * Paints the parking slot JButtons with the appropriate margin, text and background colour.
     * @param parkingSlotToButton: Maps parking slots to corresponding JButtons.
     * @param carPark: The car park used to create JButtons.
     * @param numStaffSlots: The number of staff slots in the car park.
     * @param numStudentSlots: The number of student slots in the car park.
     */
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

    /**
     * Sets the specified parking slot's background colour to red in response to a car being parked in the
     * parking slot.
     * @param parkingSlotChanged: The parking slot the car was parked in.
     */
    public void setParkingSlotBackgroundToRed(ParkingSlot parkingSlotChanged) {
        final HashMap<String, Component> identifierToComponent = Util.createNamesToComponentsMap(new HashMap<>(), this.parkingSlotsPanel);
        JButton parkingSlotButton = (JButton) identifierToComponent.get(parkingSlotChanged.getIdentifier());
        parkingSlotButton.setBackground(Color.RED);
    }

    /**
     * Sets the specified parking slot's background colour to green in response to a car being removed from the
     * parking slot.
     * @param parkingSlotChanged: The parking slot from which the car was removed.
     */
    public void setParkingSlotBackgroundToGreen(ParkingSlot parkingSlotChanged) {
        final HashMap<String, Component> identifierToComponent = Util.createNamesToComponentsMap(new HashMap<>(), this.parkingSlotsPanel);
        JButton parkingSlotButton = (JButton) identifierToComponent.get(parkingSlotChanged.getIdentifier());
        parkingSlotButton.setBackground(Color.GREEN);
    }

    /**
     * Paints a new parking slot into the car park panel with the appropriate background colour (green). Additionally
     * updates the parking slot to JButton map.
     * @param newParkingSlot: The parking slot added.
     * @param parkingSlotToButton: Maps parking slots to JButtons.
     * @param newParkingSlotButton: The new parking slot button added to the car park panel.
     */
    public void paintNewParkingSlot(ParkingSlot newParkingSlot, LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton, JButton newParkingSlotButton) {

        final String newParkingSlotIdentifier = newParkingSlot.getIdentifier();
        newParkingSlotButton.setName(newParkingSlotIdentifier);
        newParkingSlotButton.setMargin(new Insets(0, 0, 0, 0));
        this.parkingSlotsPanel.add(newParkingSlotButton);

        parkingSlotToButton.put(newParkingSlot, newParkingSlotButton);

        newParkingSlotButton.setBackground(Color.GREEN);

        this.parkingSlotsPanel.validate();
        this.parkingSlotsPanel.repaint();
    }

    /**
     * Removes the parking slot button (retrieved from the parking slot identifier) from the parking slots
     * JPanel.
     * @param parkingSlotIdentifier: The parking slot identifier that was removed from the car park.
     */
    public void removeParkingSlotButton(String parkingSlotIdentifier) {
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

    /**
     * Adds the parking slot JButtons to the parking slot panel.
     * @param parkingSlotToButton: Maps parking slots to JButtons.
     * @param numStaffSlots: The number of staff slots in the car park.
     * @param numStudentSlots: The number of student slots in the car park.
     */
    public void setLayout(LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton, int numStaffSlots, int numStudentSlots) {
        final int numGridRows = this.getNumRows(numStaffSlots, numStudentSlots);
        final int numGridColumns = NUM_SLOTS_PER_ROW;
        this.parkingSlotsPanel.setLayout(new GridLayout(numGridRows, numGridColumns));
        for (JButton parkingSlotButton : parkingSlotToButton.values()) {
            this.parkingSlotsPanel.add(parkingSlotButton);
        }
    }

    /**
     * Gets the number of grid rows in the parking slots panel grid layout.
     * @param numStaffSlots: The number of staff slots in the car park.
     * @param numStudentSlots: The number of student slots in the car park.
     * @return the number of grid rows in the parking slots panel grid layout.
     */
    private int getNumRows(int numStaffSlots, int numStudentSlots) {
        final int totalNumSlots = numStaffSlots + numStudentSlots;
        if (totalNumSlots % NUM_SLOTS_PER_ROW == 0) {
            return Math.floorDiv(totalNumSlots, NUM_SLOTS_PER_ROW);
        } else {
            return Math.floorDiv(totalNumSlots, NUM_SLOTS_PER_ROW) + 1;
        }
    }
}

