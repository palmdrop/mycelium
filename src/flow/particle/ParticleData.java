package flow.particle;

import util.vector.Vector;

public class ParticleData {
    public final Particle p;
    public Vector previousPos;
    public boolean hasEntered;

    public ParticleData(Particle p) {
        this.p = p;
        previousPos = new Vector(p.getPos());
        hasEntered = false;
    }
}
