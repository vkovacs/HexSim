package simulation;

import hex.ComplexHexBoard;
import hex.Hex;

import java.util.List;

public class FoxRabbitSimulation {
    private final ComplexHexBoard<Hex> hexBoard;

    public FoxRabbitSimulation(int rowCount, int colCount, Hex defaultHex) {
        hexBoard = new ComplexHexBoard<>(rowCount, colCount, defaultHex);
    }

    public void randomInitialize(List<HexProbability> hexProbabilities) {
        for (int i = 0; i < hexBoard.rowCount(); i++) {
            int maxJ = hexBoard.maxColInRow(i);
            for (int j = 0; j < maxJ; j++) {
                var randomHex = randomHexContent(hexProbabilities);
                hexBoard.set(i, j, randomHex);
            }
        }
    }

    Hex randomHexContent(List<HexProbability> hexProbabilities) {
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

    public ComplexHexBoard<Hex> hexBoard() {
        return hexBoard;
    }
}