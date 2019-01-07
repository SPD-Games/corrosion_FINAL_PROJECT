/** Edward Pei
  * January 7 2019
  * class for tree that drop wood
  */
package corrosion.entity;


public class Tree extends Entity{

  // radius of Tree in pixels
  int rad = 325;

  /**
  * Main Constructor of the Crate
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  */
  public Tree (double x, double y, double r, long id) {
    super(x,y,r,id);
  }

  /**
  * Main Constructor of the Crate
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  * @param scaleSize the size to which the object is scaled to
  */
  public Tree (double x, double y, double r, long id,int rad) {
    super(x,y,r,id);
    this.rad = rad;
  }

  public draw() {


  }

  /**
  * if the Tree is hit, decrease its size until it is small then return resources
  */
  public hit() {

    if(rad > 120) {
      // reduce the size of the Tree
      scaleSize -= 30;
    } else {
      //TODO: ADD SOME SORT OF LOOT DROP WHEN CRATE IS HIT A BUNCH
    }
  }

}
