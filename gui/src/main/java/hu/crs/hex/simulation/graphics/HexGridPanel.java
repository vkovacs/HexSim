package hu.crs.hex.simulation.graphics;

import hu.crs.hex.ComplexHexBoard;
import hu.crs.hex.Coordinate;
import hu.crs.hex.HexBoard;
import hu.crs.hex.HexEntity;
import hu.crs.hex.HexField;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;

public class HexGridPanel extends JPanel {
    private final int hexSize;
    private final HexBoard<Coordinate> hexCenters;
    private ComplexHexBoard<HexEntity> hexBoard;

    public HexGridPanel(int rowCount, int colCount, int hexSize, ComplexHexBoard<HexEntity> hexBoard) {
        this.hexSize = hexSize;
        int hexWidth = (int) (Math.sqrt(3) * this.hexSize);
        int verticalSpacing = (int) Math.round(this.hexSize * (3d / 2d));
        this.hexBoard = hexBoard;

        hexCenters = HexDrawer.hexCenters(rowCount, colCount, hexWidth, hexWidth, verticalSpacing);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g, hexBoard);
    }

    public void drawHexField(Graphics g, HexField<HexEntity> hexField) {
        var hexCenter = hexCenters.getHex(hexField.id());
        HexDrawer.draw(g, hexCenter.row(), hexCenter.col(), hexSize, hexField.content());
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