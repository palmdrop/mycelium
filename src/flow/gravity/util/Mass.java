package flow.gravity.util;

import util.vector.Vector;

public class Mass {
    private final Vector position;
    private final double mass;

    public Mass(Vector position, double mass) {
        this.position = position;
        this.mass = mass;
    }

    public double getMass() {
        return mass;
    }

    public Vector getPosition() {
        return position;
    }
}
