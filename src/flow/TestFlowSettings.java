package flow;

import flow.generation.SimpleWeightMap;
import flow.generation.bubbleField.settings.BubbleFieldSettings;
import flow.generation.particle.ParticleSettings;
import flow.generation.position.*;
import flow.gravity.*;
import flow.render.FromHueRenderer;
import flow.sketch.SketchSettings;
import processing.core.PApplet;
import processing.core.PGraphics;
import text.TextTools;
import util.geometry.Rectangle;
import util.vector.Vector;

import java.util.concurrent.atomic.AtomicInteger;

public class TestFlowSettings extends FlowSettings {
    public TestFlowSettings(PApplet p, int sw, int sh, double q) {
        sketch = new SketchSettings() {
            SketchSettings init() {
                windowWidth = sw;
                windowHeight = sh;
                quality = q;

                width = (int)(windowWidth * q);
                height = (int)(windowHeight * q);

                center = new Vector(width/2.0, height/2.0);

                paused = false;
                debug = false;
                pretty = true;
                hidden = false;
                iterationsPerFrame = 100;
                return this;
            }
        }.init();

        int textSize = (int) (sketch.height / 3.8);
        String text = "AGGREGATE";

        PGraphics graphics = p.createGraphics(sketch.width, sketch.height);
        graphics.beginDraw();
        graphics.background(0);

        graphics.textSize(textSize);
        graphics.fill(255);

        Rectangle r = TextTools.renderCentered(graphics, text, (float)sketch.center.getX(), (float)sketch.center.getY());

        graphics.endDraw();

        //background = spawnGraphics;

        double padding = 100;


        double dist = sketch.width / 4;
        position
                //= new WeightedPositionGenerator(spawnGraphics, r, 50.0);
                //= new AreaPositionGenerator(sketch.width, sketch.height);
                //= new CirclePositionGenerator(sketch.center, sketch.height/6.0, 0.1f, true);
                = new AggregatePositionGenerator(
                    //new CirclePositionGenerator(sketch.center, sketch.height/3.0, 0.2f, true)
                    //new CirclePositionGenerator(sketch.center, sketch.height/2.5, 0.04f, true)
                new DynamicCirclePositionGenerator(sketch.center,
                        sketch.height/6,
                        sketch.height/2.5,
                        0.01,
                        0.36,
                        10,
                        5,
                        5,
                        1.0)
                );
                //= PointsPositionGenerator.fromPoint(sketch.center, 600.0f);
                //= PointsPositionGenerator.fromPoint(new Vector(sketch.width/2.0, -50), 100f);
                //= new LinePositionGenerator(new Vector(0, -50), new Vector(sketch.width, -50), 100, true);
                //= new LinePositionGenerator(new Vector(0, 0), new Vector(sketch.width, 0), 10, true);
                //= new LinePositionGenerator(new Vector(sketch.width/2.0, 0), new Vector(sketch.width/2.0, sketch.height), 100, true);
                /*= new AggregatePositionGenerator(
                    new LinePositionGenerator(new Vector(sketch.width/3.0, 0), new Vector(sketch.width/3.0, sketch.height), 140, true),
                    new LinePositionGenerator(new Vector(2*sketch.width/3.0, 0), new Vector(2*sketch.width/3.0, sketch.height), 140, true)
                );*/
                /*= new AggregatePositionGenerator(
                    new LinePositionGenerator(new Vector(-padding, -padding), new Vector(sketch.width + padding, -padding), padding, true),
                    new LinePositionGenerator(new Vector(sketch.width + padding, -padding), new Vector(sketch.width + padding, sketch.height + padding), padding, true),
                    new LinePositionGenerator(new Vector(-padding, sketch.height + padding), new Vector(sketch.width + padding, sketch.height + padding), padding, true),
                    new LinePositionGenerator(new Vector(-padding, -padding), new Vector(-padding, sketch.height + padding), padding, true)


                );*/
                /*= new AggregatePositionGenerator(
                    PointsPositionGenerator.fromPoint(
                            Vector.fromAngle(Math.PI * 2 * Math.random()).mult(dist * MathUtils.random(0.8, 1.2)).add(sketch.center),
                            200),
                PointsPositionGenerator.fromPoint(
                        Vector.fromAngle(Math.PI * 2 * Math.random()).mult(dist * MathUtils.random(0.8, 1.2)).add(sketch.center),
                        200),
                PointsPositionGenerator.fromPoint(
                        Vector.fromAngle(Math.PI * 2 * Math.random()).mult(dist * MathUtils.random(0.8, 1.2)).add(sketch.center),
                        200)
                );*/


        particle = new ParticleSettings() {
            ParticleSettings init() {
               positionGenerator = position;
               particleCount = 1000;

               minMass = 5;
               maxMass = 15;
               maxStartSpeed = 0.5;

               maxRandomForce = 0.04;
               friction = 0.01;
               shrink = 0.02f;
               return this;
            }
        }.init();

        bubbles = new BubbleFieldSettings() {
            BubbleFieldSettings init() {
                this.width = sketch.width;
                this.height = sketch.height;

                frequency = 0.008f;
                noisePow = 2.0f;

                minRadius = 30;
                maxRadius = 150;

                tries = 55;

                //minForce = maxForce = 0.0073f;
                minForce = 0.03f;
                maxForce = 0.10f;
                distPow = 1.0f;

                //forceMap = WeightedMap.mapFromGraphics(graphics, new Rectangle(0, 0, sketch.width, sketch.height));
                forceMap = new SimpleWeightMap(p, graphics);
                //forceMap

                return this;
            }
        }.init();

        AtomicInteger iteration = new AtomicInteger();
        double offset = Math.random() * PApplet.TWO_PI;

        double cornerConstant = -0.01;
        double cornerPow = 0.3;

        gravity = new AggregateGravity(
                //new GravitySwirl(0.5f, sketch.center, 0.5),
                //new GravitySwirl(0.007f, sketch.center, 0.0)
                //new GravityFromSettings(new NaturalG1())
                new GravityRotation(0.01, sketch.center, 1.4, 0.01),
                GravityFromSources.fromPoint(sketch.center, 1.0, -0.02, 0.5)
                //new GravityDirection(new Vector(0, 1), 0.005, 0.0)
                //new GravityMap(new SimpleWeightMap(p, graphics), 0.006),

                /*GravityFromSources.fromPoint(new Vector(0, 0), 1.0, cornerConstant, cornerPow),
                GravityFromSources.fromPoint(new Vector(sketch.width, 0), 1.0, cornerConstant, cornerPow),
                GravityFromSources.fromPoint(new Vector(sketch.width, sketch.height), 1.0, cornerConstant, cornerPow),
                GravityFromSources.fromPoint(new Vector(0, sketch.height), 1.0, cornerConstant, cornerPow),
                 */

                //GravityFromSources.fromPoint(sketch.center, 1.0, 0.0002, 0.0f)

                //new GravityNoise(p, 0.003, 0.0045)

                /*(position, mass) -> {
                    double constant = 0.0008;
                    double distPow = 0.0;
                    double speed = 0.0000002;
                    double radius = sketch.height / 2.4;

                    float noiseFreq = 0.01f;
                    double noiseAmount = radius * 0.2;

                    double a = (speed * iteration.get()) % PApplet.TWO_PI;

                    float x = (float) (Math.cos(a + offset) * radius + sketch.center.getX());
                    float y = (float) (Math.sin(a  + offset) * radius + sketch.center.getY());

                    Vector v = new Vector(x, y);
                    //double n = p.noise(noiseFreq * x, noiseFreq * y);

                    ////Vector off = Vector.sub(v, sketch.center).mult(noiseAmount * n / radius);
                    double n = iteration.get() * 3 * speed + offset;
                    Vector off = Vector.fromAngle(n).mult(noiseAmount);


                    iteration.getAndIncrement();

                    Gravity g = GravityFromSources.fromPoint(Vector.add(v, off), 1.0, constant, distPow);
                    return g.calculate(position, mass);
                }*/
        );

        renderer =
             //new BlackWhiteRenderer(particle, true);
             new FromHueRenderer(particle, 15, 150, 255, 255 * 283.0f / 360, 255 * 180.0f / 360, 1);
    }
}
