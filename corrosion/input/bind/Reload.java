/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * moves the player down
  */
package corrosion.input.bind;

import corrosion.input.bind.Bindable;
import corrosion.entity.player.MainPlayer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Reload extends Bindable{
  /**
  * reloads the mainplayers equipped item
  */
  public void pressed(KeyEvent e){
    MainPlayer.getMainPlayer().reload();
  }


  public void released(KeyEvent e){
  }

  public void typed(){}
}
