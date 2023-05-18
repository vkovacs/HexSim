package hu.crs.hex.simulation.foxrabbitsimulation;

import hu.crs.hex.ComplexHexBoard;
import hu.crs.hex.HexField;

import java.util.Optional;

public interface Simulation<T> {
    ComplexHexBoard<T> hexBoard();
    Optional<HexField<T>> step();
}
