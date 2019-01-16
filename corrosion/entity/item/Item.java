/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * a class that defines any thing you can pick up and put in your inventory
  */
package corrosion.entity.item;
//imports
import java.awt.image.BufferedImage;
import corrosion.entity.Entity;
import java.io.Serializable;
import corrosion.Sprite;
import corrosion.network.Client;
import corrosion.network.protocol.Protocol;
import java.awt.*;
import java.awt.geom.*;

abstract public class Item extends Entity implements Serializable{
  //Item varaibles
  public transient Sprite sprite;
  protected boolean stackable = true;
  protected int stackSize = 1;
  protected int[] delay;
  protected int[] state;
  /**
  * Sprite method for all items
  */
  abstract public void fromServer();

  /**
  * server to sprite communication method
  */
  public void sendItem(){
    if (sprite != null){
      state = sprite.getState();
      delay = sprite.getDelay();
    } else {
      state = null;
      delay = null;
    }
    Protocol.send(8,this,Client.getConnection());
  }

  /**
  * Main Constructor
  * @param x the x position of the Item
  * @param y the y position of the Item
  * @param r the rotation applied to the Item
  * @param id id number assoicated with item
  */
  public Item(double x, double y, double r, long id){
    super(x,y,r,id);
  }

  /**
  * Main Constructor
  */
  public Item(){
    super();
  }

  /**
  *get icon method
  */
  abstract public BufferedImage getIcon();
  /**
  * Check stackbility method
  * @return boolean of stack
  */
  public boolean isStackable(){
    return stackable;
  }
  /**
  * Method to add to stack
  * @param i
  */
  }
  public void addStack(Item i){
    stackSize += i.getStackSize();
  }
  /**
  * Method to return size of stack
  * @return size of stack
  */
  public int getStackSize(){
    return stackSize;
  }
  /**
  *info method
  */
  abstract public String getInfo();
  /**
  * draw sprite method
  * @param g graphics tool used to draw
  */
  public void draw(Graphics g, long t){
    BufferedImage i = sprite.getIcon();
    transform.setToTranslation(xPos -50, yPos -50);
    transform.scale(100.0/i.getWidth(),100.0/i.getHeight());
    ((Graphics2D)(g)).drawImage(i, transform, null);
  }
  /**
  * method to change size of stack
  * @param i  item
  */
  public void removeStack(Item i){
    int otherStackSize = i.getStackSize();
    if (otherStackSize <= stackSize){
      int tmp = stackSize;
      stackSize -= otherStackSize;
      i.stackSize -= tmp;
      return;
    } else {
      i.removeStack(this);
    }
  }

  /**
  * hit box method
  */
  public Shape getPickUpHitBox(){
    return new Rectangle2D.Double(xPos - 50, yPos - 50, 100, 100);
  }
}
