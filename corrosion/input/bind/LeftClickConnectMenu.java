/** Edward Pei
  * January 7, 2019
  * a class which allows the player to click on the connect menu
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

public class LeftClickConnectMenu extends MouseBindable{

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
        // begin to draw the game and connect with the IP user inputted
        Drawing.setStateGame(ConnectMenuDrawing.getIP(), 1234);
      }
    }

  }

  /**
  * method not used in this class
  */
  public void released(Point p){}

  /**
  * method not used in this class
  */
  public void clicked(Point p){}
}
