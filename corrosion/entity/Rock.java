/** Edward Pei
  * January 7 2019
  * class for rocks that drop sulfer, metal, and stone
  */
package corrosion.entity;


public class Rock extends Entity{

  // radius of rock in pixels
  int rad = 150;

  /**
  * Main Constructor of the Crate
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  */
  public Rock (double x, double y, double r, long id) {
    super(x,y,r,id);
  }

  /**
  * Main Constructor of the Crate
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  * @param scaleSize the size to which the object is scaled to
  */
  public Rock (double x, double y, double r, long id,int rad) {
    super(x,y,r,id);
    this.rad = rad;
  }

  public draw() {


  }

  /**
  * if the Rock is hit, decrease its size until it is small then return resources
  */
  public hit() {

    if(rad > 75) {
      // reduce the size of the rock
      scaleSize -= 15;
    } else {
      //TODO: ADD SOME SORT OF LOOT DROP WHEN CRATE IS HIT A BUNCH
    }
  }

}
