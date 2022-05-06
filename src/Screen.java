import javax.swing.*;
import java.awt.*;

public class Screen {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    public static Integer cardNumber = 1;

    private final JFrame window = new JFrame("Parking Spot System");
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final JPanel testPanel = new JPanel(cardLayout);

    private final NumSlotsInputScreen numSlotsInputScreen = new NumSlotsInputScreen();

    public Screen() {
        this.window.setSize(WIDTH, HEIGHT);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void run() {
        runSlotInputScreen();

        window.setVisible(true);
    }

    private void runSlotInputScreen() {
        this.numSlotsInputScreen.getNumStaffSlotsField().setPreferredSize(new Dimension(100, 30));
        this.numSlotsInputScreen.getNumStudentSlotsField().setPreferredSize(new Dimension(100, 30));

        // Action listener on button to start program.
        this.numSlotsInputScreen.getSubmitSlotNumbersButton().addActionListener(e -> {
            getSlotNumbersInput();
        });

        this.contentPanel.add(numSlotsInputScreen.getInputPanel(), "1");
        this.contentPanel.add(testPanel, "2");
        this.cardLayout.show(this.contentPanel, String.valueOf(cardNumber));
        this.window.getContentPane().add(contentPanel);
    }

    private void getSlotNumbersInput() {
        String staffSlotsInput = this.numSlotsInputScreen.getNumStaffSlotsField().getText();
        String studentSlotsInput = this.numSlotsInputScreen.getNumStudentSlotsField().getText();

        try {
            Integer numStaffSlots = Integer.parseInt(staffSlotsInput);
            Integer numStudentSlots = Integer.parseInt(studentSlotsInput);
            if (numStaffSlots + numStudentSlots > 999) {
                this.numSlotsInputScreen.getErrorMessage().setText("You cannot enter more than 999 " +
                        "total slots!");
            } else {
                changePanel();
            }
        } catch (NumberFormatException exception) {
            this.numSlotsInputScreen.getErrorMessage().setText("Please enter positive numbers for the " +
                    "number of slots! Note that you cannot enter more than 999 slots in total.");
        }
    }

    private void changePanel() {
        cardNumber++;
        this.cardLayout.show(this.contentPanel, String.valueOf(cardNumber));
    }
}
