/*
* Michael Metzinger
* Dec 13 2018
* Projectile interface
*/

/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * Class which defines all projectiles in the game such as arrows and bullets on screen
  */
package corrosion;

import corrosion.entity.Entity;
import corrosion.entity.player.Player;

public interface Projectile{
  /**
  * Determines if the object has hit anything
  * @return if the projectile hit an object
  */
  public boolean hitCheck();

  /**
  * When an object is hit
  */
  public void hit();
}
