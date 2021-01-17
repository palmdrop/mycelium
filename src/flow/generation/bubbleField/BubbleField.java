package flow.generation.bubbleField;

import flow.generation.WeightMap;
import flow.generation.bubbleField.settings.BubbleFieldSettings;
import processing.core.PApplet;
import util.math.MathUtils;
import flow.generation.PoissonDiskSampling;
import util.space.tree.QuadTree;
import util.vector.ReadVector;
import util.vector.Vector;

import java.util.ArrayList;
import java.util.List;

public class BubbleField {
    private final List<Bubble> bubbles;
    private final QuadTree<Bubble> bubbleTree;
    private final BubbleFieldSettings s;
    private final PApplet p;

    private double maxGeneratedBubbleRadius;

    private Vector[] vectorfield;

    public BubbleField(BubbleFieldSettings settings, PApplet p) {
        this.s = settings;
        this.p = p;

        bubbles = new ArrayList<>();
        bubbleTree = new QuadTree<>(0, 0, s.width, s.height, 4); //TODO: experiment with cap?

        vectorfield = null;
    }

    public void generate() {
        WeightMap radiusGenerator = (x, y) -> {
            float n = p.noise((x * s.frequency), (y * s.frequency));
            return Math.pow(n, s.noisePow);
            //return PApplet.map((float) Math.pow(n, s.noisePow), 0, 1, s.minRadius, s.maxRadius);
        };

        List<Vector> positions = PoissonDiskSampling.generate(s.width, s.height, s.tries, radiusGenerator, s.minRadius, s.maxRadius);

        for(Vector pos : positions) {
            float radius = PApplet.map((float)radiusGenerator.get(pos), 0, 1, s.minRadius, s.maxRadius);
            maxGeneratedBubbleRadius = Math.max(maxGeneratedBubbleRadius, radius);

            double force = 0.0;
            if(s.forceMap == null) {
                force = MathUtils.random(s.minForce, s.maxForce);
            } else {
                force = PApplet.map((float)s.forceMap.get(pos), 0, 1, s.minForce, s.maxForce);
            }

            Bubble bubble = new Bubble(pos, radius, force, s.distPow);
            bubbleTree.insert(pos, bubble);
            bubbles.add(bubble);
        }
    }

    public Vector getForce(ReadVector point) {
        if(vectorfield != null) {
            int x = (int) Math.max(0, Math.min(point.getX(), s.width));
            int y = (int) Math.max(0, Math.min(point.getY(), s.height));
            return vectorfield[(int) (x + y * s.width)];
        }

        Vector sumForce = new Vector();
        List<Bubble> neighbors = getNeighbours(point);
        for (Bubble b : neighbors) {
            if (b.isInside(point)) {
                Vector dir = Vector.sub(point, b.getPos());
                double dist = dir.length();
                double m = 1.0 - dist / b.getRadius();
                Vector force = Vector.mult(dir, b.getForce() * Math.pow(m, b.getDistPow()) / dist);
                sumForce.add(force);
            }
        }
        return sumForce;
    }

    public void calculateVectorField() {
        Vector[] vf = new Vector[(int) (s.width * s.height)];
        for(int x = 0; x < s.width; x++) for(int y = 0; y < s.height; y++) {
            Vector f = getForce(new Vector(x, y));
            vf[(int) (x + y * s.width)] = f;
        }
        vectorfield = vf;
    }

    public List<Bubble> get() {
        return bubbles;
    }

    public List<Bubble> getNeighbours(ReadVector point) {
        return bubbleTree.query(point, maxGeneratedBubbleRadius);
    }

    public QuadTree<Bubble> getTree() {
        return bubbleTree;
    }

    public double getMaxGeneratedBubbleRadius() {
        return maxGeneratedBubbleRadius;
    }
}
