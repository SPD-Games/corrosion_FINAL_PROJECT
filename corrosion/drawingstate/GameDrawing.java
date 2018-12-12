/** Micheal Metzinger
  * December 11 2018
  * Draws the main game
  */

package corrosion.drawingstate;

import java.awt.*;

import corrosion.Drawing;
import corrosion.entity.player.*;

public class GameDrawing extends DrawingState{

  /**
  * Initiates the drawing state
  */
  public void init(){
    Player.init();

    MainPlayer.spawn(0, 0);
  }

  /**
  * Draws a new frame of game
  * @param g the graphics context
  * @param t the time elapsed from the last frame
  */
  public void draw(Graphics g, long t){
    //clears the screen
    g.clearRect(0, 0, Drawing.width(), Drawing.height());

    //draw map

    //draw entities

    //draw mainplayer
    MainPlayer.getMainPlayer().draw(g, t);

    //draw static menus
  }


}
