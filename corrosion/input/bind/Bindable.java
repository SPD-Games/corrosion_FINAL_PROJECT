/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * abstract class which helps with key binds
  */
package corrosion.input.bind;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

abstract public class Bindable{

  /**
  * checks if key is pressed
  * @param e a key event
  */
  abstract public void pressed(KeyEvent e);

  /**
  * checks if key is released
  * @param e a key event
  */
  abstract public void released(KeyEvent e);

  /**
  * handles typing
  * @param e a key event
  */
  abstract public void typed();
}
