package hu.crs.hex.simulation.foxrabbitsimulation;

import hu.crs.hex.ComplexHexBoard;
import hu.crs.hex.RandomHexContentFactory;
import hu.crs.hex.HexField;
import hu.crs.hex.HexProbability;
import hu.crs.hex.simulation.Simulation;

import java.util.List;
import java.util.Optional;

public class FoxRabbitSimulation implements Simulation<FoxRabbitHexEntity> {
    private final ComplexHexBoard<FoxRabbitHexEntity> hexBoard;
    private final RandomHexContentFactory<FoxRabbitHexEntity> randomHexContentFactory = new RandomHexContentFactory<>();

    public FoxRabbitSimulation(int rowCount, int colCount, FoxRabbitHexEntity defaultHexEntity) {
        hexBoard = new ComplexHexBoard<>(rowCount, colCount, defaultHexEntity);
        randomInitialize(List.of(new HexProbability<>(FoxRabbitHexEntity.GRASS, 0.6), new HexProbability<>(FoxRabbitHexEntity.RABBIT, 0.2), new HexProbability<>(FoxRabbitHexEntity.FOX, 0.1), new HexProbability<>(FoxRabbitHexEntity.EMPTY, 0.1)));
    }

    public void randomInitialize(List<HexProbability<FoxRabbitHexEntity>> hexProbabilities) {
        for (int i = 0; i < hexBoard.rowCount(); i++) {
            int maxJ = hexBoard.maxColInRow(i);
            for (int j = 0; j < maxJ; j++) {
                var randomHex = randomHexContentFactory.randomHexEntity(hexProbabilities);
                hexBoard.set(i, j, randomHex);
            }
        }
    }

    @Override
    public Optional<HexField<FoxRabbitHexEntity>> step() {
        var hexField = hexBoard.randomHexField();
        return switch (hexField.content()) {
            case EMPTY -> Optional.empty();
            case FOX -> handleFox(hexField);
            case RABBIT -> handleRabbit(hexField);
            case GRASS -> handleGrass(hexField);
            default -> throw new IllegalArgumentException("No such HexEntity Type handler!");
        };
    }

    private Optional<HexField<FoxRabbitHexEntity>> handleGrass(HexField<FoxRabbitHexEntity> hexField) {
        if (FoxRabbitHexEntity.GRASS != hexField.content()) {
            throw new IllegalArgumentException("Not grass!");
        }

        var maybeEmpty = hexBoard.neighbourHexFields(hexField.id()).stream()
                .filter(neighbourHexField -> FoxRabbitHexEntity.EMPTY == neighbourHexField.content())
                .findAny();

        maybeEmpty.ifPresent(emptyHexField -> hexBoard.set(emptyHexField.id(), FoxRabbitHexEntity.GRASS));

        return maybeEmpty;
    }

    private Optional<HexField<FoxRabbitHexEntity>> handleRabbit(HexField<FoxRabbitHexEntity> hexField) {
        if (FoxRabbitHexEntity.RABBIT != hexField.content()) {
            throw new IllegalArgumentException("Not a rabbit!");
        }

        var maybeGrass = hexBoard.neighbourHexFields(hexField.id()).stream()
                .filter(neighbourHexField -> FoxRabbitHexEntity.GRASS == neighbourHexField.content())
                .findAny();

        if (maybeGrass.isPresent()) {
            var grassHexField = maybeGrass.get();
            hexBoard.set(grassHexField.id(), FoxRabbitHexEntity.RABBIT);
        } else {
            hexBoard.set(hexField.id(), FoxRabbitHexEntity.EMPTY);
        }

        return maybeGrass;
    }

    private Optional<HexField<FoxRabbitHexEntity>> handleFox(HexField<FoxRabbitHexEntity> hexField) {
        if (FoxRabbitHexEntity.FOX != hexField.content()) {
            throw new IllegalArgumentException("Not a fox!");
        }

        var maybeRabbit = hexBoard.neighbourHexFields(hexField.id()).stream()
                .filter(neighbourHexField -> FoxRabbitHexEntity.RABBIT == neighbourHexField.content())
                .findAny();

        if (maybeRabbit.isPresent()) {
            var rabbitHexField = maybeRabbit.get();
            hexBoard.set(rabbitHexField.id(), FoxRabbitHexEntity.FOX);
        } else {
            hexBoard.set(hexField.id(), FoxRabbitHexEntity.EMPTY);
        }

        return maybeRabbit;
    }

    @Override
    public ComplexHexBoard<FoxRabbitHexEntity> hexBoard() {
        return hexBoard;
    }
}
