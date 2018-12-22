/** Micheal Metzinger
  * December 13 2018
  * Draws the main game
  */

package corrosion.drawingstate;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import corrosion.network.*;
import corrosion.Drawing;
import corrosion.entity.*;
import corrosion.entity.player.*;
import corrosion.entity.item.*;
import corrosion.entity.projectile.*;
import corrosion.entity.item.equippable.*;
import corrosion.input.*;
import corrosion.input.bind.*;


public class GameDrawing extends DrawingState{

  public static double zoom = 1;

  /**
  * Initiates the drawing state
  */
  public void init(){
    Drawing.getPanel().getTimer().start();
    Player.init();
    CrossBow.init();
    Pistol.init();
    ArrowProjectile.init();

    setBinds();
  }

  public void setBinds(){
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
  }

  public GameDrawing(String ip, int port){
    super();
    new Client(ip, port);
  }

  /**
  * Draws a new frame of game
  * @param g the graphics context
  * @param t the time elapsed from the last frame
  */
  public void draw(Graphics g, long t){
    Graphics2D g2d = (Graphics2D)g;

    //setting to change render quality
    //low settings
    //g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

    //high settings
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    //clears the screen
    g.clearRect(0, 0, Drawing.width(), Drawing.height());
    g.setColor(new Color(173, 216, 230));
    g.fillRect(0, 0, Drawing.width(), Drawing.height());

    g2d.scale(zoom, zoom);
    g2d.translate(-MainPlayer.getMainPlayer().getXPos() + Drawing.width()/2/zoom, -MainPlayer.getMainPlayer().getYPos() + Drawing.height()/2/zoom);
    //draw map
    g.setColor(Color.black);

    g.fillOval(0, 0, 250, 250);//TMP TO TEST MOVEMENT
    //draw entities
    ArrayList<Entity> entities = Client.getEntities();
    for (int i = 0; i < entities.size(); ++i){
      entities.get(i).draw(g, t);
    }

    //draw players
    ArrayList<Player> players = Client.getPlayers();
    for (int i = 0; i < players.size(); ++i){
      players.get(i).draw(g, t);
    }
    MainPlayer.getMainPlayer().draw(g, t);

    //draw static menus
    ((Graphics2D)g).setTransform(new AffineTransform());
    g.setColor(Color.black);
    g.drawLine(0, Drawing.height()/2,Drawing.width(),Drawing.height()/2);
    g.drawLine(Drawing.width()/2, 0, Drawing.width()/2,Drawing.height());
    g.drawString(Drawing.getFps()+ "fps. " + Client.getPing() + "ms. Pos(" + MainPlayer.getMainPlayer().getXPos() + ", " + MainPlayer.getMainPlayer().getYPos() + ")", 50, 50);

    Client.updateReady();
  }


}
