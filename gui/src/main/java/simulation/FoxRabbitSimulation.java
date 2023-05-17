package simulation;

import hex.ComplexHexBoard;
import hex.Hex;

import java.util.List;

import static simulation.SimulationConfig.COL_COUNT;
import static simulation.SimulationConfig.ROW_COUNT;

public class FoxRabbitSimulation {
    private final ComplexHexBoard<Hex> hexBoard = new ComplexHexBoard<>(ROW_COUNT, COL_COUNT, Hex.EMPTY);

    public void randomInitialize(int rowCount, int colCount, List<HexProbability> hexProbabilities) {
        var probabilitySum = hexProbabilities.stream()
                .map(HexProbability::probability)
                .mapToDouble(Double::doubleValue)
                .sum();

        if (probabilitySum > 1) {
            throw new IllegalArgumentException("Probability sum must not be greater than 1");
        }

        for (int i = 0; i < rowCount; i++) {
            int maxJ = hexBoard.maxColInRow(i);
            for (int j = 0; j < maxJ; j++) {
                var randomHex = randomHexContent(hexProbabilities);
                hexBoard.set(i, j, randomHex);
            }
        }
    }

    public ComplexHexBoard<Hex> hexBoard() {
        return hexBoard;
    }

    public Hex randomHexContent(List<HexProbability> hexProbabilities) {
        var probabilitySum = hexProbabilities.stream()
                .map(HexProbability::probability)
                .mapToDouble(Double::doubleValue)
                .sum();

        if (probabilitySum > 1) {
            throw new IllegalArgumentException("Probability sum must not be greater than 1");
        }
        return cdfSelector(hexProbabilities, Math.random());
    }

    Hex cdfSelector(List<HexProbability> hexProbabilities, double randomNumber) {
        var cumulativeProbability = 0d;

        for (HexProbability hexProbability : hexProbabilities) {
            cumulativeProbability += hexProbability.probability();

            if (cumulativeProbability > randomNumber) {
                return hexProbability.hex();
            }
        }

        return Hex.EMPTY;
    }
}
