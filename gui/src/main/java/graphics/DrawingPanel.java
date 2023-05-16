package graphics;

import hex.Coordinate;
import hex.Hex;
import hex.HexBoard;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;

public class DrawingPanel extends JPanel {
    private final int ROW_COUNT = 10;
    private final int COL_COUNT = 20;
    private final int HEX_SIZE = 48;
    private final int HEX_WIDTH = (int) (Math.sqrt(3) * HEX_SIZE);
    private final int HORIZONTAL_SPACING = HEX_WIDTH;
    private final int VERTICAL_SPACING = (int) Math.round(HEX_SIZE * (3d / 2d));
    private final HexBoard<Hex> hexBoard = new HexBoard<>(ROW_COUNT, COL_COUNT, Hex.EMPTY);
    private final HexBoard<Coordinate> hexCenters;

    {
        hexCenters = hexCenters(ROW_COUNT, COL_COUNT, HEX_SIZE, HORIZONTAL_SPACING, VERTICAL_SPACING);
    }

    private HexBoard<Coordinate> hexCenters(int rowCount, int colCount, int size, int horizontalSpacing, int verticalSpacing) {
        var hexCenters = new HexBoard<>(rowCount, colCount, new Coordinate(-1, -1));
        final int width = size * 2;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (hexCenters.isLastColInRow(i, j)) continue;

                var colOffset = i % 2 == 1 ? width : size;
                var rowOffset = verticalSpacing;

                var centerX = colOffset + j * horizontalSpacing;
                var centerY = rowOffset + i * verticalSpacing;

                hexCenters.set(i, j, new Coordinate(centerX, centerY));
            }
        }

        return hexCenters;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g, hexCenters);
    }

    private void drawGrid(Graphics g, HexBoard<Coordinate> hexCenters) {
        hexCenters.forEach(hexCenter -> Draw.hex(g, hexCenter.row(), hexCenter.col(), HEX_SIZE));
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
}