package hu.crs.hex.simulation.foxrabbitsimulation;

import hu.crs.hex.simulation.SimulationConfig;

public class FoxRabbitSimulationConfig implements SimulationConfig {
    public static final int ROW_COUNT = 60;
    public static final int COL_COUNT = 100;
    public static final int HEX_SIZE = 12;
    public static final long STEP_DELAY = 0;

    @Override
    public int rowCount() {
        return ROW_COUNT;
    }

    @Override
    public int colCount() {
        return COL_COUNT;
    }

    @Override
    public int hexSize() {
        return HEX_SIZE;
    }

    @Override
    public long hexDelay() {
        return STEP_DELAY;
    }
}
