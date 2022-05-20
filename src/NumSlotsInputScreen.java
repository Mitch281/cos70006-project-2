import javax.swing.*;
import java.awt.*;

/**
 * The screen to input the number of staff and student slots the user wants to create.
 */
public class NumSlotsInputScreen {

    private final JPanel inputPanel = new JPanel();

    private final JLabel instructions = new JLabel("Please enter the number of staff and student parking slots" +
            " you want to create.");

    private final JLabel numStaffSlotsLabel = new JLabel("Number of staff slots: ");
    private final JTextField numStaffSlotsField = new JTextField();

    private final JLabel numStudentSlotsLabel = new JLabel("Number of student slots: ");
    private final JTextField numStudentSlotsField = new JTextField();

    private final JButton submitSlotNumbersButton = new JButton("Start");
    private final GridBagConstraints gbc = new GridBagConstraints();

    private final JLabel errorMessage = new JLabel();

    /**
     * Creates an instance of NumSlotsInputScreen. Also sets grid bad layout for input panel and adds
     * relevant components to input panel.
     */
    public NumSlotsInputScreen() {
        this.inputPanel.setLayout(new GridBagLayout());
        this.setLayout();
    }

    /**
     * Gets input panel.
     * @return input panel.
     */
    public JPanel getInputPanel() {
        return inputPanel;
    }

    /**
     * Gets number of staff slots text field.
     * @return number of staff slots text field.
     */
    public JTextField getNumStaffSlotsField() {
        return numStaffSlotsField;
    }

    /**
     * Gets number of student slots text field.
     * @return number of student slots text field.
     */
    public JTextField getNumStudentSlotsField() {
        return numStudentSlotsField;
    }

    /**
     * Gets submit button.
     * @return submit button.
     */
    public JButton getSubmitSlotNumbersButton() {
        return submitSlotNumbersButton;
    }

    /**
     * Get error message label.
     * @return error message label.
     */
    public JLabel getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the layout of the input panel.
     */
    private void setLayout() {
        this.numStaffSlotsField.setPreferredSize(new Dimension(100, 30));
        this.numStudentSlotsField.setPreferredSize(new Dimension(100, 30));

        // Add margins to top of components.
        this.gbc.insets = new Insets(5, 0, 0, 0);

        // Change colour of error message.
        this.errorMessage.setForeground(Color.RED);

        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.inputPanel.add(this.instructions, this.gbc);

        final JPanel numStaffSlotsPanel = new JPanel();
        numStaffSlotsPanel.add(this.numStaffSlotsLabel);
        numStaffSlotsPanel.add(this.numStaffSlotsField);
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.inputPanel.add(numStaffSlotsPanel, this.gbc);

        final JPanel numStudentSlotsPanel = new JPanel();
        numStudentSlotsPanel.add(this.numStudentSlotsLabel);
        numStudentSlotsPanel.add(this.numStudentSlotsField);
        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.inputPanel.add(numStudentSlotsPanel, this.gbc);

        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.inputPanel.add(this.submitSlotNumbersButton, this.gbc);

        this.gbc.gridx = 0;
        this.gbc.gridy = 4;
        this.inputPanel.add(this.errorMessage, this.gbc);
    }
}
