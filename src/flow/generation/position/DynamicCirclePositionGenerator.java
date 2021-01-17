package flow.generation.position;

import sampling.heightMap.HeightMap;
import util.math.MathUtils;
import util.noise.generator.GNoise;
import util.noise.type.OpenSimplexNoise;
import util.vector.Vector;

public class DynamicCirclePositionGenerator implements PositionGenerator {
    private final Vector center;

    private final HeightMap radiusVariation;
    private final HeightMap deviationVariation;
    private final HeightMap swirlVariation;

    private final double minRadius, maxRadius;
    private final double minDeviation, maxDeviation;

    private final double swirlAmount;

    public DynamicCirclePositionGenerator(Vector center, double minRadius, double maxRadius, double minDeviation, double maxDeviation, double radiusFrequency, double deviationFrequency, double swirlFrequency, double swirlAmount){
        this.center = center;
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.minDeviation = minDeviation;
        this.maxDeviation = maxDeviation;

        radiusVariation =
                new GNoise(new OpenSimplexNoise(), radiusFrequency, 1.0);
        deviationVariation =
                new GNoise(new OpenSimplexNoise(), deviationFrequency, 1.0);

        if(swirlFrequency != 0) {
            swirlVariation = new GNoise(new OpenSimplexNoise(), swirlFrequency, 1.0);
        } else {
            swirlVariation = (x,y) -> 1.0;
        }

        this.swirlAmount = swirlAmount;
    }


    @Override
    public Vector generate() {
        double angle = Math.random() * Math.PI * 2;
        Vector unit = Vector.fromAngle(angle);


        double r = MathUtils.map(radiusVariation.get(unit), 0, 1, minRadius, maxRadius);
        double d = MathUtils.map(deviationVariation.get(unit), 0, 1, minDeviation, maxDeviation);

        double randomVariation = 1 + MathUtils.randomGaussian(d);
        r *= randomVariation;

        angle += MathUtils.map(swirlVariation.get(unit), 0, 1, -randomVariation * swirlAmount, randomVariation * swirlAmount);

        return Vector.fromAngle(angle).mult(r).add(center);
    }
}
