/** Micheal Metzinger
  * December 13 2018
  * Draws the main game
  */

package corrosion.drawingstate;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import corrosion.Drawing;
import corrosion.entity.*;
import corrosion.entity.player.*;
import corrosion.entity.item.*;
import corrosion.entity.item.equippable.*;
import corrosion.input.*;
import corrosion.input.bind.*;


public class GameDrawing extends DrawingState{

  public static double zoom = 1;
  private static ArrayList<Entity> entities = new ArrayList<Entity>();

  public static void addEntity(Entity e){
      entities.add(e);
  }

  public static void removeEntity(Entity e){
      entities.remove(e);
  }

  /**
  * Initiates the drawing state
  */
  public void init(){
    Drawing.getPanel().getTimer().start();
    Player.init();
    CrossBow.init();
    Arrow.init();

    MouseBindable mouseBinds[] = new MouseBindable[5];
    mouseBinds[1] = new Attack();//left click
    mouseBinds[3] = new Attack2();//right click
    Mouse.setBinds(mouseBinds);

    Bindable binds[] = new Bindable[526];
    binds[87] = new Up();//w
    binds[83] = new Down();//s
    binds[65] = new Left();//a
    binds[68] = new Right();//d
    binds[82] = new Reload();//r
    Keyboard.setBinds(binds);

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
    g.setColor(Color.green);
    g.fillRect(0, 0, Drawing.width(), Drawing.height());

    ((Graphics2D)g).scale(zoom, zoom);
    ((Graphics2D)g).translate(-MainPlayer.getMainPlayer().getXPos() + Drawing.width()/2/zoom, -MainPlayer.getMainPlayer().getYPos() + Drawing.height()/2/zoom);
    //draw map
    g.setColor(Color.black);

    g.fillOval(0, 0, 250, 250);//TMP TO TEST MOVEMENT
    //draw entities
    for (Entity e : entities){
      e.draw(g, t);
    }
    //draw mainplayer
    MainPlayer.getMainPlayer().draw(g, t);

    //draw static menus
    ((Graphics2D)g).setTransform(new AffineTransform());
    g.setColor(Color.black);
    g.drawString(Drawing.getFps()+ "fps. (" + MainPlayer.getMainPlayer().getXPos() + ", " + MainPlayer.getMainPlayer().getYPos() + ")", 50, 50);
  }


}
