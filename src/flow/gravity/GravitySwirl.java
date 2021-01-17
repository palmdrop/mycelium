package flow.gravity;

import processing.core.PApplet;
import util.vector.ReadVector;
import util.vector.Vector;

public class GravitySwirl implements Gravity {
    private final double constant;
    private final Vector center;
    private final double distPow;

    public GravitySwirl(double constant, Vector center, double distPow) {
        this.constant = constant;
        this.center = center;
        this.distPow = distPow;
    }

    @Override
    public Vector calculate(ReadVector position, double mass) {

        Vector dir = Vector.sub(position, center);
        double angle = -angle(dir);

        double div = 1.0;
        if(distPow != 0.0) {
            div = Math.pow(dir.length(), distPow);
        }

        return Vector.fromAngle(angle).mult(constant / div);
    }

    private float angle(Vector p) {
        float a = (float) Math.atan2(p.getX(), p.getY());
        return (PApplet.TWO_PI + a) % PApplet.TWO_PI;
    }
}
