package flow.generation.position;

import util.math.MathUtils;
import util.vector.Vector;

import java.util.ArrayList;
import java.util.List;

public class PointsPositionGenerator implements PositionGenerator {
    public static class WeightedPoint {
        public final Vector point;
        public final double weight;

        public WeightedPoint(Vector point, double weight) {
            this.point = point;
            this.weight = weight;
        }
    }

    private final List<WeightedPoint> points;
    private final double deviation;

    public PointsPositionGenerator(List<WeightedPoint> points, double deviation) {
        if(!validate(points)) throw new IllegalArgumentException();
        this.points = points;
        this.deviation = deviation;
    }

    private boolean validate(List<WeightedPoint> points) {
        if(points == null || points.size() < 1) return false;
        double sum = 0.0;
        for(WeightedPoint wp : points) {
            sum += wp.weight;
            if(wp.weight < 0 || sum > 1.0) return false;
        }
        return sum == 1.0;
    }


    @Override
    public Vector generate() {
        double n = Math.random();
        double v = 0.0;
        Vector p = points.get(0).point;
        for(WeightedPoint wp : points) {
            v += wp.weight;
            if(v >= n) p = wp.point;
        }
        double d = MathUtils.randomGaussian(deviation);
        return Vector.add(p, Vector.randomWithLength(d));
    }

    public static PointsPositionGenerator fromPoint(Vector point, double deviation) {
        return new PointsPositionGenerator(List.of(new WeightedPoint(point, 1.0)), deviation);
    }

    public static PointsPositionGenerator fromPoints(List<Vector> points, double deviation) {
        if(points == null || points.size() < 1) throw new IllegalArgumentException();
        List<WeightedPoint> weightedPoints = new ArrayList<>(points.size());
        double v = 1.0 / points.size();
        for(Vector point : points) {
            weightedPoints.add(new WeightedPoint(point, v));
        }
        return new PointsPositionGenerator(weightedPoints, deviation);
    }
}
