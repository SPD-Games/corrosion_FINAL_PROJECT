/** Edward Pei
  * January 7 2019
  * class for crates that drop loot
  */
package corrosion.entity;


public class Crate extends Entity{

  double scaleSize = 0.5;

  public static void init(){
    try{
      //loads Crate icon
      icon = ImageIO.read(new File("sprites/crate.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Crate Sprite: " + e);
      System.exit(-1);
    }
  }

  /**
  * Main Constructor of the Crate
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  */
  public Crate (double x, double y, double r, long id) {
    super(x,y,r,id);
  }

  /**
  * Main Constructor of the Crate
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  * @param scaleSize the size to which the object is scaled to
  */
  public Crate (double x, double y, double r, long id, double scaleSize) {
    super(x,y,r,id);
    this.scaleSize = scaleSize;
  }

  public draw() {


  }

  /**
  * if the Crate is hit, decrease its size until it is small then return resources
  */
  public hit() {

    if(scaleSize > 0.2) {
      // reduce the size of the crate
      scaleSize -= 0.05;
    } else {
      //TODO: ADD SOME SORT OF LOOT DROP WHEN CRATE IS HIT A BUNCH
    }
  }

}
