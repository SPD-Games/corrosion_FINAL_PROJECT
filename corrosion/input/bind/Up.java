/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * Left click from the player
  */
package corrosion.input.bind;

import corrosion.input.bind.Bindable;
import corrosion.entity.player.MainPlayer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Up extends Bindable{
  /**
  * Moves the player up
  * @param e the key event
  */
  public void pressed(KeyEvent e){
    MainPlayer.getMainPlayer().setUp(true);
  }

  /**
  * Stops the player from moving the player left
  * @param e the key event
  */
  public void released(KeyEvent e){
    MainPlayer.getMainPlayer().setUp(false);
  }

  /**
  * method not used in this class
  */
  public void typed(){}
}
