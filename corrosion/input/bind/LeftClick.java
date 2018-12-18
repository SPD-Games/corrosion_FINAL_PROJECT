/** Edward Pei
  * December 4 2018
  * a class which allows the player to attack where they click
  */
package corrosion.input.bind;

import java.awt.*;

import corrosion.input.bind.MouseBindable;
import corrosion.input.Mouse;

import corrosion.entity.player.MainPlayer;
import java.awt.Point;
import corrosion.Drawing;
import corrosion.drawingstate.menuobjects.ButtonG;
import corrosion.drawingstate.*;

public class LeftClick extends MouseBindable{

  /**
  * sees if user clicks the button in a menu
  * @param p the point where the mouse clicked
  */
  public void pressed(Point p){
    int xClicked = (int)(p.getX()) - Drawing.width()/2;
    int yClicked = (int)(p.getY()) - Drawing.height()/2;

    // check if button in connect menu is pressed
    if (xClicked > (ConnectMenuDrawing.getB()).getXBounds()[0] && xClicked < (ConnectMenuDrawing.getB()).getXBounds()[1]) {
      if (yClicked > (ConnectMenuDrawing.getB()).getYBounds()[0] && yClicked < (ConnectMenuDrawing.getB()).getYBounds()[1]) {
        System.out.println("clicked!!!");
        (ConnectMenuDrawing.getB()).setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
      }
    }

  }

  public void released(Point p){}

  public void clicked(Point p){}
}
