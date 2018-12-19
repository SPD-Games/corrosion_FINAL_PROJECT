/*
* Michael Metzinger
* Dec 13 2018
* Projectile interface
*/

/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * Class which defines all projectiles in the game such as arrows and bullets on screen
  */
package corrosion.entity.projectile;

import corrosion.entity.Entity;
import corrosion.entity.player.Player;

abstract public class Projectile extends Entity{
  protected double xVel, yVel;
  protected double lastXPos, lastYPos;
  protected double range;
  protected boolean watchHit = false;
  protected Player player;

  public Projectile(double xPos, double yPos, double xVel, double yVel, double r, long id){
    super(xPos,yPos,r,id);
    this.xVel = xVel;
    this.yVel = yVel;
    lastXPos = xPos;
    lastYPos = yPos;
    range = 1000;
  }

  /**
  * Main Constructor
  * @param player the player who created the Arrow
  * @param mouseX the x position of the mouse cursor relative to the player
  * @param mouseY the y position of the mouse cursor relative to the player
  */
  public Projectile(Player player, double mouseX, double mouseY, double MAX_VEL, double MAX_RANGE){
    this();
    this.player = player;
    this.rotation = Math.atan2(mouseX, mouseY);
    this.xPos = player.getXPos() + 189 * Math.sin(rotation);
    this.yPos = player.getYPos() - 189 * Math.cos(rotation);
    this.lastXPos = xPos;
    this.lastYPos = yPos;
    this.xVel = MAX_VEL*Math.sin(rotation);
    this.yVel = MAX_VEL*-Math.cos(rotation);
    range = MAX_RANGE;
  }

  public Projectile(){
    super();
    this.xVel = 0;
    this.yVel = 0;
    lastXPos = 0;
    lastYPos = 0;
    range = 1000;
  }
  /**
  * Determines if the object has hit anything
  * @return if the projectile hit an object
  */
  abstract public Entity hitCheck();

  /**
  * When an object is hit
  */
  abstract public void hit();
  abstract public void update(long t);
}
