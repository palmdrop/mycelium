package flow;

import flow.generation.bubbleField.settings.BFS1;
import flow.generation.particle.PS1;
import flow.generation.position.AreaPositionGenerator;
import flow.generation.position.CirclePositionGenerator;
import flow.generation.position.PointsPositionGenerator;
import flow.gravity.settings.CenterG1;
import flow.gravity.settings.NaturalG1;
import flow.render.BlackWhiteRenderer;
import flow.sketch.Golden4K;

public class FlowSettings1 extends FlowSettings {
    public FlowSettings1() {
        background = null;

        sketch = new Golden4K();

        position
                //= new CirclePositionGenerator(sketch.center, sketch.height/2.3, 0.03, true);
                //= PointsPositionGenerator.fromPoint(center, 50);
                //= PointsPositionGenerator.fromPoint(new Vector(sketch.width/2.0, 50), 50);
                = new AreaPositionGenerator(sketch.width, sketch.height);
        particle = new PS1(position);

        bubbles = new BFS1(sketch.width, sketch.height);

        gravity =
                //new CenterG1(sketch.center).gravity;
                new NaturalG1().gravity;

        renderer = new BlackWhiteRenderer(particle, true);



    }
}
