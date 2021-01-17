import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import util.geometry.Rectangle;
import text.TextTools;
import util.space.WeightedMap;

public class TestMain extends PApplet {
    private final int screenWidth = 1000;
    private final int screenHeight = (int)(screenWidth / Math.sqrt(2));

    private final double quality = 4.0;
    private final int width = (int) (screenWidth * quality);
    private final int height = (int) (screenHeight * quality);

    // Setup
    public void settings() {
        size(screenWidth, screenHeight);
        smooth(5);
    }

    private PGraphics canvas;
    private final double textSize = 500;

    private WeightedMap wm;
    private Rectangle r;

    public void setup() {

        //TODO: spawn particles in strict, gridlike patterns, mathematically, systematically, and then let that
        //TODO: contrast with their organic structure!

        //TODO: visualize using dynamic text packing??? letters with varying "size" (use sorting algorithm) to visualize
        //TODO: greater opacity, vary colors depending on underlying content
        //TODO: maybe also vary detail (have areas with more tightly packed text (find a way to subdivide, perhaps
        //TODO: using a quad tree (great idea???) where the underlying data is more detailed (how to determine this though???)

        //TODO: combine designs with type, with text! let design instagram inspire! text boxes, poetic stuff? or just words,
        //TODO: names of the piece, generated into the sketch? test out

        canvas = createGraphics(width, height);

        canvas.beginDraw();

        canvas.fill(255);
        canvas.textSize((float) textSize);
        r = TextTools.renderCentered(canvas, "COALESCE", width/2.0f, height/2.0f);

        canvas.noFill();
        canvas.stroke(255);
        //Rectangle.render(canvas, r);

        canvas.endDraw();

        //Setup map
        canvas.beginDraw();
        canvas.loadPixels();

        wm = WeightedMap.mapFromGraphics(canvas, r);

        background(0);
        //PImage img = canvas.get();
        //img.resize(screenWidth, screenHeight);
        //image(img, 0, 0);
    }



    public void draw() {
        long time = System.currentTimeMillis();

        for(int i = 0; i < 10000; i++) {
            int[] p = wm.getRandom();
            float x = (float) ((p[0] + r.x) / quality);
            float y = (float) ((p[1] + r.y) / quality);

            stroke((float) (255 * Math.random()), (float)(255 * Math.random()), (float)(255 * Math.random()));
            point(x, y);
        }

        System.out.println(System.currentTimeMillis() - time);
    }

    public static void main(String[] args) {
        PApplet.main("TestMain");
    }

    public void keyPressed() {
    }
}
