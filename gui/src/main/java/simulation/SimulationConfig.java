package simulation;

public class SimulationConfig {
    public static final int ROW_COUNT = 10;
    public static final int COL_COUNT = 20;
    public static final int HEX_SIZE = 48;
    public static final int HEX_WIDTH = (int) (Math.sqrt(3) * HEX_SIZE);
    public static final int HORIZONTAL_SPACING = HEX_WIDTH;
    public static final int VERTICAL_SPACING = (int) Math.round(HEX_SIZE * (3d / 2d));
}
