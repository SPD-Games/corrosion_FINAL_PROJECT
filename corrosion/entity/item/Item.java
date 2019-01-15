/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * a class that defines any thing you can pick up and put in your inventory
  */
package corrosion.entity.item;

import java.awt.image.BufferedImage;
import corrosion.entity.Entity;
import java.io.Serializable;
import corrosion.Sprite;
import corrosion.network.Client;
import corrosion.network.protocol.Protocol;
import java.awt.*;
import java.awt.geom.*;

abstract public class Item extends Entity implements Serializable{
  public transient Sprite sprite;
  protected boolean stackable = true;
  protected int stackSize = 1;
  protected int[] delay;
  protected int[] state;
  abstract public void fromServer();
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

  abstract public String getInfo();

  public void draw(Graphics g, long t){
    BufferedImage i = sprite.getIcon();
    transform.setToTranslation(xPos -50, yPos -50);
    transform.scale(100.0/i.getWidth(),100.0/i.getHeight());
    ((Graphics2D)(g)).drawImage(i, transform, null);
  }

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

  public Shape getPickUpHitBox(){
    return new Rectangle2D.Double(xPos - 50, yPos - 50, 100, 100);
  }
}
