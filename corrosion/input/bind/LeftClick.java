/** Edward Pei
  * December 4 2018
  * a class which allows the player to attack where they click
  */
package corrosion.input.bind;

import corrosion.input.bind.MouseBindable;
import corrosion.input.Mouse;

import corrosion.entity.player.MainPlayer;
import java.awt.Point;

public class LeftClick extends MouseBindable{

  /**
  * sees if user clicks the button in a menu
  * @param p the point where the mouse clicked
  */
  public void pressed(Point p){
    System.out.println(p.getX() + " " + p.getY()); // x and y have 0,0 at top left
  }

  public void released(Point p){}

  public void clicked(Point p){}
}
