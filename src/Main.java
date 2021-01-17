import background.BackgroundTools;
import flow.FS1;
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

import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {

    private FlowSettings s;

    private int screenWidth;
    private int screenHeight;

    private int width;
    private int height;

    private Area area;

    // Canvas and data
    private PGraphics canvas;
    private List<ParticleData> particles;
    private BubbleField bubbles;

    // Setup
    public void settings() {
        //size(screenWidth, screenHeight);
        size(20, 20);
        smooth(5);
    }

    public void setup() {
        //TODO: spawn particles in strict, gridlike patterns, mathematically, systematically, and then let that
        //TODO: contrast with their organic structure!

        //TODO: visualize using dynamic text packing??? letters with varying "size" (use sorting algorithm) to visualize
        //TODO: greater opacity, vary colors depending on underlying content
        //TODO: maybe also vary detail (have areas with more tightly packed text (find a way to subdivide, perhaps
        //TODO: using a quad tree (great idea???) where the underlying data is more detailed (how to determine this though???)

        //TODO: combine designs with type, with text! let design instagram inspire! text boxes, poetic stuff? or just words,
        //TODO: names of the piece, generated into the sketch? test out

        //TODO: mycelium text, spawn particles where text is written
        //TODO: artistic overlay with "real" text, make legible, grainy, blueish background (use map to spawn particles quickly)
        //TODO: have the text mirror the effect generated! or contrast it
        //TODO: for example, COALESCE for text that grows together, fade for text that fades out, SWIRL for text that swirls, etc

        //TODO: vary particle color using underlying image

        //TODO: perpetual motion by fading and drawing anew!

        //TODO: layer sketches using thick blur and scaling! produce fake 3D effect
        //TODO: scaling effect, create sketch, size up, blur, draw in the background! generate new for each layer!
        //TODO: fake 3d effect, or fading between layers, by changing size dynamically, changing blur, moving "to the front"
        //TODO: go from legible to less legible

        //TODO: get significant speedup by converting the circles to a vector field! requires a lot of data, but should
        //TODO: be quick to access and calculate velocities

        //TODO: apply same effect on other stuff. Try using textflow (circles packed on letter regions) to draw text!
        //TODO: (test if circle is on text! if yes, increase or decrease force!!!! TRY TRY TRY!!!!)

        System.out.println("SETTINGS SETUP");
        s =
                new TestFlowSettings(this, 1000, 1000, 4);
                //new FS1();
        surface.setSize(s.sketch.screenWidth, s.sketch.screenHeight);
        width = s.sketch.width;
        height = s.sketch.height;
        screenWidth = s.sketch.screenWidth;
        screenHeight = s.sketch.screenHeight;
        area = Area.build(width, height);

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

                    /*List<Bubble> neighbors = bubbles.getNeighbours(p.getPos());
                    for (Bubble b : neighbors) {
                        if (b.isInside(p.getPos())) {
                            Vector dir = Vector.sub(p.getPos(), b.getPos());
                            double dist = dir.length();
                            double m = 1.0 - dist / b.getRadius();
                            Vector force = Vector.mult(dir, b.getForce() * Math.pow(m, b.getDistPow()) / dist);
                            p.addForce(force);
                        }
                    }*/
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
            image(s.background, 0, 0, s.sketch.screenWidth, s.sketch.screenHeight);
        }

        if(s.sketch.pretty && !s.sketch.hidden) {
            PImage screen = canvas.get();
            screen.resize(s.sketch.screenWidth, s.sketch.screenHeight);
            image(screen, 0, 0);
        } else if(!s.sketch.hidden){
            image(canvas, 0, 0, s.sketch.screenWidth, s.sketch.screenHeight);
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
            case 'q': {
                PGraphics toSave = createGraphics(s.sketch.width, s.sketch.height);
                toSave.beginDraw();
                toSave.background(0);
                toSave.image(canvas, 0, 0);
                toSave.endDraw();

                PImage image = toSave.get();
                image.resize(screenWidth, screenHeight);
                image.save("pictures/flow/final/pic" + System.currentTimeMillis() + ".png");
                System.out.println("saved!");
            }
            case 'b': {
                PGraphics b = BackgroundTools.toBlurredBackground(this, canvas, 2, new Vector(), 10, 50);
                s.background = b;
            } break;
        }
    }
}
