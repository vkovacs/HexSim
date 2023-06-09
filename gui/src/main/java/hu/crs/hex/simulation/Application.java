package hu.crs.hex.simulation;

import hu.crs.hex.simulation.foxrabbitsimulation.FoxRabbitHexEntity;
import hu.crs.hex.simulation.foxrabbitsimulation.FoxRabbitPersistentGrassSimulation;
import hu.crs.hex.simulation.foxrabbitsimulation.FoxRabbitSimulationConfig;
import hu.crs.hex.simulation.graphics.HexGridPanel;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Toolkit;

import static hu.crs.hex.simulation.foxrabbitsimulation.FoxRabbitSimulationConfig.COL_COUNT;
import static hu.crs.hex.simulation.foxrabbitsimulation.FoxRabbitSimulationConfig.ROW_COUNT;

public class Application extends JFrame {
    private final Simulation<FoxRabbitHexEntity> simulation = new FoxRabbitPersistentGrassSimulation(ROW_COUNT, COL_COUNT, FoxRabbitHexEntity.EMPTY);
    private final SimulationConfig simulationConfig = new FoxRabbitSimulationConfig();
    private final HexGridPanel<FoxRabbitHexEntity> hexGridPanel = new HexGridPanel<>(simulationConfig.rowCount(), simulationConfig.colCount(), simulationConfig.hexSize(), simulation.hexBoard());

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
