package flow.gravity;

import processing.core.PApplet;
import util.math.MathUtils;
import util.vector.ReadVector;
import util.vector.Vector;

public class GravityNoise implements Gravity {
    private final PApplet p;
    private final double constant;
    private final double frequency;

    private final double offset;

    public GravityNoise(PApplet p, double constant, double frequency) {
        this.p = p;
        this.constant = constant;
        this.frequency = frequency;

        offset = MathUtils.random(1000, 50000);
    }

    @Override
    public Vector calculate(ReadVector position, double mass) {
        double angle = 2 * PApplet.TWO_PI * p.noise((float)(position.getX() * frequency + offset), (float)(position.getY() * frequency));
        return Vector.fromAngle(angle).mult(constant);
    }

    private float angle(Vector p) {
        float a = (float) Math.atan2(p.getX(), p.getY());
        return (PApplet.TWO_PI + a) % PApplet.TWO_PI;
    }
}
