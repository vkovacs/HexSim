package hu.crs.hex.simulation;

import hu.crs.hex.simulation.foxrabbitsimulation.FoxRabbitSimulation;
import hu.crs.hex.simulation.graphics.HexGridPanel;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Toolkit;

public class Application extends JFrame {
    private final Simulation simulation = FoxRabbitSimulation.instance();
    private final HexGridPanel hexGridPanel = new HexGridPanel(simulation.simulationConfig().rowCount(), simulation.simulationConfig().colCount(), simulation.simulationConfig().hexSize(), simulation.hexBoard());

    public Application() {
        setTitle("Hexagon Drawing Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        add(hexGridPanel);
    }

    public static void main(String[] args) {
        var application = new Application();
        application.start();
    }

    private void start() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            var simulationThread = new Thread(() -> {
                while (true) {
                    simulation.step();
                    hexGridPanel.hexBoard(simulation.hexBoard());
                    SwingUtilities.invokeLater(hexGridPanel::repaint);
                    try {
                        Thread.sleep(simulationConfig.hexDelay());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            simulationThread.start();
        });
    }
}
