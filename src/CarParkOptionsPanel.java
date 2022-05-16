import javax.swing.*;
import java.awt.*;

public class CarParkOptionsPanel {

    private final JPanel optionsPanel = new JPanel(new GridBagLayout());
    private final GridBagConstraints gbc = new GridBagConstraints();

    private final JLabel parkingSlotID = new JLabel();

    public CarParkOptionsPanel() {
        this.optionsPanel.setPreferredSize(new Dimension(200, 500));
    }

    public JPanel getOptionsPanel() {
        return optionsPanel;
    }

    public void paintOptionsPanelHeader() {
        final JLabel optionsHeader = new JLabel("Options");
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.optionsPanel.add(optionsHeader, gbc);
    }

    public void paintParkingSlotID() {
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.optionsPanel.add(this.parkingSlotID, gbc);
    }

    public void setTextOfSlotLabel(String parkingSlotIdentifier) {
        this.parkingSlotID.setText(parkingSlotIdentifier);
    }
}
