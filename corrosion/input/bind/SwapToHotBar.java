/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * abstract class which helps with key binds
  */
package corrosion.input.bind;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import corrosion.entity.player.MainPlayer;
public class SwapToHotBar extends Bindable{

  /**
  * set item in main player hotbar
  * @param e the key event
  */
  public void pressed(KeyEvent e){
    MainPlayer.getMainPlayer().getInvetory().swapToHotBar(e.getKeyCode()-49);
  }

  /**
  * method not used in this class
  */
  public void released(KeyEvent e){}

  /**
  * method not used in this class
  */
  public void typed(){}
}
