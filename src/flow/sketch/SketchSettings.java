package flow.sketch;

import util.vector.Vector;

public abstract class SketchSettings {
    public int screenWidth;
    public int screenHeight;

    public double quality;

    public int width; //should be screenWidth * quality
    public int height; //should be screenHeight * quality

    public Vector center; //should be (width/2, height/2)

    public boolean paused;
    public boolean debug;
    public boolean pretty;
    public boolean hidden;

    public int iterationsPerFrame;

    public void togglePaused() {
        paused = !paused;
    }
    public void toggleDebug() {
        debug = !debug;
    }
    public void togglePretty() {
        pretty = !pretty;
    }
    public void toggleHidden() { hidden = !hidden; }
}
