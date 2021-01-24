package flow.sketch;

import util.vector.Vector;

public class Golden4K extends SketchSettings {
    public Golden4K() {
        windowWidth = 1000;
        windowHeight = (int) (windowWidth / Math.sqrt(2));

        quality = 4.0;

        width = (int) (windowWidth * quality);
        height = (int) (windowHeight * quality);

        center = new Vector(width/2.0, height/2.0);

        paused = false;
        debug = false;
        pretty = true;
        hidden = false;
        iterationsPerFrame = 100;
    }
}
