package flow.gravity;

import util.vector.ReadVector;
import util.vector.Vector;

public interface Gravity {
    Vector calculate(ReadVector position, double mass);
}
