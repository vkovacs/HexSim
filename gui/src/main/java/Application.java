import graphics.HexGridPanel;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Application extends JFrame {

    public Application() {
        setTitle("Hexagon Drawing Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HexGridPanel hexGridPanel = new HexGridPanel();
        add(hexGridPanel);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Application app = new Application();
            app.setVisible(true);
        });
    }
}