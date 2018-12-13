/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * moves the player down
  */
package corrosion.input.bind;

import corrosion.input.bind.Bindable;
import corrosion.entity.player.MainPlayer;

public class Reload extends Bindable{
  /**
  * reloads the mainplayers equipped item
  */
  public void pressed(){
    MainPlayer.getMainPlayer().reload();
  }


  public void released(){
  }

  public void typed(){}
}
