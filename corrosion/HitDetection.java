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

  private static boolean circleLine(Ellipse2D s1, Line2D s2){
    return s1.contains(s2.getP1());
  }
}
