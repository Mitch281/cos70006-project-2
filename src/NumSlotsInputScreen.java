import javax.swing.*;
import java.awt.*;

public class NumSlotsInputScreen {

    private final JPanel inputPanel = new JPanel();

    private final JLabel instructions = new JLabel("Please enter the number of staff and student parking slots" +
            " you want to create.");
    private final JTextField numStaffSlotsField = new JTextField();
    private final JTextField numStudentSlotsField = new JTextField();
    private final JButton submitSlotNumbersButton = new JButton("Start");
    private final GridBagLayout gridBagLayout = new GridBagLayout();
    private final GridBagConstraints gridBagConstraints = new GridBagConstraints();

    private final JLabel errorMessage = new JLabel();

    public NumSlotsInputScreen() {
        this.inputPanel.setLayout(gridBagLayout);
        this.changeLayout();
    }

    public JPanel getInputPanel() {
        return inputPanel;
    }

    public JTextField getNumStaffSlotsField() {
        return numStaffSlotsField;
    }

    public JTextField getNumStudentSlotsField() {
        return numStudentSlotsField;
    }

    public JButton getSubmitSlotNumbersButton() {
        return submitSlotNumbersButton;
    }

    public GridBagLayout getGridBagLayout() {
        return gridBagLayout;
    }

    public GridBagConstraints getGridBagConstraints() {
        return gridBagConstraints;
    }

    public JLabel getInstructions() {
        return instructions;
    }

    public JLabel getErrorMessage() {
        return errorMessage;
    }

    private void changeLayout() {

        // Add margins to top of components.
        this.gridBagConstraints.insets = new Insets(5, 0, 0, 0);

        // Change colour of error message.
        this.errorMessage.setForeground(Color.RED);

        this.gridBagConstraints.gridx = 0;
        this.gridBagConstraints.gridy = 0;
        this.inputPanel.add(this.instructions, this.gridBagConstraints);

        this.gridBagConstraints.gridx = 0;
        this.gridBagConstraints.gridy = 1;
        this.inputPanel.add(this.numStaffSlotsField, this.gridBagConstraints);

        this.gridBagConstraints.gridx = 0;
        this.gridBagConstraints.gridy = 2;
        this.inputPanel.add(this.numStudentSlotsField, this.gridBagConstraints);

        this.gridBagConstraints.gridx = 0;
        this.gridBagConstraints.gridy = 3;
        this.inputPanel.add(this.submitSlotNumbersButton, this.gridBagConstraints);

        this.gridBagConstraints.gridx = 0;
        this.gridBagConstraints.gridy = 4;
        this.inputPanel.add(this.errorMessage, this.gridBagConstraints);
    }
}
