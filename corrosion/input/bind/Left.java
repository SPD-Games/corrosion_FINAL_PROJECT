/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * moves the player left
  */
package corrosion.input.bind;

import corrosion.input.bind.Bindable;
import corrosion.entity.player.MainPlayer;

public class Left extends Bindable{
  /**
  * Moves the player left
  */
  public void pressed(){
    MainPlayer.getMainPlayer().setLeft(true);
  }

  /**
  * Stops the player from moving the player left
  */
  public void released(){
    MainPlayer.getMainPlayer().setLeft(false);
  }

  public void typed(){}
}
