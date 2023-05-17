package graphics;

import hex.Coordinate;
import hex.HexBoard;
import hex.HexEntity;
import hex.HexField;
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
    private final FoxRabbitSimulation foxRabbitSimulation = new FoxRabbitSimulation(ROW_COUNT, COL_COUNT, HexEntity.EMPTY);


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        foxRabbitSimulation.randomInitialize(List.of(new HexProbability(HexEntity.GRASS, 0.5), new HexProbability(HexEntity.RABBIT, 0.2), new HexProbability(HexEntity.FOX, 0.1)));
        drawGrid(g, foxRabbitSimulation.hexBoard(), hexCenters);

//        int i = 0;
//        while (i < 5) {
//            var maybeModifiedHexField = foxRabbitSimulation.step();
//            maybeModifiedHexField.ifPresent(modifiedHexField -> drawHexField(g, modifiedHexField));
//            i++;
//            try {
//                Thread.sleep(5_000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

    private void drawHexField(Graphics g, HexField<HexEntity> hexField) {
        var hexCenter = hexCenters.getHex(hexField.id());
        HexDrawer.draw(g, hexCenter.row(), hexCenter.col(), HEX_SIZE, hexField.content());
    }

    private void drawGrid(Graphics g, HexBoard<HexField<HexEntity>> hexBoard, HexBoard<Coordinate> hexCenters) {
        hexBoard.forEach(hexField -> drawHexField(g, hexField));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
}