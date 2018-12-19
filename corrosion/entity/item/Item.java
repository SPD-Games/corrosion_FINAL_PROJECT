/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * a class that defines any thing you can pick up and put in your inventory
  */
package corrosion.entity.item;

import corrosion.entity.Entity;

abstract public class Item extends Entity{
  /**
  * Main Constructor
  * @param x the x position of the Item
  * @param y the y position of the Item
  * @param r the rotation applied to the Item
  */
  public Item(double x, double y, double r, long id){
    super(x,y,r,id);
  }

  /**
  * Main Constructor
  * @param x the x position of the Item
  * @param y the y position of the Item
  * @param r the rotation applied to the Item
  */
  public Item(){
    super();
  }
}
