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
    private final HexBoard<Coordinate> hexCenters = new HexBoard<>(ROW_COUNT, COL_COUNT, new Coordinate(-1, -1));

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COL_COUNT; j++) {
                var colOffset = i % 2 == 1 ? HEX_WIDTH : HEX_WIDTH / 2;
                var rowOffset = VERTICAL_SPACING;

                var centerX = colOffset + j * HORIZONTAL_SPACING;
                var centerY = rowOffset + i * VERTICAL_SPACING;

                Draw.hex(g, centerX, centerY, HEX_SIZE);
                hexCenters.set(i, j, new Coordinate(centerX, centerY));
            }
        }
        System.out.println("a");
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
}