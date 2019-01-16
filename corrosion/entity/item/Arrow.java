//Edward Pei, Henry Lim
//January 11, 2019
//Bandage class
package corrosion.entity.item;
//imports
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.geom.AffineTransform;
import java.awt.Point;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

import corrosion.Sprite;
import corrosion.entity.Entity;
import corrosion.entity.player.*;

public class Arrow extends Item{
  /**
  * empty method
  * @param player using arrow
  */
  public void attackOff(Player player){}
  //Class variables for sprite
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][];
  private final static int[] LAST_FRAME = {0,1};

  /**
  * Initialize arrow
  */
  public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(Arrow.class.getResourceAsStream("/sprites/arrowIcon.png"));

    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Arrow Sprite: " + e);
      System.exit(-1);
    }
  }
  /**
  * Method to return icon
  * @return icon
  */
  public BufferedImage getIcon(){
    return icon;
  }

  /**
  * Constructor
  */
  public Arrow(){
    super();
    sprite = new Sprite(icon, null, null, null);
  }
  /**
  * Constructor
  * @param size of stack
  */
  public Arrow(int stackSize){
    this();
    this.stackSize = stackSize;
  }
  /**
  * Constuctor
  * @param x position
  * @param y position
  * @param r rotation applied
  * @param id id number associated with Arrow
  */
  public Arrow(double x, double y, double r, long id){
    super(x,y,r,id);
    sprite = new Sprite(icon, null, null, null);
  }
  /**
  * Info Method
  * @return size of stack
  */
  public String getInfo(){
    return  stackSize + "";
  }

  /**
  * Sprite state method
  */
  public void fromServer(){
    sprite = new Sprite(icon, null, null, null);
  }
}
*
