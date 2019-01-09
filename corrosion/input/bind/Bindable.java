/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * abstract class which helps with key binds
  */
package corrosion.input.bind;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

abstract public class Bindable{
  abstract public void pressed(KeyEvent e);
  abstract public void released(KeyEvent e);
  abstract public void typed();
}
