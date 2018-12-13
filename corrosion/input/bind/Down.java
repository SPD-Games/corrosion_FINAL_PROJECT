/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * moves the player down
  */
package corrosion.input.bind;

import corrosion.input.bind.Bindable;
import corrosion.entity.player.MainPlayer;

public class Down extends Bindable{
  /**
  * Moves the player down
  */
  public void pressed(){
    MainPlayer.getMainPlayer().setDown(true);
  }

  /**
  * Stops the player from moving the player down
  */
  public void released(){
    MainPlayer.getMainPlayer().setDown(false);
  }

  public void typed(){}
}
