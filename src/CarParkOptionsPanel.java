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

    /**
     * Creates an instance of the CarParkOptionsPanel class. Also sets grid bag constraints anchor to north,
     * grid bag constraints weight y to 1 and sets the size of the options panel.
     */
    public CarParkOptionsPanel() {
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;

        final double width = CarParkScreen.OPTIONS_PANEL_WIDTH_MULTIPLIER * Screen.WIDTH;
        final int widthInt = (int) width;
        this.optionsPanel.setPreferredSize(new Dimension(widthInt, Screen.HEIGHT));
    }

    /**
     * Resizes the options panel based on the current size of the JFrame.
     * @param window: The JFrame used to calculate the size of the options panel.
     */
    public void setCarParkOptionsPanelSize(JFrame window) {
        final int currentFrameWidth = window.getWidth();
        final int currentFrameHeight = window.getWidth();
        final double newPanelWidth = CarParkScreen.OPTIONS_PANEL_WIDTH_MULTIPLIER * currentFrameWidth;
        final int newPanelWidthInt = (int) newPanelWidth;
        this.optionsPanel.setPreferredSize(new Dimension(newPanelWidthInt, currentFrameHeight));
    }

    /**
     * Gets the options panel.
     * @return options panel.
     */
    public JPanel getOptionsPanel() {
        return optionsPanel;
    }

    /**
     * Gets the option button to park a car.
     * @return the option button to park a car.
     */
    public JButton getParkCarButton() {
        return parkCarButton;
    }

    /**
     * Gets the option button to find a car.
     * @return the option button to find a car.
     */
    public JButton getFindCarButton() {
        return findCarButton;
    }

    /**
     * Gets the option button to remove a car.
     * @return the option button to remove a car.
     */
    public JButton getRemoveCarButton() {
        return removeCarButton;
    }

    /**
     * Gets the option button to add a parking slot to the car park.
     * @return the option button to add a parking slot to the car park.
     */
    public JButton getAddParkingSlotButton() {
        return addParkingSlotButton;
    }

    /**
     * Gets the option button to delete a parking slot from the car park.
     * @return the option button to delete a parking slot from the car park.
     */
    public JButton getDeleteParkingSlotButton() {
        return deleteParkingSlotButton;
    }

    /**
     * Creates and paints the title of the options header, as well as find car and add parking slot buttons,
     * This is only called once when the options panel is initially painted.
     */
    public void paintOptionsPanelHeader() {
        final JLabel optionsHeader = new JLabel("Options");
        optionsHeader.setFont(new Font(Constants.DEFAULT_FONT, Font.BOLD, Constants.OPTIONS_HEADER_FONT_SIZE));
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.optionsPanel.add(optionsHeader, gbc);
        this.paintButton(this.findCarButton, 3);
        this.paintButton(this.addParkingSlotButton, 4);
        this.findCarButton.setVisible(true);
        this.addParkingSlotButton.setVisible(true);
    }


    /**
     * Creates and paints the buttons used to carry out functionality in the options panel.
     * This is only called once when the options panel is initially painted.
     */
    public void paintParkingSlotOptions() {
        this.paintParkingSlotID();
        this.paintCarParkedInSlot();

        this.paintButton(this.parkCarButton, 5);
        this.paintButton(this.removeCarButton, 6);
        this.paintButton(this.deleteParkingSlotButton, 7);
    }

    /**
     * Repaint the options panel content.
     * @param parkingSlotInFocus: The parking slot we use to paint the relevant details onto the option panel.
     */
    public void repaintParkingSlotOptions(ParkingSlot parkingSlotInFocus) {
        this.parkingSlotIDLabel.setText(parkingSlotInFocus.getIdentifier());
        this.changeCarParkedInSlotLabel(parkingSlotInFocus);
        this.parkCarButton.setVisible(true);
        this.findCarButton.setVisible(true);
        this.removeCarButton.setVisible(true);
        this.addParkingSlotButton.setVisible(true);
        this.deleteParkingSlotButton.setVisible(true);
    }

    /**
     * This changes the message telling the user which car is parked in a parking slot depending on the parking slot
     * the user is currently focusing on. If no car is parked in the parking slot, it will display a corresponding
     * message saying so.
     * @param parkingSlotInFocus: The parking slot the user is currently focusing on.
     */
    private void changeCarParkedInSlotLabel(ParkingSlot parkingSlotInFocus) {
        Car carParked;
        if (parkingSlotInFocus.getCarParked() == null) {
            this.carParkedInSlotLabel.setText("There is no car parked here");
        } else {
            carParked = parkingSlotInFocus.getCarParked();
            final String carParkedRegistration = carParked.getRegistrationNumber();
            final String carOwner = carParked.getOwner();
            final String carOwnerType = carParked.getOwnerType();
            final String output = String.format("<html><p>%s is parked here. This car is owned by the %s named %s." +
                            "</p></html>",
                    carParkedRegistration, carOwnerType, carOwner);
            this.carParkedInSlotLabel.setText(output);
        }
    }

    /**
     * Unpaint the relevant information in the options panel. This is called when a user un-focuses a parking slot.
     */
    public void unpaintParkingSlotOptions() {
        this.parkingSlotIDLabel.setText("");
        this.carParkedInSlotLabel.setText("");
        this.parkCarButton.setVisible(false);
        this.removeCarButton.setVisible(false);
        this.deleteParkingSlotButton.setVisible(false);
    }

    /**
     * Add empty parking slot ID label to parking slot panel.
     */
    private void paintParkingSlotID() {
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.optionsPanel.add(this.parkingSlotIDLabel, this.gbc);
    }

    /**
     * Add empty label representing car parked in slot to options panel.
     */
    private void paintCarParkedInSlot() {
        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.optionsPanel.add(this.carParkedInSlotLabel, this.gbc);
    }

    /**
     * Adds a button to the options panel with a specified y position. The button defaults to no visibility.
     * @param button: The button to be painted onto the options panel.
     * @param gridy: The y value used for the grid bag constraints.
     */
    private void paintButton(JButton button, int gridy) {
        button.setVisible(false);
        this.gbc.gridx = 0;
        this.gbc.gridy = gridy;
        this.optionsPanel.add(button, this.gbc);
    }
}
