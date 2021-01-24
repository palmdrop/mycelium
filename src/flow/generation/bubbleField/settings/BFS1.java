package flow.generation.bubbleField.settings;

public class BFS1 extends BubbleFieldSettings {
    public BFS1(double width, double height) {
        this.width = width;
        this.height = height;

        frequency = 0.01f;
        noisePow = 3.0f;

        minRadius = 6;
        maxRadius = 300;

        tries = 40;

        minForce = 0.01f;
        maxForce = 0.03f;
        distPow = 3.0f;
    }
}
