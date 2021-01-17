package flow.generation.particle;

import flow.generation.position.PositionGenerator;

public class PS1 extends ParticleSettings {
    public PS1(PositionGenerator positionGenerator) {
        this.positionGenerator = positionGenerator;
        particleCount = 1000;

        minMass = 10;
        maxMass = 30;
        maxStartSpeed = 0.5;

        maxRandomForce = 0.005;
        friction = 0.05;
        shrink = 0.01;
    }
}
