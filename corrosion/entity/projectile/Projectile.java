/** Micheal Metzinger, Edward Pei
  * December 20 2018
  * Class which defines all projectiles in the game such as arrows and bullets on screen
  */
package corrosion.entity.projectile;

import corrosion.entity.Entity;
import corrosion.entity.player.Player;

abstract public class Projectile extends Entity{
  protected double xVel, yVel;
  protected double lastXPos, lastYPos;
  protected double range;
  protected Player player = null;
  protected boolean isHit = false;

  /**
  * projectile Constructor
  */
  public Projectile(double xPos, double yPos, double xVel, double yVel, double r, long id){
    super(xPos,yPos,r,id);
    this.xVel = xVel;
    this.yVel = yVel;
    lastXPos = xPos;
    lastYPos = yPos;
    range = 1000;
  }

  /**
  * projectile Constructor
  */
  public Projectile(){
    super();
    this.xVel = 0;
    this.yVel = 0;
    lastXPos = 0;
    lastYPos = 0;
    range = 1000;
  }

  /**
  * get x velocity
  * @return x velocity
  */
  public double getXVel(){
    return xVel;
  }

  /**
  * get y velocity
  * @return y velocity
  */
  public double getYVel(){
    return yVel;
  }

  /**
  * Determines if the object has hit anything
  */
  abstract public void hitCheck();

  /**
  * When an object is hit
  */
  abstract public void hit();

  /**
  * update position
  */
  abstract public void update(long t);
}
