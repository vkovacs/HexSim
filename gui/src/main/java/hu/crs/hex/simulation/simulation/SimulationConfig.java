package hu.crs.hex.simulation.simulation;

public class SimulationConfig {
    public static final int ROW_COUNT = 60;
    public static final int COL_COUNT = 100;
    public static final int HEX_SIZE = 12;
    public static final long DELAY_MILIS = 0;
    public static final int HEX_WIDTH = (int) (Math.sqrt(3) * HEX_SIZE);
    public static final int HORIZONTAL_SPACING = HEX_WIDTH;
    public static final int VERTICAL_SPACING = (int) Math.round(HEX_SIZE * (3d / 2d));
}
