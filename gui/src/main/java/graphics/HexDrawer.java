package graphics;

import hex.Coordinate;
import hex.Hex;
import hex.HexBoard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class HexDrawer {
    private static final int HEXAGON_SIDES = 6;

    public static void draw(Graphics g, int centerX, int centerY, int size, Hex content) {
        // Calculate the vertices of the hexagon
        int[] xPoints = new int[HEXAGON_SIDES];
        int[] yPoints = new int[HEXAGON_SIDES];

        for (int i = 0; i < HEXAGON_SIDES; i++) {
            double angle = 2 * Math.PI / HEXAGON_SIDES * i + Math.PI / 2;
            xPoints[i] = (int) (centerX + size * Math.cos(angle));
            yPoints[i] = (int) (centerY + size * Math.sin(angle));
        }

        // Draw the hexagon
        var g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1));

        if (content == Hex.EMPTY) {
            g2d.setColor(Color.BLACK);
            g2d.drawPolygon(xPoints, yPoints, HEXAGON_SIDES);
        } else if (content == Hex.GRASS) {
            g2d.setColor(Color.GREEN);
            g2d.fillPolygon(xPoints, yPoints, HEXAGON_SIDES);
        }
    }

    public static HexBoard<Coordinate> hexCenters(int rowCount, int colCount, int width, int horizontalSpacing, int verticalSpacing) {
        var hexCenters = new HexBoard<>(rowCount, colCount, new Coordinate(-1, -1));
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (hexCenters.maxColIndexInRow(i) < j) continue;

                var colOffset = i % 2 == 1 ? width : width / 2;
                var rowOffset = verticalSpacing;

                var centerX = colOffset + j * horizontalSpacing;
                var centerY = rowOffset + i * verticalSpacing;

                hexCenters.setHex(i, j, new Coordinate(centerX, centerY));
            }
        }

        return hexCenters;
    }
}
