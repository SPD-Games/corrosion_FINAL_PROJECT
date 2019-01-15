/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * right click from the player
  */
package corrosion.input.bind;

import corrosion.input.bind.Bindable;
import corrosion.entity.player.MainPlayer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Right extends Bindable{
  /**
  * Moves the player right
  * @param e the key event
  */
  public void pressed(KeyEvent e){
    MainPlayer.getMainPlayer().setRight(true);

  }
  /**
  * Stops the player from moving the player right
  * @param e the key event
  */
  public void released(KeyEvent e){
    MainPlayer.getMainPlayer().setRight(false);
  }

  /**
  * method not used in this class
  */
  public void typed(){}
}
