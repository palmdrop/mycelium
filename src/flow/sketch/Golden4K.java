package flow.sketch;

import util.vector.Vector;

public class Golden4K extends SketchSettings {
    public Golden4K() {
        screenWidth = 1000;
        screenHeight = (int) (screenWidth / Math.sqrt(2));

        quality = 4.0;

        width = (int) (screenWidth * quality);
        height = (int) (screenHeight * quality);

        center = new Vector(width/2.0, height/2.0);

        paused = false;
        debug = false;
        pretty = true;
        hidden = false;
        iterationsPerFrame = 100;
    }
}
