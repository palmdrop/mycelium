package flow.generation.bubbleField;

import util.geometry.Circle;
import util.vector.Vector;

public class Bubble extends Circle {
    private final double force;
    private final double distPow;

    public Bubble(Vector pos, double radius, double force, double distPow) {
        super(pos, radius);
        this.force = force;
        this.distPow = distPow;
    }

    public double getForce() {
        return force;
    }

    public double getDistPow() {
        return distPow;
    }
}
