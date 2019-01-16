// Micheal Metzinger, Edward Pei
//Jan. 15th, 2018
//abstract class that define any item that you can equipt

package corrosion.entity.item.equippable;
//Imports
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import corrosion.entity.player.Player;
import corrosion.entity.item.Item;
import corrosion.Sprite;


abstract public class Equippable extends Item implements Serializable{
  /**
   * Main Constructor
   * @param s the player that has the equipable equipped
  */
  public Equippable(Sprite s){
    //Evoke constructor
    this(0,0,0, -1);
    sprite = s;
  }
  /**
   * Constructor
   * @param state state of Equippable
  */
  public Equippable(int[] state){
    //Evoke constructor
    this(0,0,0, -1);
    //Insantiate
    sprite = new Sprite(null, state, null, null);
  }

  /**
  * Main Constructor
  * @param x the x position of the Equippable
  * @param y the y position of the Equippable
  * @param r the rotation applied to the Equippable
  * @param id id number associated with the Equippable
  */
  public Equippable(double x, double y, double r, long id){
    super(x,y,r,id);
    stackable = false;
  }

  /**
  * Draws the useable equipped to the player
  * @param g the graphics context
  * @param player using equippable
  */
  abstract public void drawEquipped(Graphics g, Player player);

  /**
  * Uses the useable
  * @param p the point relative to the player the user clicked
  * @param player using equippable
  */
  abstract public void attack(Point p, Player player);

  /**
  * Uses the useable
  * @param player the player that stopped attacking
  */
  abstract public void attackOff(Player player);

  /**
  * Uses the second function useable
  * @param p the point relative to the player the user clicked
  * @param player using equippable
  */
  abstract public void attack2(Point p, Player player);

  /**
  * Reloads the usable
  */
  abstract public void reload();
}
