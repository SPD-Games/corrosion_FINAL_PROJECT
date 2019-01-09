/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * a abstract class that models mouse controls
  */
package corrosion.input.bind;

import java.awt.Point;

abstract public class MouseBindable{
  abstract public void pressed(Point p);
  abstract public void released(Point p);
  abstract public void clicked(Point p);
}
