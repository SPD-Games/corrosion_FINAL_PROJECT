/** Micheal Metzinger
  * December 11 2018
  * Draws the main game
  */

package corrosion.drawingstate;

import java.awt.*;

import corrosion.Drawing;

public class GameDrawing extends DrawingState{

  /**
  * Initiates the drawing state
  */
  public void init(){

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

    //draw player

    //draw static menus
  }


}
