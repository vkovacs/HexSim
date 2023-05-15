package graphics;

import java.awt.*;

public class HexShape {
    private static final int HEXAGON_SIDES = 6;

    public static void paint(Graphics g, int centerX, int centerY, int radius) {
        // Calculate the vertices of the hexagon
        int[] xPoints = new int[HEXAGON_SIDES];
        int[] yPoints = new int[HEXAGON_SIDES];

        for (int i = 0; i < HEXAGON_SIDES; i++) {
            double angle = 2 * Math.PI / HEXAGON_SIDES * i + Math.PI / 2;
            xPoints[i] = (int) (centerX + radius * Math.cos(angle));
            yPoints[i] = (int) (centerY + radius * Math.sin(angle));
        }

        // Draw the hexagon
        g.setColor(Color.RED);
        g.drawPolygon(xPoints, yPoints, HEXAGON_SIDES);
    }
}
