import graphics.HexGridPanel;
import hex.HexEntity;
import simulation.FoxRabbitSimulation;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Toolkit;

import static simulation.SimulationConfig.COL_COUNT;
import static simulation.SimulationConfig.ROW_COUNT;

public class Application extends JFrame {
    private final FoxRabbitSimulation foxRabbitSimulation = new FoxRabbitSimulation(ROW_COUNT, COL_COUNT, HexEntity.EMPTY);
    private final HexGridPanel hexGridPanel = new HexGridPanel(foxRabbitSimulation.hexBoard());

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
                int i = 0;
                while (i < 50) {
                    foxRabbitSimulation.step();
                    hexGridPanel.hexBoard(foxRabbitSimulation.hexBoard());
                    SwingUtilities.invokeLater(hexGridPanel::repaint);
                    i++;
                    try {
                        Thread.sleep(100);
                        System.out.println("wait..");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            );

        simulationThread.start();
});
        }
        }
