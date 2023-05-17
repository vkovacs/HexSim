package graphics;

import hex.ComplexHexBoard;
import hex.Coordinate;
import hex.HexBoard;
import hex.HexEntity;
import hex.HexField;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;

import static simulation.SimulationConfig.COL_COUNT;
import static simulation.SimulationConfig.HEX_SIZE;
import static simulation.SimulationConfig.HEX_WIDTH;
import static simulation.SimulationConfig.HORIZONTAL_SPACING;
import static simulation.SimulationConfig.ROW_COUNT;
import static simulation.SimulationConfig.VERTICAL_SPACING;

public class HexGridPanel extends JPanel {
    private final HexBoard<Coordinate> hexCenters = HexDrawer.hexCenters(ROW_COUNT, COL_COUNT, HEX_WIDTH, HORIZONTAL_SPACING, VERTICAL_SPACING);
    private ComplexHexBoard<HexEntity> hexBoard;

    public HexGridPanel(ComplexHexBoard<HexEntity> hexBoard) {
        this.hexBoard = hexBoard;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g, hexBoard);
    }

    public void drawHexField(Graphics g, HexField<HexEntity> hexField) {
        var hexCenter = hexCenters.getHex(hexField.id());
        HexDrawer.draw(g, hexCenter.row(), hexCenter.col(), HEX_SIZE, hexField.content());
    }

    private void drawGrid(Graphics g, HexBoard<HexField<HexEntity>> hexBoard) {
        hexBoard.forEach(hexField -> drawHexField(g, hexField));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    public void hexBoard(ComplexHexBoard<HexEntity> hexBoard) {
        this.hexBoard = hexBoard;
    }
}