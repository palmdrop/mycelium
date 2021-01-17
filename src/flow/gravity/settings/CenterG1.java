package flow.gravity.settings;

import flow.gravity.GravityFromSources;
import util.vector.Vector;

public class CenterG1 extends GravitySettings {
    public CenterG1(Vector center) {
        float constant = 0.1f;
        float distPow = 1.0f;
        float mass = 1.0f;
        gravity = GravityFromSources.fromPoint(center, mass, constant, distPow);
    }

}
