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

    System.out.println("COLLISION NOT FOUND");
    return false;
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
