package flow.generation.position;

import util.vector.Vector;

public class AreaPositionGenerator implements PositionGenerator {
    private final double width, height;

    public AreaPositionGenerator(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Vector generate() {
        return Vector.random(width, height);
    }
}
