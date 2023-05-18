package hu.crs.hex.simulation.foxrabbitsimulation;

import hu.crs.hex.simulation.HasColor;

public enum FoxRabbitHexEntity implements HasColor {
    EMPTY(64, 64, 64),
    FOX(255,0,0),
    RABBIT(255, 255, 255),
    GRASS(0, 255, 0);

    private final int r;
    private final int g;
    private final int b;

    FoxRabbitHexEntity(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int r() {
        return r;
    }

    public int g() {
        return g;
    }

    public int b() {
        return b;
    }
}
