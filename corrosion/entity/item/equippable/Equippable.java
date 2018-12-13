/** Micheal Metzinger, Edward Pei
  * December 12 2018
  * a class that define any item that you can equipt
  */

package corrosion.entity.item.equippable;

import java.awt.Graphics;
import java.awt.Point;

import corrosion.entity.player.Player;
import corrosion.entity.item.Item;

abstract public class Equippable extends Item{
  protected Player player;

  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public Equippable(Player p){
    this(0,0,0);
    player = p;
  }

  /**
  * Main Constructor
  * @param x the x position of the Equippable
  * @param y the y position of the Equippable
  * @param r the rotation applied to the Equippable
  */
  public Equippable(double x, double y, double r){
    super(x,y,r);
  }

  /**
  * Draws the useable equipped to the player
  * @param g the graphics context
  */
  abstract public void drawEquipped(Graphics g);

  /**
  * Uses the useable
  * @param p the point relative to the player the user clicked
  */
  abstract public void attack(Point p);

  /**
  * Uses the second function useable
  * @param p the point relative to the player the user clicked
  */
  abstract public void attack2(Point p);

  /**
  * Reloads the usable
  * @param p the point relative to the player the user clicked
  */
  abstract public void reload();
}
