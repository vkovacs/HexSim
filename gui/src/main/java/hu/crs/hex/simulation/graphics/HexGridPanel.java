package hu.crs.hex.simulation.graphics;

import hu.crs.hex.ComplexHexBoard;
import hu.crs.hex.Coordinate;
import hu.crs.hex.HexBoard;
import hu.crs.hex.HexField;
import hu.crs.hex.simulation.HasColor;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;

public class HexGridPanel<T extends HasColor> extends JPanel {
    private final int hexSize;
    private final HexBoard<Coordinate> hexCenters;
    private ComplexHexBoard<T> hexBoard;

    public HexGridPanel(int rowCount, int colCount, int hexSize, ComplexHexBoard<T> hexBoard) {
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

    public void drawHexField(Graphics g, HexField<T> hexField) {
        var hexCenter = hexCenters.getHex(hexField.id());
        HexDrawer.draw(g, hexCenter.row(), hexCenter.col(), hexSize, hexField.content());
    }

    private void drawGrid(Graphics g, HexBoard<HexField<T>> hexBoard) {
        hexBoard.forEach(hexField -> drawHexField(g, hexField));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    public void hexBoard(ComplexHexBoard<T> hexBoard) {
        this.hexBoard = hexBoard;
    }
}