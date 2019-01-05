package corrosion;
import java.awt.*;
import java.awt.geom.*;

public class HitDetection{
  public static boolean hit(Shape s1, Shape s2){
    if (s1 == null || s2 == null){
      return false;
    }
    if (s1 instanceof Ellipse2D && s2 instanceof Line2D){
      return circleLine((Ellipse2D)s1, (Line2D) s2);
    }
    Area a1 = new Area(s1);
    Area a2 = new Area(s2);
    a1.intersect(a2);
    return !a1.isEmpty();
  }

  /**
  * Checks if a circle intersects/contains a line segment
  * @param s1 a circle
  * @param s2 a line segment
  */
  private static boolean circleLine(Ellipse2D s1, Line2D s2){
    return s2.ptSegDistSq(s1.getCenterX(),s1.getCenterY()) <= s1.getWidth()*s1.getWidth()/4;
  }
}
