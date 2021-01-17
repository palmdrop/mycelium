package background;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import util.vector.ReadVector;

public class BackgroundTools {



    public static PGraphics toBlurredBackground(PApplet p, PGraphics original, double scale, ReadVector offset, double blur, int alpha) {
        if(scale < 1.0 || p == null || original == null || blur < 0.0) throw new IllegalArgumentException();

        int sampleWidth = (int) (original.width / scale);
        int sampleHeight = (int) (original.height / scale);
        int sampleX = (int) ((original.width - sampleWidth) / 2 + offset.getX());
        int sampleY = (int) ((original.height - sampleHeight) / 2 + offset.getX());

        PImage segment = original.get(sampleX, sampleY, sampleWidth, sampleHeight);
        segment.resize(original.width, original.height);

        PGraphics g = p.createGraphics(original.width, original.height);
        g.beginDraw();
        g.image(segment, 0, 0);
        g.filter(PApplet.BLUR, (float) blur);
        g.tint(256, alpha);
        g.endDraw();

        return g;
    }


}
