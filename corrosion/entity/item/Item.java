/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * a class that defines any thing you can pick up and put in your inventory
  */
package corrosion.entity.item;

import java.awt.image.BufferedImage;
import corrosion.entity.Entity;
import java.io.Serializable;

abstract public class Item extends Entity implements Serializable{
  protected boolean stackable = true;
  protected int stackSize = 1;
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

  abstract public BufferedImage getIcon();

  public boolean isStackable(){
    return stackable;
  }

  public void addStack(Item i){
    stackSize += i.getStackSize();
  }

  public int getStackSize(){
    return stackSize;
  }

  public String getInfo(){
      return getStackSize() + "";
  }

  public void removeStack(Item i){
    int otherStackSize = i.getStackSize();
    if (otherStackSize <= stackSize){
      stackSize -= otherStackSize;
      return;
    } else {
      i.removeStack(this);
    }
  }
}
