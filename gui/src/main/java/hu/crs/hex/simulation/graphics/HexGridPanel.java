package hu.crs.hex.simulation.graphics;

import hu.crs.hex.ComplexHexBoard;
import hu.crs.hex.Coordinate;
import hu.crs.hex.HexBoard;
import hu.crs.hex.HexEntity;
import hu.crs.hex.HexField;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;

import static hu.crs.hex.simulation.simulation.SimulationConfig.COL_COUNT;
import static hu.crs.hex.simulation.simulation.SimulationConfig.HEX_SIZE;
import static hu.crs.hex.simulation.simulation.SimulationConfig.HEX_WIDTH;
import static hu.crs.hex.simulation.simulation.SimulationConfig.HORIZONTAL_SPACING;
import static hu.crs.hex.simulation.simulation.SimulationConfig.ROW_COUNT;
import static hu.crs.hex.simulation.simulation.SimulationConfig.VERTICAL_SPACING;

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