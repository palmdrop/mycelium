package flow.generation;

import util.vector.ReadVector;

public interface WeightMap {
    default double get(ReadVector p) { return get((int)p.getX(), (int)p.getY()); }
    double get(int x, int y);
}
