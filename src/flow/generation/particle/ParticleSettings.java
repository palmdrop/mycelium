package flow.generation.particle;

import flow.generation.position.PositionGenerator;

public abstract class ParticleSettings {
    public int particleCount; //Particles in frame

    public PositionGenerator positionGenerator;

    public double minMass;
    public double maxMass;
    public double maxStartSpeed;

    public double maxRandomForce;
    public double friction;
    public double shrink;
}
