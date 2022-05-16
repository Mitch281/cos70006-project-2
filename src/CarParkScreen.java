import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;

public class CarParkScreen {

    private static final double PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER = 0.7;
    private static final double OPTIONS_PANEL_WIDTH_MULTIPLIER = 1 - PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER;

    private final JPanel carParkPanel = new JPanel();
    private final ParkingSlotsPanel parkingSlotsPanel = new ParkingSlotsPanel();
    private final CarParkOptionsPanel optionsPanel = new CarParkOptionsPanel();

    private final CarPark carPark = new CarPark();
    private final LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton = new LinkedHashMap<>();

    public CarParkScreen() {
        this.carParkPanel.setLayout(new BorderLayout());
        this.carParkPanel.add(this.parkingSlotsPanel.getParkingSlotsPanel(), BorderLayout.LINE_START);
        this.carParkPanel.add(this.optionsPanel.getOptionsPanel(), BorderLayout.LINE_END);
    }

    public void paintParkingSlots(int numStaffSlots, int numStudentSlots) {
        this.parkingSlotsPanel.paintParkingSlots(this.parkingSlotToButton, this.carPark, numStaffSlots, numStudentSlots);
        this.parkingSlotsPanel.setLayout(this.parkingSlotToButton, numStaffSlots, numStudentSlots);
    }

    public void paintOptionsPanelHeader() {
        this.optionsPanel.paintOptionsPanelHeader();
    }

    public JPanel getCarParkPanel() {
        return carParkPanel;
    }

    // TODO: Handle screen resizing so that we can make border layout responsive.

}
