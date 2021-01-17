package util.geometry;

import util.vector.ReadVector;
import util.vector.Vector;

public class Circle {
     private final Vector pos;
     private final double radius;

     public Circle(double x, double y, double radius) {
          this.pos = new Vector(x, y);
          this.radius = radius;
     }
     public Circle(Vector pos, double radius) {
          this.pos = pos;
          this.radius = radius;
     }

     public double getRadius() {
          return radius;
     }

     public double getX() {
          return pos.getX();
     }

     public double getY() {
          return pos.getY();
     }

     public Vector getPos() {
          return pos;
     }

     public boolean isInside(double x, double y) {
          return isInside(new Vector(x, y));
     }

     public boolean isInside(ReadVector point) {
          return pos.distSq(point) < radius * radius;
     }
}
