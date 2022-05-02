import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Screen {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    private final JFrame window;

    public Screen() {
        this.window = new JFrame("Parking Spot System");
    }

    public void run() {
        this.window.setSize(WIDTH, HEIGHT);

        NumSlotsTextFields numSlotsTextFields = new NumSlotsTextFields();
        numSlotsTextFields.render(this.window);

        this.window.setLayout(null);
        this.window.setVisible(true);
    }
}
