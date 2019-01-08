/** Micheal Metzinger, Edward Pei
  * December 12 2018
  * a class that define any item that you can equipt
  */

package corrosion.entity.item.equippable;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import corrosion.entity.player.Player;
import corrosion.entity.item.Item;
import corrosion.Sprite;


abstract public class Equippable extends Item implements Serializable{
  public transient Sprite sprite;
  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public Equippable(Sprite s){
    this(0,0,0, -1);
    sprite = s;
  }

  public Equippable(int[] state){
    this(0,0,0, -1);
    sprite = new Sprite(null, state, null, null);
  }

  /**
  * Main Constructor
  * @param x the x position of the Equippable
  * @param y the y position of the Equippable
  * @param r the rotation applied to the Equippable
  */
  public Equippable(double x, double y, double r, long id){
    super(x,y,r,id);
  }

  /**
  * Draws the useable equipped to the player
  * @param g the graphics context
  */
  abstract public void drawEquipped(Graphics g, Player player);

  /**
  * Uses the useable
  * @param p the point relative to the player the user clicked
  */
  abstract public void attack(Point p, Player player);

  /**
  * Uses the second function useable
  * @param p the point relative to the player the user clicked
  */
  abstract public void attack2(Point p, Player player);

  /**
  * Reloads the usable
  * @param p the point relative to the player the user clicked
  */
  abstract public void reload();
}
