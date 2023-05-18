package hu.crs.hex.simulation.foxrabbitsimulation;

import hu.crs.hex.ComplexHexBoard;
import hu.crs.hex.HexContentFactory;
import hu.crs.hex.HexField;
import hu.crs.hex.HexProbability;
import hu.crs.hex.simulation.Simulation;
import hu.crs.hex.simulation.SimulationConfig;

import java.util.List;
import java.util.Optional;

import static hu.crs.hex.simulation.foxrabbitsimulation.FoxRabbitSimulationConfig.COL_COUNT;
import static hu.crs.hex.simulation.foxrabbitsimulation.FoxRabbitSimulationConfig.ROW_COUNT;

public class FoxRabbitSimulation<FoxRabbitHexEntity> implements Simulation<FoxRabbitHexEntity> {
    private final ComplexHexBoard<FoxRabbitHexEntity> hexBoard;
    private final SimulationConfig simulationConfig = FoxRabbitSimulationConfig.instance();
    private final HexContentFactory<FoxRabbitHexEntity> hexContentFactory = new HexContentFactory<>();

    private FoxRabbitSimulation(int rowCount, int colCount, FoxRabbitHexEntity defaultHexEntity) {
        hexBoard = new ComplexHexBoard<>(rowCount, colCount, defaultHexEntity);
        randomInitialize(List.of(new HexProbability<>(FoxRabbitHexEntity.GRASS, 0.5), new HexProbability<>(FoxRabbitHexEntity.RABBIT, 0.2), new HexProbability<>(FoxRabbitHexEntity.FOX, 0.1)));
    }

    public static FoxRabbitSimulation instance() {
        return new FoxRabbitSimulation(ROW_COUNT, COL_COUNT, FoxRabbitHexEntity.EMPTY);
    }

    public void randomInitialize(List<HexProbability<FoxRabbitHexEntity>> hexProbabilities) {
        for (int i = 0; i < hexBoard.rowCount(); i++) {
            int maxJ = hexBoard.maxColInRow(i);
            for (int j = 0; j < maxJ; j++) {
                var randomHex = hexContentFactory.randomHexEntity(hexProbabilities);
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

    @Override
    public SimulationConfig simulationConfig() {
        return simulationConfig;
    }
}
