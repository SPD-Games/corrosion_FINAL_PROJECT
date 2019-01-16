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
import corrosion.entity.item.equippable.*;
import corrosion.entity.item.*;

public class Tree extends Entity{

  // radius of Tree in pixels
  private int rad = 75;

  /**
  * Main Constructor of the tree
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  */
  public Tree (double x, double y, double r, long id) {
    super(x,y,r,id);
  }
  /**
  * Main Constructor of the Tree
  */
  public Tree () {
    super();
  }
  /**
  * Main Constructor of the Tree
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  * @param scaleSize the size to which the object is scaled to
  */
  public Tree (double x, double y, double r, long id,int rad) {
    super(x,y,r,id);
    this.rad = rad;
  }

  /**
  * Draws the player to the Window
  * @param g the graphics context
  * @param t time since last frame
  */
  public void draw(Graphics g, long t) {
    g.setColor(new Color(45,87,44));
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
  * if the Tree is hit, decrease its size until it is small then return resources
  */
  public void hit(int damage) {
    if(rad > 20) {
      // reduce the size of the Tree
      rad -= 5;
      Item i = new Wood(5);
      // give the chance for the tree to drop apples and oranges when hit
      if(Math.random() < 0.02) {
        MainPlayer.getMainPlayer().getInvetory().addItem(new Apple());
      } else if (Math.random() > 0.97) {
        MainPlayer.getMainPlayer().getInvetory().addItem(new Orange());
      }
      MainPlayer.getMainPlayer().getInvetory().addItem(i);
      Protocol.send(8, this, Client.getConnection());
    } else {
      //drop 200 wood when the tree is broken
      Item i = new Wood(200);
      MainPlayer.getMainPlayer().getInvetory().addItem(i);
      Client.removeEntity(this);
      Protocol.send(12, this, Client.getConnection());
    }
  }

}
