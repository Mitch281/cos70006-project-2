import javax.swing.*;
import java.awt.*;

/**
 * Represents a screen made up of two pages (slot input page and car park page).
 */
public class ParentWindow {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    public static Integer cardNumber = 1;

    private final JFrame window = new JFrame("Parking Spot System");
    private final CardLayout cardLayout = new CardLayout();

    // The parent panel for both the car park screen and the slot input screen.
    private final JPanel contentPanel = new JPanel(cardLayout);

    private final NumSlotsInputScreen numSlotsInputScreen = new NumSlotsInputScreen();
    private final CarParkScreen carParkScreen = new CarParkScreen();

    /**
     * Creates an instance of ParentWindow. Specifies width and height of main JFrame and sets default on close behaviour
     * to exit on close.
     */
    public ParentWindow() {
        this.window.setSize(WIDTH, HEIGHT);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Renders the initial screen and makes window visible.
     */
    public void run() {
        runSlotInputScreen();

        this.contentPanel.add(numSlotsInputScreen.getInputPanel(), "1");
        this.contentPanel.add(carParkScreen.getCarParkPanel(), "2");
        this.cardLayout.show(this.contentPanel, String.valueOf(cardNumber));
        this.window.setVisible(true);
    }

    /**
     * Adds action listener to the start button. Also adds the content panel to content pain of the main
     * JFrame.
     */
    private void runSlotInputScreen() {

        // Action listener on button to start program.
        this.numSlotsInputScreen.getSubmitSlotNumbersButton().addActionListener(e -> getSlotNumbersInput());

        this.window.getContentPane().add(contentPanel);
    }

    /**
     * Gets and validates the number of slots entered by the user when the start button is pressed. If validation
     * is successful, it will change the content panel to render the car park screen.
     */
    private void getSlotNumbersInput() {
        String staffSlotsInput = this.numSlotsInputScreen.getNumStaffSlotsField().getText();
        String studentSlotsInput = this.numSlotsInputScreen.getNumStudentSlotsField().getText();

        try {
            int numStaffSlots = Integer.parseInt(staffSlotsInput);
            int numStudentSlots = Integer.parseInt(studentSlotsInput);
            if (numStaffSlots + numStudentSlots > CarPark.MAX_NUMBER_PARKING_SLOTS) {
                this.numSlotsInputScreen.getErrorMessage().setText("You cannot enter more than 999 " +
                        "total slots!");
            }
            else if (numStaffSlots < 0 || numStudentSlots < 0) {
                this.numSlotsInputScreen.getErrorMessage().setText("Please enter only positive numbers for" +
                        " the number of parking slots!");
            } else {
                // User has successfully entered the right amount of slots.
                changePanel();
                this.carParkScreen.addScreenResizeListener();
                this.carParkScreen.paintParkingSlots(numStaffSlots, numStudentSlots);
                this.carParkScreen.initialPaintOptionsPanel();
            }
        } catch (NumberFormatException exception) {
            this.numSlotsInputScreen.getErrorMessage().setText("Please enter positive numbers for the " +
                    "number of slots! Note that you cannot enter more than 999 slots in total.");
        }
    }

    /**
     * Changes panel from home screen (inputting number of slots) to car park screen.
     */
    private void changePanel() {
        cardNumber++;
        this.cardLayout.show(this.contentPanel, String.valueOf(cardNumber));
    }
}
