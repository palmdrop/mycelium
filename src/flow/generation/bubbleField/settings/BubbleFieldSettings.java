package flow.generation.bubbleField.settings;

import flow.generation.WeightMap;
import util.space.region.Area;

public abstract class BubbleFieldSettings {
    public double width;
    public double height;

    public float frequency;
    public float noisePow;

    public float minRadius;
    public float maxRadius;

    public int tries;

    public WeightMap forceMap;

    public float minForce, maxForce;
    public float distPow;

    public BubbleFieldSettings(double width, double height) {
        this.width = width;
        this.height = height;

        frequency = 0.01f;
        noisePow = 2.0f;

        minRadius = 30;
        maxRadius = 100;

        forceMap = null;

        minForce = maxForce = 0.3f;
        distPow = 1.0f;
    }

    public BubbleFieldSettings() {
    }
}
