/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * abstract class which helps with key binds
  */
package corrosion.input.bind;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import corrosion.entity.player.MainPlayer;
public class DropEquipped extends Bindable{
  public void pressed(KeyEvent e){
    MainPlayer.getMainPlayer().getInvetory().dropEquipped();
  }
  public void released(KeyEvent e){}
  public void typed(){}
}
