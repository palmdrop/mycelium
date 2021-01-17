package flow.gravity;

import util.vector.ReadVector;
import util.vector.Vector;

public class GravityDirection implements Gravity {
    private final Vector gravity;
    private final double massPower;

    public GravityDirection(Vector direction, double constant) {
        this(direction, constant, 0);
    }

    public GravityDirection(Vector direction, double constant, double massPower) {
        this.gravity = new Vector(direction).normalize().mult(constant);
        this.massPower = massPower;
    }

    @Override
    public Vector calculate(ReadVector position, double mass) {
        return Vector.mult(gravity, Math.pow(mass, massPower));
    }
}
