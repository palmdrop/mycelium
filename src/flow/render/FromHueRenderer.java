package flow.render;

import flow.generation.particle.ParticleSettings;
import flow.particle.Particle;
import flow.particle.ParticleData;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class FromHueRenderer implements ParticleRenderer {
    private final ParticleSettings ps;
    private final float alpha;
    private final float sat, bri;
    private final float startHue, endHue;

    private boolean colorsCalculated = false;
    private int c1, c2;

    private float particleSize;

    public FromHueRenderer(ParticleSettings ps, float alpha, float sat, float bri, float startHue, float endHue, float particleSize) {
        this.ps = ps;
        this.alpha = alpha;
        this.sat = sat;
        this.bri = bri;
        this.startHue = startHue;
        this.endHue = endHue;
        this.particleSize = particleSize;
    }


    @Override
    public void render(ParticleData pd, PGraphics g) {
        if(!colorsCalculated) {
            g.colorMode(PApplet.HSB);



            //float hue1 = (float) (Math.random() * 255);
            //float hue2 = (hue1 + 255 * hueVariation) % 256;
            c1 = g.color(startHue, sat, bri);
            c2 = g.color(endHue, sat, bri);

            colorsCalculated = true;
        }

        //g.colorMode(PApplet.RGB);

        Particle p = pd.p;
        g.strokeWeight(particleSize);

        float n = (float) (p.getMass() / ps.maxMass);

        int c = g.lerpColor(c1, c2, n);

        g.stroke(c, alpha);

        float x = (float) p.getX();
        float y = (float) p.getY();
        float px = (float) pd.previousPos.getX();
        float py = (float) pd.previousPos.getY();
        g.line(px, py, x, y);
    }
}
