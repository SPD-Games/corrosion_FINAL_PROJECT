/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * moves the player left
  */
package corrosion.input.bind;

import corrosion.input.bind.Bindable;
import corrosion.entity.player.MainPlayer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Left extends Bindable{
  /**
  * Moves the player left
  * @param e the key event
  */
  public void pressed(KeyEvent e){
    MainPlayer.getMainPlayer().setLeft(true);
  }

  /**
  * Stops the player from moving the player left
  * @param e the key event
  */
  public void released(KeyEvent e){
    MainPlayer.getMainPlayer().setLeft(false);
  }

  /**
  * method not used in this class
  */
  public void typed(){}
}
