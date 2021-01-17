package flow.generation.position;

import processing.core.PGraphics;
import util.geometry.Rectangle;
import util.space.WeightedMap;
import util.vector.Vector;

public class WeightedPositionGenerator implements PositionGenerator {
    private final WeightedMap wm;
    private final Vector offset;
    private final double random;

    public WeightedPositionGenerator(PGraphics g, Rectangle region, double random) {
        this(WeightedMap.mapFromGraphics(g, region), new Vector(region.x, region.y), random);
    }

    public WeightedPositionGenerator(WeightedMap wm, Vector offset, double random) {
        this.wm = wm;
        this.offset = offset;
        this.random = random;
    }

    @Override
    public Vector generate() {
        int[] p = wm.getRandom();
        Vector r = Vector.randomWithLength(1 + Math.random() * random);
        return new Vector(p[0] + offset.getX() + r.getX(), p[1] + offset.getY() + r.getY());
    }
}
