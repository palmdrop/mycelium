package flow.generation.position;

import util.math.MathUtils;
import util.vector.Vector;

public class LinePositionGenerator implements PositionGenerator {
    private final Vector a, b;
    private final double deviation;
    private final boolean guassian;

    public LinePositionGenerator(Vector a, Vector b, double deviation, boolean guassian) {
        this.a = a;
        this.b = b;
        this.deviation = deviation;
        this.guassian = guassian;
    }

    @Override
    public Vector generate() {
        double n = Math.random();
        Vector p = Vector.interpolate(a, b, n);
        double d;
        if(guassian) {
            d = MathUtils.randomGaussian(deviation);
        } else {
            d = Math.random() * deviation;
        }
        Vector dv = Vector.randomWithLength(d);
        return p.add(dv);
    }
}
