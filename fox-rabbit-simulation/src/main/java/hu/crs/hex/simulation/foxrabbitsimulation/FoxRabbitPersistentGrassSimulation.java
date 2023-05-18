package hu.crs.hex.simulation.foxrabbitsimulation;

import hu.crs.hex.HexField;

import java.util.Optional;

public class FoxRabbitPersistentGrassSimulation extends FoxRabbitSimulation {
    public FoxRabbitPersistentGrassSimulation(int rowCount, int colCount, FoxRabbitHexEntity defaultHexEntity) {
        super(rowCount, colCount, defaultHexEntity);
    }

    @Override
    protected Optional<HexField<FoxRabbitHexEntity>> handleRabbit(HexField<FoxRabbitHexEntity> hexField) {
        var maybeWasGrass = super.handleRabbit(hexField);

        if (maybeWasGrass.isEmpty()) {
            hexBoard().set(hexField.id(), FoxRabbitHexEntity.GRASS);
        }

        return maybeWasGrass;
    }

    @Override
    protected Optional<HexField<FoxRabbitHexEntity>> handleFox(HexField<FoxRabbitHexEntity> hexField) {
        var maybeWasRabbit = super.handleFox(hexField);
        if (maybeWasRabbit.isEmpty()) {
            hexBoard().set(hexField.id(), FoxRabbitHexEntity.GRASS);
        }
        return maybeWasRabbit;
    }
}
