import javax.swing.*;
import java.awt.*;

public class CarParkOptionsPanel {

    private final JPanel optionsPanel = new JPanel(new GridBagLayout());
    private final GridBagConstraints gbc = new GridBagConstraints();

    private final JLabel parkingSlotIDLabel = new JLabel();
    private final JLabel carParkedInSlotLabel = new JLabel();

    private final JButton parkCarButton = new JButton("Park Car");
    private final JButton findCarButton = new JButton("Find Car");
    private final JButton removeCarButton = new JButton("Remove Car");
    private final JButton addParkingSlotButton = new JButton("Add Parking Slot");
    private final JButton deleteParkingSlotButton = new JButton("Delete Parking Slot");

    public CarParkOptionsPanel() {
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;
        this.optionsPanel.setPreferredSize(new Dimension(200, 500));
    }

    public JPanel getOptionsPanel() {
        return optionsPanel;
    }

    public JLabel getParkingSlotIDLabel() {
        return parkingSlotIDLabel;
    }

    public JLabel getCarParkedInSlotLabel() {
        return carParkedInSlotLabel;
    }

    public JButton getParkCarButton() {
        return parkCarButton;
    }

    public JButton getFindCarButton() {
        return findCarButton;
    }

    public JButton getRemoveCarButton() {
        return removeCarButton;
    }

    public JButton getAddParkingSlotButton() {
        return addParkingSlotButton;
    }

    public JButton getDeleteParkingSlotButton() {
        return deleteParkingSlotButton;
    }

    public void paintOptionsPanelHeader() {
        final JLabel optionsHeader = new JLabel("Options");
        optionsHeader.setFont(new Font(Constants.DEFAULT_FONT, Font.BOLD, Constants.OPTIONS_HEADER_FONT_SIZE));
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.optionsPanel.add(optionsHeader, gbc);
    }

    // This is needed to paint the options panel the first time. We also add action listeners to open dialogs here.
    public void paintParkingSlotOptions() {
        this.paintParkingSlotID();
        this.paintCarParkedInSlot();

        int gridyForButtons = 3;

        this.paintButton(this.parkCarButton, gridyForButtons);
        gridyForButtons++;

        this.paintButton(this.findCarButton, gridyForButtons);
        gridyForButtons++;

        this.paintButton(this.removeCarButton, gridyForButtons);
        gridyForButtons++;

        this.paintButton(this.addParkingSlotButton, gridyForButtons);
        gridyForButtons++;

        this.paintButton(this.deleteParkingSlotButton, gridyForButtons);
    }

    // This is needed to repaint the options panel (after paint has been called which adds necessary components to panel).
    public void repaintParkingSlotOptions(ParkingSlot parkingSlotInFocus) {
        this.parkingSlotIDLabel.setText(parkingSlotInFocus.getIdentifier());
        this.changeCarParkedInSlotLabel(parkingSlotInFocus);
        this.parkCarButton.setVisible(true);
        this.findCarButton.setVisible(true);
        this.removeCarButton.setVisible(true);
        this.addParkingSlotButton.setVisible(true);
        this.deleteParkingSlotButton.setVisible(true);
    }

    private void changeCarParkedInSlotLabel(ParkingSlot parkingSlotInFocus) {
        Car carParked;
        if (parkingSlotInFocus.getCarParked() == null) {
            this.carParkedInSlotLabel.setText("There is no car parked here");
        } else {
            carParked = parkingSlotInFocus.getCarParked();
            String carParkedRegistration = carParked.getRegistrationNumber();
            this.carParkedInSlotLabel.setText(carParkedRegistration + "is parked here.");
        }
    }

    public void unpaintParkingSlotOptions() {
        this.parkingSlotIDLabel.setText("");
        this.carParkedInSlotLabel.setText("");
        this.parkCarButton.setVisible(false);
        this.findCarButton.setVisible(false);
        this.removeCarButton.setVisible(false);
        this.addParkingSlotButton.setVisible(false);
        this.deleteParkingSlotButton.setVisible(false);
    }

    private void paintParkingSlotID() {
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.optionsPanel.add(this.parkingSlotIDLabel, this.gbc);
    }

    private void paintCarParkedInSlot() {
        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.optionsPanel.add(this.carParkedInSlotLabel, this.gbc);
    }

    private void paintButton(JButton button, int gridy) {
        button.setVisible(false);
        this.gbc.gridx = 0;
        this.gbc.gridy = gridy;
        this.optionsPanel.add(button, this.gbc);
    }
}
