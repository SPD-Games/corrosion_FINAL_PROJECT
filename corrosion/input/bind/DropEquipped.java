/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * abstract class which helps with key binds
  */
package corrosion.input.bind;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import corrosion.entity.player.MainPlayer;
public class DropEquipped extends Bindable{

  /**
  * if right key is pressed drop selected item drop the equipped item
  * @param e a key event
  */
  public void pressed(KeyEvent e){
    MainPlayer.getMainPlayer().getInvetory().dropEquipped();
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
