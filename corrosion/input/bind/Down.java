/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * moves the player down
  */
package corrosion.input.bind;

import corrosion.input.bind.Bindable;
import corrosion.entity.player.MainPlayer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Down extends Bindable{
  /**
  * Moves the player down
  */
  public void pressed(KeyEvent e){
    MainPlayer.getMainPlayer().setDown(true);
  }

  /**
  * Stops the player from moving the player down
  */
  public void released(KeyEvent e){
    MainPlayer.getMainPlayer().setDown(false);
  }

  /**
  * method not used in this class
  */
  public void typed(){}
}
