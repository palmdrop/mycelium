package flow.gravity.settings;

import flow.gravity.GravityDirection;
import util.vector.Vector;

public class NaturalG1 extends GravitySettings {
    public NaturalG1() {
        float constant = 0.012f;
        float distPow = 1.0f;
        float mass = 1.0f;
        gravity = new GravityDirection(new Vector(0, 1), constant);
    }

}
