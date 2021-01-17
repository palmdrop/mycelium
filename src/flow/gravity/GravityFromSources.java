package flow.gravity;

import flow.gravity.util.Mass;
import util.vector.ReadVector;
import util.vector.Vector;

import java.util.List;

public class GravityFromSources implements Gravity {
    private final List<Mass> sources;
    private final double constant;
    private final double distPow;

    public GravityFromSources(List<Mass> sources, double constant, double distPow) {
        this.sources = sources;
        this.constant = constant;
        this.distPow = distPow;
    }

    @Override
    public Vector calculate(ReadVector position, double mass) {
        Vector gravity = new Vector();
        for(Mass m : sources) {
            double dist = Vector.dist(position, m.getPosition());
            double weight = constant * m.getMass() * mass / dist;
            Vector gv = Vector.sub(m.getPosition(), position).mult(weight / Math.pow(dist, distPow));
            gravity.add(gv);
        }
        return gravity;
    }

    public static GravityFromSources fromPoint(Vector position, double mass, double constant, double distPow) {
        return new GravityFromSources(List.of(new Mass(position, mass)), constant, distPow);
    }
}
