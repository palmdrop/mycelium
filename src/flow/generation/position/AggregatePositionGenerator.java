package flow.generation.position;

import util.math.MathUtils;
import util.vector.Vector;

import java.util.List;

public class AggregatePositionGenerator implements PositionGenerator {
    private final List<PositionGenerator> generators;

    public AggregatePositionGenerator(PositionGenerator... generators) {
        this(List.of(generators));
    }

    public AggregatePositionGenerator(List<PositionGenerator> generators) {
        if(generators == null || generators.size() < 1) throw new IllegalArgumentException();
        this.generators = generators;
    }


    @Override
    public Vector generate() {
        int index = (int) MathUtils.random(generators.size());
        return generators.get(index).generate();
    }
}
