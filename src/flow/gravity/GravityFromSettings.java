package flow.gravity;

import flow.gravity.settings.GravitySettings;
import util.vector.ReadVector;
import util.vector.Vector;

public class GravityFromSettings implements Gravity{
    private final GravitySettings s;

    public GravityFromSettings(GravitySettings s) {
        this.s = s;
    }

    @Override
    public Vector calculate(ReadVector position, double mass) {
        return s.gravity.calculate(position, mass);
    }
}
