package flow.gravity;

import util.vector.ReadVector;
import util.vector.Vector;

public class GravityRotation implements Gravity {
    private final double constant;
    private final Vector center;
    private final double distPow;
    private final double amount;

    public GravityRotation(double constant, Vector center, double distPow, double amount) {
        this.constant = constant;
        this.center = center;
        this.distPow = distPow;
        this.amount = amount;
    }

    @Override
    public Vector calculate(ReadVector position, double mass) {
        double dist = Vector.dist(position, center);
        double d = Math.pow(dist, distPow);
        double angle = amount * d;
        return Vector.fromAngle(angle).mult(constant);
    }
}
