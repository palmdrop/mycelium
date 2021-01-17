package flow.gravity;

import util.vector.ReadVector;
import util.vector.Vector;

import java.util.List;

public class AggregateGravity implements Gravity {
    private final List<Gravity> gravities;

    public AggregateGravity(List<Gravity> gravities) {
        if(gravities == null || gravities.size() < 1) throw new IllegalArgumentException();
        this.gravities = gravities;
    }

    public AggregateGravity(Gravity... gravities) {
        this(List.of(gravities));
    }

    @Override
    public Vector calculate(ReadVector position, double mass) {
        Vector force = new Vector();
        for(Gravity g : gravities) {
            force.add(g.calculate(position, mass));
        }
        return force;
    }
}
