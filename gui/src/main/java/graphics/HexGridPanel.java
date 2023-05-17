package graphics;

import hex.Coordinate;
import hex.Hex;
import hex.HexBoard;
import hex.HexProperties;
import simulation.FoxRabbitSimulation;
import simulation.HexProbability;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import static simulation.SimulationConfig.COL_COUNT;
import static simulation.SimulationConfig.HEX_SIZE;
import static simulation.SimulationConfig.HEX_WIDTH;
import static simulation.SimulationConfig.HORIZONTAL_SPACING;
import static simulation.SimulationConfig.ROW_COUNT;
import static simulation.SimulationConfig.VERTICAL_SPACING;

public class HexGridPanel extends JPanel {
    private final HexBoard<Coordinate> hexCenters = HexDrawer.hexCenters(ROW_COUNT, COL_COUNT, HEX_WIDTH, HORIZONTAL_SPACING, VERTICAL_SPACING);
    private final FoxRabbitSimulation foxRabbitSimulation = new FoxRabbitSimulation(ROW_COUNT, COL_COUNT, Hex.EMPTY);


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        foxRabbitSimulation.randomInitialize(List.of(new HexProbability(Hex.GRASS, 0.5), new HexProbability(Hex.RABBIT, 0.2), new HexProbability(Hex.FOX, 0.1)));
        drawGrid(g, foxRabbitSimulation.hexBoard(), hexCenters);
    }

    private void drawGrid(Graphics g, HexBoard<HexProperties<Hex>> hexBoard, HexBoard<Coordinate> hexCenters) {
        hexBoard.forEach(hexProperties -> {
                    var hexCenter = hexCenters.getHex(hexProperties.id().row(), hexProperties.id().col());
                    HexDrawer.draw(g, hexCenter.row(), hexCenter.col(), HEX_SIZE, hexProperties.content());
                }
        );
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
}