/** Micheal Metzinger
  * December 11 2018
  * Draws the main game
  */

package corrosion.drawingstate;

import java.awt.*;

import corrosion.Drawing;
import corrosion.entity.player.*;
import corrosion.entity.item.equippable.*;
import corrosion.input.*;
public class GameDrawing extends DrawingState{

  public static double zoom = 0.5;

  /**
  * Initiates the drawing state
  */
  public void init(){
    Player.init();
    CrossBow.init();

    Mouse.init();
    Keyboard.init();
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
    ((Graphics2D)g).scale(zoom, zoom);
    ((Graphics2D)g).translate(MainPlayer.getMainPlayer().getXPos() + Drawing.width()/2/zoom, MainPlayer.getMainPlayer().getYPos() + Drawing.height()/2/zoom);
    //draw map
    g.fillOval(0, 0, 250, 250);//TMP TO TEST MOVEMENT
    //draw entities

    //draw mainplayer
    MainPlayer.getMainPlayer().draw(g, t);

    //draw static menus
  }


}
