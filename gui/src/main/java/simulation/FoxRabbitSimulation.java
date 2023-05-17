package simulation;

import hex.ComplexHexBoard;
import hex.HexEntity;
import hex.HexField;

import java.util.List;
import java.util.Optional;

public class FoxRabbitSimulation {
    private final ComplexHexBoard<HexEntity> hexBoard;

    public FoxRabbitSimulation(int rowCount, int colCount, HexEntity defaultHexEntity) {
        hexBoard = new ComplexHexBoard<>(rowCount, colCount, defaultHexEntity);
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

    public Optional<HexField<HexEntity>> step() {
        var hexField = hexBoard.randomHexField();
        return switch (hexField.content()) {
            case EMPTY -> Optional.empty();
            case FOX -> handleFox(hexField);
            case RABBIT -> handleRabbit(hexField);
            case GRASS -> handleGrass(hexField);
            default -> throw new IllegalArgumentException("No such HexEntity Type handler!");
        };
    }

    private Optional<HexField<HexEntity>> handleGrass(HexField<HexEntity> hexField) {
        if (HexEntity.GRASS != hexField.content()) {
            throw new IllegalArgumentException("Not grass!");
        }

        var maybeEmpty = hexBoard.neighbourHexFields(hexField.id()).stream()
                .filter(neighbourHexField -> HexEntity.EMPTY == neighbourHexField.content())
                .findAny();

        maybeEmpty.ifPresent(emptyHexField -> hexBoard.set(emptyHexField.id(), HexEntity.GRASS));

        return maybeEmpty;
    }

    private Optional<HexField<HexEntity>> handleRabbit(HexField<HexEntity> hexField) {
        if (HexEntity.RABBIT != hexField.content()) {
            throw new IllegalArgumentException("Not a rabbit!");
        }

        var maybeGrass = hexBoard.neighbourHexFields(hexField.id()).stream()
                .filter(neighbourHexField -> HexEntity.GRASS == neighbourHexField.content())
                .findAny();

        if (maybeGrass.isPresent()) {
            var grassHexField = maybeGrass.get();
            hexBoard.set(grassHexField.id(), HexEntity.RABBIT);
        } else {
            hexBoard.set(hexField.id(), HexEntity.EMPTY);
        }

        return maybeGrass;
    }

    private Optional<HexField<HexEntity>> handleFox(HexField<HexEntity> hexField) {
        if (HexEntity.FOX != hexField.content()) {
            throw new IllegalArgumentException("Not a fox!");
        }

        var maybeRabbit = hexBoard.neighbourHexFields(hexField.id()).stream()
                .filter(neighbourHexField -> HexEntity.RABBIT == neighbourHexField.content())
                .findAny();

        if (maybeRabbit.isPresent()) {
            var rabbitHexField = maybeRabbit.get();
            hexBoard.set(rabbitHexField.id(), HexEntity.FOX);
        } else {
            hexBoard.set(hexField.id(), HexEntity.EMPTY);
        }

        return maybeRabbit;
    }

    public ComplexHexBoard<HexEntity> hexBoard() {
        return hexBoard;
    }

    HexEntity randomHexContent(List<HexProbability> hexProbabilities) {
        var probabilitySum = hexProbabilities.stream()
                .map(HexProbability::probability)
                .mapToDouble(Double::doubleValue)
                .sum();

        if (probabilitySum > 1) {
            throw new IllegalArgumentException("Probability sum must not be greater than 1");
        }
        return cdfSelector(hexProbabilities, Math.random());
    }

    HexEntity cdfSelector(List<HexProbability> hexProbabilities, double randomNumber) {
        var cumulativeProbability = 0d;

        for (HexProbability hexProbability : hexProbabilities) {
            cumulativeProbability += hexProbability.probability();

            if (cumulativeProbability > randomNumber) {
                return hexProbability.hexEntity();
            }
        }

        return HexEntity.EMPTY;
    }
}
