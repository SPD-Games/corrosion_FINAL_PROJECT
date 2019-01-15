/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * a class which allows the player to attack where they click
  */
package corrosion.input.bind;

import corrosion.input.bind.MouseBindable;
import corrosion.input.Mouse;

import corrosion.entity.player.MainPlayer;
import java.awt.Point;

public class Attack extends MouseBindable{

  /**
  * Uses the main players equipped item
  * @param p the point where the mouse clicked relative to the player
  */
  public void pressed(Point p){
    MainPlayer.getMainPlayer().attack(Mouse.relativeToPlayer(p));
  }

  /**
  * stops the attack of the main player
  * @param p the point where the mouse clicked relative to the player
  */
  public void released(Point p){
    MainPlayer.getMainPlayer().attackOff();
  }

  /**
  * not used in this bind...
  * @param p the point where the mouse clicked relative to the player
  */
  public void clicked(Point p){}
}
