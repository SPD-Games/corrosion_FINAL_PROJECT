/** Edward Pei
  * January 7 2019
  * class for rocks that drop sulfer, metal, and stone
  */
package corrosion.entity;
import corrosion.entity.item.*;
import corrosion.Sprite;
import corrosion.entity.player.MainPlayer;
import java.awt.*;
import java.awt.geom.*;
import corrosion.entity.*;
import corrosion.network.*;
import corrosion.network.protocol.*;

public class Rock extends Entity{

  // radius of rock in pixels
  private int rad = 75;

  /**
  * Main Constructor of the rock
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  */
  public Rock (double x, double y, double r, long id) {
    super(x,y,r,id);
  }
  /**
  * Constructor of the rock
  */
  public Rock () {
    super();
  }
  /**
  *  Constructor of the rock
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  * @param scaleSize the size to which the object is scaled to
  */
  public Rock (double x, double y, double r, long id,int rad) {
    super(x,y,r,id);
    this.rad = rad;
  }

  /**
  * Draws the player to the Window
  * @param g the graphics context
  * @param t time since last frame
  */
  public void draw(Graphics g, long t) {
    g.setColor(new Color(149, 148, 139));
    g.fillOval((int)xPos-rad, (int)yPos-rad, 2*rad, 2*rad);
  }

  /**
  * get the hit box
  * @return the hit box
  */
  public Shape getHitBox(){
    return new Ellipse2D.Double(xPos- rad, yPos-rad,2*rad,2*rad);
  }

  /**
  * if the Rock is hit, decrease its size until it is small then return resources
  */
  public void hit(int damage) {
    if(rad > 20) {
      // reduce the size of the rock
      rad -= 5;
      Item i = new Stone(5);
      MainPlayer.getMainPlayer().getInvetory().addItem(i);
      Protocol.send(8, this, Client.getConnection());
    } else {
      Item i = new Stone(200);
      MainPlayer.getMainPlayer().getInvetory().addItem(i);
      Client.removeEntity(this);
      Protocol.send(12, this, Client.getConnection());
    }
  }

}
