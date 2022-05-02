import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumSlotsTextFields implements ActionListener {

    private JTextField numStaffSlotsField;
    private JTextField numStudentSlotsField;

    private static final int WIDTH = 200;
    private static final int HEIGHT = 30;

    public NumSlotsTextFields() {
        this.numStaffSlotsField = new JTextField();
        this.numStudentSlotsField = new JTextField();

        numStaffSlotsField.addActionListener(this);
        numStudentSlotsField.addActionListener(this);
    }

    public JTextField getNumStaffSlotsField() {
        return numStaffSlotsField;
    }

    public void setNumStaffSlotsField(JTextField numStaffSlotsField) {
        this.numStaffSlotsField = numStaffSlotsField;
    }

    public JTextField getNumStudentSlotsField() {
        return numStudentSlotsField;
    }

    public void setNumStudentSlotsField(JTextField numStudentSlotsField) {
        this.numStudentSlotsField = numStudentSlotsField;
    }

    public void render(JFrame window) {
        final int xCoord = Math.floorDiv(Screen.WIDTH, 2) - Math.floorDiv(WIDTH, 2);
        final int staffFieldY = 50;
        final int studentFieldY = 100;

        this.numStaffSlotsField.setBounds(xCoord,staffFieldY, WIDTH, HEIGHT);
        window.add(numStaffSlotsField);
        this.numStudentSlotsField.setBounds(xCoord,studentFieldY, WIDTH, HEIGHT);
        window.add(numStudentSlotsField);
    }

    public void unRender() {
        Container parent = numStaffSlotsField.getParent();
        parent.remove(numStaffSlotsField);
        parent.remove(numStudentSlotsField);
        parent.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String numStaffSlots = this.numStaffSlotsField.getText();
        String numStudentSlots = this.numStudentSlotsField.getText();
        this.unRender();
    }
}
