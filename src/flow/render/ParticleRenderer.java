package flow.render;

import flow.particle.ParticleData;
import processing.core.PGraphics;

public interface ParticleRenderer {
    void render(ParticleData pd, PGraphics g);
}
