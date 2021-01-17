package flow.generation.position;

import processing.core.PApplet;
import util.math.MathUtils;
import util.vector.Vector;

public class CirclePositionGenerator implements PositionGenerator {
    private final Vector center;
    private final double radius;
    private final double variation;
    private final boolean gaussian;

    public CirclePositionGenerator(Vector center, double radius, double variation, boolean gaussian) {
        this.center = center;
        this.radius = radius;
        this.variation = variation;
        this.gaussian = gaussian;
    }

    @Override
    public Vector generate() {
        double randomVariation;
        if(!gaussian) {
            randomVariation = MathUtils.random(1 - variation, 1 + variation);
        } else {
            randomVariation = 1 + MathUtils.randomGaussian(variation);
        }
        double r = radius * randomVariation;
        double angle = Math.random() * PApplet.TWO_PI;
        return Vector.fromAngle(angle).mult(r).add(center);
    }
}
