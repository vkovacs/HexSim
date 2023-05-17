package graphics;

import hex.ComplexHexBoard;
import hex.Coordinate;
import hex.Hex;
import hex.HexBoard;
import hex.HexProperties;

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
    private final ComplexHexBoard<Hex> hexBoard = new ComplexHexBoard<>(ROW_COUNT, COL_COUNT, Hex.EMPTY);
    private final HexBoard<Coordinate> hexCenters = HexDrawer.hexCenters(ROW_COUNT, COL_COUNT, HEX_WIDTH, HORIZONTAL_SPACING, VERTICAL_SPACING);

    {
        hexBoard.set(0,0, Hex.GRASS);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g, hexBoard, hexCenters);
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