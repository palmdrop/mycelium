package flow.generation;

import flow.generation.WeightMap;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class SimpleWeightMap implements WeightMap {
    private final int width, height;
    private final double[] weights;

    public SimpleWeightMap(int width, int height, double[] weights) {
        this.width = width;
        this.height = height;
        this.weights = weights;
    }

    public SimpleWeightMap(PApplet p, PImage img) {
        this.width = img.width;
        this.height = img.height;

        weights = new double[width * height];

        img.loadPixels();

        for(int x = 0; x < width; x++) for(int y = 0; y < height; y++) {
            double v = p.red(img.pixels[x + y * width]);
            weights[x + y * width] = v / 256.0;
        }
    }


    @Override
    public double get(int x, int y) {
        return weights[x + y * width];
    }
}
