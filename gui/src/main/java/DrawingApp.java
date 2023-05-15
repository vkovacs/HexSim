import graphics.DrawingPanel;

import javax.swing.*;

public class DrawingApp extends JFrame {

    public DrawingApp() {
        setTitle("Hexagon Drawing Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawingPanel drawingPanel = new DrawingPanel();
        add(drawingPanel);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DrawingApp app = new DrawingApp();
            app.setVisible(true);
        });
    }
}