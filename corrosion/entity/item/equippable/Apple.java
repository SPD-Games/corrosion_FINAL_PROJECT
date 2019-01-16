//Edward Pei, Henry Lim
//Jan 15, 2019
//Apple class
package corrosion.entity.item.equippable;
//Imports
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

public class Apple extends Equippable{
  //Static variables for apple animation (applies to all instances)
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][];
  private final static int[] LAST_FRAME = {0,3};

  /**
  *Method not in use for Apple class
  *@param player player class
  */
  public void attackOff(Player player){}
  /**
  *Sprite method for apple
  */
  public void fromServer(){
      //Instantiate sprite
      sprite = new Sprite(icon, state, sprites, delay);
  }

  /**
  *Method to initialize apple class
  */
  public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(new File("sprites/apple/icon.png"));
      //loads eating animations
      sprites[0] = new BufferedImage[4];
      for (int i = 1; i <= 4; ++i){
        sprites[0][i-1] = ImageIO.read(new File("sprites/apple/animation/frame" + i + ".png"));
      }
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading apple Sprite: " + e);
      System.exit(-1);
    }
  }
  /**
  * Method to return stack size of apples
  * @return size of stack
  */
  public String getInfo(){
    return stackSize + "";
  }
  /**
  * Method to return icon
  * @return icon
  */
  public BufferedImage getIcon(){
    return icon;
  }
  /**
  *Constuctor
  *@param stack stack number of apples
  */
  public Apple(int stack){
    //Evoke constructor
    this();
    stackSize = stack;
  }
  /**
  *Constuctor
  */
  public Apple(){
    //Evoke constructor
    this(new int[]{0,0});
  }
  /**
  * Constuctor
  * @param state state of sprite
  */
  public Apple(int[] state){
    //Evoke consctructor in Equippable
    super(new Sprite(icon, state, sprites, new int[]{0}));
    //Set boolean to true
    stackable = true;
  }

  /**
  * Constuctor
  * @param x position
  * @param y position
  * @param r rotation applied
  * @param id id number associated with the Equippable
  */
  public Apple(double x, double y, double r, long id){
    //Evoke consctructor in equippable
    super(x,y,r,id);
    //Set boolean to true
    stackable = true;
  }

 /**
 * Draw the item
 * @param g graphics tool used to draw
 * @param player item owner
 */
  public void drawEquipped(Graphics g, Player player){
    transform = player.getTransform();
    //Scale original apple image
    transform.scale(.3,.3);
    //Shift image
    transform.translate(-18, -65);
    //Draw sprite
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }

  /**
  * Atack method, but used as eat animation
  * @param p cursor cooridinate relative to player
  * @param player player using apple
  */
  public void attack(Point p, Player player){
    //Draw apple eating
    int[] frame = sprite.getState();
    //If ready to eat...
    if (frame[0] == LAST_FRAME[0] && frame[1] == LAST_FRAME[1]){
      //Reset state
      sprite.setState(0,0);
      //Remove Apple after use
      ((MainPlayer)player).getInvetory().removeItem(new Apple());
      player.setEquipped(null);
    } else {
      sprite.nextFrame();
    }
    //Replenish player health
    ((MainPlayer)player).hit(-2);
  }

  /**
  * Does nothing, attack2 method not used by apple class
  * @param p
  * @param player
  */
  public void attack2(Point p, Player player){}

  /**
  * Does nothing, you can't reload ammo in apple
  */
  public void reload(){}
}
