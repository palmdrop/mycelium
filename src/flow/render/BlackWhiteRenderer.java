package flow.render;

import flow.generation.particle.ParticleSettings;
import flow.particle.Particle;
import flow.particle.ParticleData;
import processing.core.PGraphics;

public class BlackWhiteRenderer implements ParticleRenderer {
    private final ParticleSettings ps;
    private final boolean asLines;

    public BlackWhiteRenderer(ParticleSettings ps, boolean asLines) {
        this.ps = ps;
        this.asLines = asLines;
    }


    @Override
    public void render(ParticleData pd, PGraphics g) {
        Particle p = pd.p;
        g.strokeWeight(1.0f);

        //double alpha = pd.p.getMass() * pd.p.getVel().length();
        double alpha = 20;

        int c1 = g.color(255, 255, 255); // white
        //int c1 = color(50, 0, 0);

        //int c2 = color(126, 40, 158); //purple
        //int c2 = color(120, 0, 0);
        int c2 = c1;

        float n = (float) (pd.p.getMass() / ps.maxMass);

        int c = g.lerpColor(c1, c2, 1 - n);

        g.stroke(c, (float) alpha);
        float x = (float) pd.p.getX();
        float y = (float) pd.p.getY();
        if(asLines) {
            float px = (float) pd.previousPos.getX();
            float py = (float) pd.previousPos.getY();
            g.line(px, py, x, y);
        } else {
            g.point((float) p.getPos().getX(), (float) p.getPos().getY());
        }
    }
}
