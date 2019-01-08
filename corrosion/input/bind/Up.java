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
  */
  public void pressed(KeyEvent e){
    MainPlayer.getMainPlayer().setUp(true);
  }

  /**
  * Stops the player from moving the player left
  */
  public void released(KeyEvent e){
    MainPlayer.getMainPlayer().setUp(false);
  }

  public void typed(){}
}
