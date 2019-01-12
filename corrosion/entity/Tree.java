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

public class Tree extends Entity{

  // radius of Tree in pixels
  private int rad = 75;

  /**
  * Main Constructor of the Crate
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  */
  public Tree (double x, double y, double r, long id) {
    super(x,y,r,id);
  }
  public Tree () {
    super();
  }
  /**
  * Main Constructor of the Crate
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  * @param scaleSize the size to which the object is scaled to
  */
  public Tree (double x, double y, double r, long id,int rad) {
    super(x,y,r,id);
    this.rad = rad;
  }

  public void draw(Graphics g, long t) {
    g.setColor(Color.GREEN);
    g.fillOval((int)xPos-rad, (int)yPos-rad, 2*rad, 2*rad);
  }

  public Shape getHitBox(){
    return new Ellipse2D.Double(xPos- rad, yPos-rad,2*rad,2*rad);
  }
  /**
  * if the Tree is hit, decrease its size until it is small then return resources
  */
  public void hit(int damage) {
    if(rad > 20) {
      // reduce the size of the Tree
      rad -= 1;
      Item i = new Wood(1);
      MainPlayer.getMainPlayer().getInvetory().addItem(i);
      Protocol.send(8, this, Client.getConnection());
    } else {
      Item i = new Wood(100);
      MainPlayer.getMainPlayer().getInvetory().addItem(i);
      Client.removeEntity(this);
      Protocol.send(12, this, Client.getConnection());
    }
  }

}
