package graphics;

import hex.Hex;
import hex.HexBoard;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

public class DrawingPanel extends JPanel {
    private final HexBoard<Hex> hexBoard = new HexBoard<>(10, 10, Hex.EMPTY);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final int size = 24;
        final int width = (int) (Math.sqrt(3) * size);
        final int horizontalSpacing = width;
        final int verticalSpacing = (int) Math.round(size * (3d / 2d));

        int rowCounter = 0;
        for (List<Hex> row : hexBoard.getBoard()) {
            int colCounter = 0;
            for (Hex hex : row) {
                var colOffset = rowCounter % 2 == 1 ? width : width / 2;
                var rowOffset = verticalSpacing;
                HexShape.paint(g, colOffset + colCounter * horizontalSpacing, rowOffset + rowCounter * verticalSpacing, size);
                colCounter++;
            }
            rowCounter++;
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
}