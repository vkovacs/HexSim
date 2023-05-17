package simulation;

import hex.ComplexHexBoard;
import hex.Coordinate;
import hex.Hex;
import hex.HexProperties;

import java.util.Map;

import static simulation.SimulationConfig.COL_COUNT;
import static simulation.SimulationConfig.ROW_COUNT;

public class FoxRabbitSimulation {
    private final ComplexHexBoard<Hex> hexBoard = new ComplexHexBoard<>(ROW_COUNT, COL_COUNT, Hex.EMPTY);

    public void randomInitialize(int rowCount, int colCount, Map<Hex, Double> hexProbabilities) {


        for (int i = 0; i < rowCount; i++) {
            int maxJ = hexBoard.maxColInRow(i);
            for (int j = 0; j < maxJ; j++) {
                var r = Math.random();
                if (r < 0.3) {
                    var actual = hexBoard.getHex(i, j);
                    hexBoard.set(i, j, Hex.GRASS);
                }
            }
        }
    }

    public ComplexHexBoard<Hex> hexBoard() {
        return hexBoard;
    }
}
