/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * a abstract class that models mouse controls
  */
package corrosion.input.bind;

import java.awt.Point;

abstract public class MouseBindable{

  /**
  * handles the button pressed
  * @param p the point where the mouse clicked
  */
  abstract public void pressed(Point p);

  /**
  * handles the button released
  * @param p the point where the mouse clicked
  */
  abstract public void released(Point p);

  /**
  * handles the button clicked
  * @param p the point where the mouse clicked
  */
  abstract public void clicked(Point p);
}
