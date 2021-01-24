import flow.FlowSettings;
import flow.TestFlowSettings;
import flow.generation.bubbleField.Bubble;
import flow.generation.bubbleField.BubbleField;
import flow.particle.Particle;
import flow.particle.ParticleData;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import util.math.MathUtils;
import util.space.region.Area;
import util.vector.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {
    private FlowSettings s;
    private Area area;

    // Canvas and data
    private PGraphics canvas;
    private List<ParticleData> particles;
    private BubbleField bubbles;

    // Setup
    public void settings() {
        size(1, 1);
        smooth(5);
    }

    public void setup() {
        System.out.println("SETTINGS SETUP");
        s =
                new TestFlowSettings(this, 1000, 1000, 4);
                //new FlowSettings1();

        width = s.sketch.width;
        height = s.sketch.height;
        area = Area.build(width, height);

        surface.setSize(s.sketch.windowWidth, s.sketch.windowHeight);
        surface.setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - s.sketch.windowWidth / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - s.sketch.windowHeight / 2
                );

        System.out.println("PARTICLE GENERATION");
        particles = new ArrayList<>();
        for(int i = 0; i < s.particle.particleCount; i++) {
            particles.add(buildParticle());
        }

        System.out.println("BUBBLE FIELD GENERATION");
        bubbles = new BubbleField(s.bubbles, this);
        bubbles.generate();

        //System.out.println("BUBBLE FIELD OPTIMIZATION");
        //bubbles.calculateVectorField();

        // Setup canvas
        System.out.println("SETUP CANVAS");
        canvas = createGraphics(s.sketch.width, s.sketch.height);
        canvas.smooth(4);
        noiseSeed(System.currentTimeMillis());

        System.out.println("BEGIN SKETCH");
    }

    private ParticleData buildParticle() {
        double mass = MathUtils.random(s.particle.minMass, s.particle.maxMass);
        Vector startVelocity = Vector.randomWithLength(Math.random() * s.particle.maxStartSpeed);
        Particle p = new Particle(s.position.generate(), startVelocity, 1, mass);
        return new ParticleData(p);
    }

    public void draw() {
        long time = System.currentTimeMillis();

        if(!s.sketch.paused) {
            canvas.beginDraw();

            for(int iteration = 0; iteration < s.sketch.iterationsPerFrame; iteration++) {
                for (int j = 0; j < particles.size(); j++) {
                    ParticleData pd = particles.get(j);
                    Particle p = pd.p;
                    pd.previousPos.set(p.getPos());

                    boolean isInside = area.isInside(p.getX(), p.getY());
                    if(!isInside && pd.hasEntered || p.getMass() <= 0.0) {
                        //Respawn particle
                        particles.set(j, buildParticle());
                        continue;
                    } else if(isInside) {
                        pd.hasEntered = true;
                        s.renderer.render(pd, canvas);
                    }

                    p.addForce(bubbles.getForce(p.getPos()));

                    Vector randomForce = Vector.randomWithLength(MathUtils.random(s.particle.maxRandomForce));
                    Vector gravityForce = s.gravity.calculate(p.getPos(), p.getMass());
                    p.addForce(randomForce);
                    p.addForce(gravityForce);

                    p.setMass(p.getMass() - s.particle.shrink);
                    p.applyFriction(s.particle.friction);
                    p.update();
                }
            }
            canvas.endDraw();
        }

        background(0);
        if(s.background != null) {
            image(s.background, 0, 0, s.sketch.windowWidth, s.sketch.windowHeight);
        }

        if(s.sketch.pretty && !s.sketch.hidden) {
            PImage screen = canvas.get();
            screen.resize(s.sketch.windowWidth, s.sketch.windowHeight);
            image(screen, 0, 0);
        } else if(!s.sketch.hidden){
            image(canvas, 0, 0, s.sketch.windowWidth, s.sketch.windowHeight);
        } else {
            background(0);
        }

        if(s.sketch.debug) {
            List<Bubble> bubbleList = bubbles.get();
            noFill();
            stroke(0, 50, 0);
            for(Bubble b : bubbleList) {
                float x = (float) (b.getX() / s.sketch.quality);
                float y = (float) (b.getY() / s.sketch.quality);
                float diameter = (float) (b.getRadius() * 2 / s.sketch.quality);
                ellipse(x, y, diameter, diameter);
            }
        }
        System.out.println(System.currentTimeMillis() - time);
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void keyPressed() {
        switch(key) {
            case 'r': setup(); break;
            case ' ': s.sketch.togglePaused(); break;
            case 'd': s.sketch.toggleDebug();  break;
            case 'm': s.sketch.togglePretty(); break;
            case 'h': s.sketch.toggleHidden(); break;
            case 's': {
                PGraphics toSave = createGraphics(s.sketch.width, s.sketch.height);
                toSave.beginDraw();
                toSave.background(0);
                if(s.background != null) {
                    toSave.image(s.background, 0, 0);
                }
                toSave.image(canvas, 0, 0);
                toSave.endDraw();
                toSave.save("pictures/flow/pic" + System.currentTimeMillis() + ".png");
                System.out.println("saved!");
            } break;
            //case 'b': {
            //    PGraphics b = BackgroundTools.toBlurredBackground(this, canvas, 2, new Vector(), 10, 50);
            //    s.background = b;
            //} break;
        }
    }
}
