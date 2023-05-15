package graphics;

import hex.Hex;
import hex.HexBoard;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

public class DrawingPanel extends JPanel {
    private final HexBoard<Hex> hexBoard = new HexBoard<>(2, 2, Hex.EMPTY);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int radius = 12;

        int rowCounter = 0;
        for (List<Hex> row : hexBoard.getBoard()) {
            int colCounter = 0;
            for (Hex hex : row) {
                var colOffset = rowCounter % 2 == 1 ? radius *2 : radius;
                var rowOffset = radius;
                HexShape.paint(g, colOffset + colCounter * radius * 2, rowOffset + rowCounter * radius * 2 , radius);
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