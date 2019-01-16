//Michael Metzinger, Edward Pei, Henry Lim
//Jan. 16th, 2018
//wood class
package corrosion.entity.item;

//Imports
import corrosion.entity.item.*;
import corrosion.Sprite;
import corrosion.entity.player.MainPlayer;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Wood extends Item{
  /**
  * Initialization method
  */
  public static void init(){
    BufferedImage icon = null;
    try{
      //loads Barrel icon
      icon = ImageIO.read(new File("sprites/wood.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Wood Sprite: " + e);
      System.exit(-1);
    }
    sprites = new Sprite(icon,null,null,null);
  }
    //Class variable
  public static Sprite sprites;
  /**
  * Main Constructor
  * @param x the x position of the Item
  * @param y the y position of the Item
  * @param r the rotation applied to the Item
  * @param id id number associated with
  */
  public Wood(double x, double y, double r, long id){
    super(x,y,r,id);
    sprite = sprites;
  }

  /**
  * Main Constructor
  */
  public Wood(){
    super();
    sprite = sprites;
  }

  /**
  * Main Constructor
  * @param stackSize the amount of Wood in the stack
  */
  public Wood(int stackSize){
    this();
    this.stackSize = stackSize;
  }
  /**
  * return info method
  * @return size of stack
  */
  public String getInfo(){
    return stackSize + "";
  }
  /**
  *Sprite state method
  */
  public void fromServer(){
    sprite = sprites;
  }
  /**
  * return icon method
  * @return icon
  */
  public BufferedImage getIcon(){
    return sprite.getIcon();
  }
}
