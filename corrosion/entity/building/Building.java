/**
* Michael Metzinger
* Jan 13 2019
* A Building that can be placed
*/

package corrosion.entity.building;

import corrosion.entity.Entity;
import corrosion.entity.player.*;
import corrosion.network.*;
import corrosion.network.protocol.*;
import java.awt.Graphics;
import java.awt.Shape;

public abstract class Building extends Entity {
  protected static final int TWIG = 0;
  protected static final int WOOD = 1;
  protected static final int STONE = 2;
  protected static final int METAL = 3;
  protected double hp = 26;
  protected double maxHp;
  protected boolean placed;
  protected int state = 0;

  /**
  * Blank Constructor
  */
  public Building(){
    super();
  }

  /**
  * Main Constructor
  * @param xPos the x position of the Building
  * @param yPos the y position of the Building
  * @param rotation the rotation of the Building
  */
  public Building(double xPos, double yPos, double rotation){
    super(xPos, yPos, rotation, -1);
  }

  /**
  * Draws the building
  * @param g the graphics context to Use
  * @param t the time since last frame
  */
  public abstract void draw(Graphics g, long t);

  /**
  * Draws the preview of the Building about to be placed
  * @param g the graphics context to Use
  * @param p the player that is placing the Building
  */
  public abstract void drawPreview(Graphics g, Player p);

  /**
  * Gets the hitbox of the Building
  * @return the hitbox of the Building
  */
  @Override
  public abstract Shape getHitBox();

  /**
  * Gets the building hit box of the Building
  * @return the building hit box of the Building
  */
  public abstract Shape getBuildingHitBox();

  /**
  * Upgrades the Building
  * @param level the level to upgrade the Building to
  */
  public abstract void upgrade(int level);

  /**
  * Places the Building
  * @return if the placement was succsesful
  */
  public abstract boolean place();

  /**
  * Damages the building after its been hit
  * @param damage the damage to deal to the building
  */
  @Override
  public void hit(int damage){
    //damage the building
    hp -= damage;
    //check if the building should be destroyed
    if (hp <= 0){
      //if so remove the building
      Client.removeEntity(this);
      Protocol.send(12,this,Client.getConnection());
      return;
    }
    //send the server the updated building
    Protocol.send(8, this, Client.getConnection());
  }
}
