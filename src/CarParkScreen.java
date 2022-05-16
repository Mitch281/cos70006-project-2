import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;

public class CarParkScreen {

    private static final double PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER = 0.7;
    private static final double OPTIONS_PANEL_WIDTH_MULTIPLIER = 1 - PARKING_SLOTS_PANEl_WIDTH_MULTIPLIER;

    private final JPanel carParkPanel = new JPanel();
    private final ParkingSlotsPanel parkingSlotsPanel = new ParkingSlotsPanel();

    private final GridBagConstraints optionsPanelgbc = new GridBagConstraints();
    private final JPanel optionsPanel = new JPanel(new GridBagLayout());

    private final CarPark carPark = new CarPark();
    private final LinkedHashMap<ParkingSlot, JButton> parkingSlotToButton = new LinkedHashMap<>();

    public CarParkScreen() {
        this.carParkPanel.setLayout(new BorderLayout());
        this.carParkPanel.add(this.parkingSlotsPanel.getParkingSlotsPanel(), BorderLayout.LINE_START);
//        this.carParkPanel.add(this.optionsPanel, BorderLayout.LINE_END);
//
//        this.optionsPanel.setPreferredSize(new Dimension(200, 500));
//        this.paintOptionsPanel();
    }

    public void paintParkingSlots(int numStaffSlots, int numStudentSlots) {
        this.parkingSlotsPanel.paintParkingSlots(this.parkingSlotToButton, this.carPark, numStaffSlots, numStudentSlots);
        this.parkingSlotsPanel.setLayout(this.parkingSlotToButton, numStaffSlots, numStudentSlots);
    }

    public JPanel getCarParkPanel() {
        return carParkPanel;
    }

//    private void paintOptionsPanel() {
//        this.optionsPanelgbc.gridx = 0;
//        this.optionsPanelgbc.gridy = 0;
//        final JLabel optionsPanelHeader = new JLabel("Options");
//        this.optionsPanel.add(optionsPanelHeader, optionsPanelgbc);
//        this.optionsPanel.setVisible(true);
//    }

    // TODO: Handle screen resizing so that we can make border layout responsive.

}
