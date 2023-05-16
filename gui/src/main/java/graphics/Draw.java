package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Draw {
    private static final int HEXAGON_SIDES = 6;

    public static void hex(Graphics g, int centerX, int centerY, int size) {
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
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawPolygon(xPoints, yPoints, HEXAGON_SIDES);
    }
}
