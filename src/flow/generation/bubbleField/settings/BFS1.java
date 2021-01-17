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

        //force = 0.003f;
        minForce = 0.003f;
        maxForce = 0.003f;
        distPow = 3.0f;
    }
}
