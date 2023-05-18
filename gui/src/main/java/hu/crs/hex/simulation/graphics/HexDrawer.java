package hu.crs.hex.simulation.graphics;

import hu.crs.hex.Coordinate;
import hu.crs.hex.HexBoard;
import hu.crs.hex.simulation.HasColor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class HexDrawer {
    private static final int HEXAGON_SIDES = 6;

    public static <T extends HasColor> void draw(Graphics g, int centerX, int centerY, int size, T content) {

        double halfPixel = 0.5; //remove thin white line between hexes
        double shiftedCenterX = centerX + halfPixel;
        double shiftedCenterY = centerY + halfPixel;

        // Calculate the vertices of the hexagon
        int[] xPoints = new int[HEXAGON_SIDES];
        int[] yPoints = new int[HEXAGON_SIDES];

        for (int i = 0; i < HEXAGON_SIDES; i++) {
            double angle = 2 * Math.PI / HEXAGON_SIDES * i + Math.PI / 2;
            xPoints[i] = (int) (shiftedCenterX + size * Math.cos(angle));
            yPoints[i] = (int) (shiftedCenterY + size * Math.sin(angle));
        }

        // Draw the hexagon
        var g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1));

        g2d.setColor(new Color(content.r(), content.g(), content.b()));
        g2d.fillPolygon(xPoints, yPoints, HEXAGON_SIDES);
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
