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
  protected Player player = null;

  public Projectile(double xPos, double yPos, double xVel, double yVel, double r, long id){
    super(xPos,yPos,r,id);
    this.xVel = xVel;
    this.yVel = yVel;
    lastXPos = xPos;
    lastYPos = yPos;
    range = 1000;
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
  */
  abstract public void hitCheck();

  /**
  * When an object is hit
  */
  abstract public void hit();
  abstract public void update(long t);
}
