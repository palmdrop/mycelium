package flow.gravity;

import flow.generation.WeightMap;
import util.vector.ReadVector;
import util.vector.Vector;

public class GravityMap implements Gravity {
    private final WeightMap wm;
    private final double constant;

    public GravityMap(WeightMap wm, double constant) {
        this.wm = wm;
        this.constant = constant;
    }

    @Override
    public Vector calculate(ReadVector position, double mass) {
        double angle = 2 * Math.PI * wm.get(position);
        return Vector.fromAngle(angle).mult(constant);
    }
}
