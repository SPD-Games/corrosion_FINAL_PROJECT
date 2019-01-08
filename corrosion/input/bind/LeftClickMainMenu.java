/** Edward Pei
  * January 7, 2019
  * a class which allows the player to click on the main menu
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

public class LeftClickMainMenu extends MouseBindable{

  /**
  * sees if user clicks the button in a menu
  * @param p the point where the mouse clicked
  */
  public void pressed(Point p){
    int xClicked = (int)(p.getX()) - Drawing.width()/2;
    int yClicked = (int)(p.getY()) - Drawing.height()/2;

    // check if play button in main menu is pressed
    if (xClicked > (MainMenuDrawing.getPlayBtn()).getXBounds()[0] && xClicked < (MainMenuDrawing.getPlayBtn()).getXBounds()[1]) {
      if (yClicked > (MainMenuDrawing.getPlayBtn()).getYBounds()[0] && yClicked < (MainMenuDrawing.getPlayBtn()).getYBounds()[1]) {
        // show the user the connection menu
        Drawing.setStateConnect();
      }
    }

  }

  public void released(Point p){}

  public void clicked(Point p){}
}
