//Edward Pei, Henry Lim
//Jan 15, 2019
//Orange class
package corrosion.entity.item.equippable;

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

public class Orange extends Equippable{

  /**
  *Method not in use for orange class
  *@param player player class
  */
  public void attackOff(Player player){}

  /**
  *Sprite method for orange
  */
  public void fromServer(){
    sprite = new Sprite(icon, state, sprites, delay);
  }

  /**
  * Method to return icon
  * @return icon
  */
  public BufferedImage getIcon(){
    return icon;
  }
<<<<<<< HEAD

=======
  /**
  * Method to return stack size of orange
  * @return size of stack
  */
>>>>>>> fbb22c94b877ab6202f191c6cab99a98f709eb63
  public String getInfo(){
    return stackSize + "";
  }

  //Static variables for orange animation (applies to all instances)
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][];
  private final static int[] LAST_FRAME = {0,1};

  /**
  *Method to initialize orange class
  */
  public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(Orange.class.getResourceAsStream("/sprites/orange/icon.png"));

      //loads eating animations
      sprites[0] = new BufferedImage[2];
      for (int i = 1; i <= 2; ++i){
        sprites[0][i-1] = ImageIO.read(Orange.class.getResourceAsStream("/sprites/orange/animation/frame" + i + ".png"));
      }
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading orange Sprite: " + e);
      System.exit(-1);
    }
  }

  /**
  * Constructor
  */
  public Orange(){
    //Evoke constructor
    this(new int[]{0,0});
  }

  /**
  * Constructor
  * @param size of stack
  */
  public Orange(int stack){
    this();
    stackSize = stack;
  }

  /**
  * Constructor
  * @param state of sprite
  */
  public Orange(int[] state){
    //Evoke superconstructor
    super(new Sprite(icon, state, sprites, new int[]{0}));
    stackable = true;
  }

  /**
  * Constuctor
  * @param x position
  * @param y position
  * @param r rotation applied
  * @param id id number associated with the Equippable
  */
  public Orange(double x, double y, double r, long id){
    super(x,y,r,id);
    stackable = true;
  }

  /**
  * Draw the item
  * @param g graphics tool used to draw
  * @param player item owner
  */
  public void drawEquipped(Graphics g, Player player){
    //Draw sprite
    transform = player.getTransform();
    transform.scale(1.2,1.2);
    transform.translate(-18, -70);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }

  /**
  * Atack method, but used as eat animation
  * @param p cursor cooridinate relative to player
  * @param player player using orange
  */
  public void attack(Point p, Player player){
    int[] frame = sprite.getState();
    if (frame[0] == LAST_FRAME[0] && frame[1] == LAST_FRAME[1]){
      ((MainPlayer)player).getInvetory().removeItem(new Orange());
      sprite.setState(0,0);
      player.setEquipped(null);
    } else {
      sprite.nextFrame();
    }
    ((MainPlayer)player).hit(-3);
  }

  /**
  * Method not used by orange class
  * @param p the pointer position on the screen relative to the player
  */
  public void attack2(Point p, Player player){

  }

  /**
  * Does nothing, you cant reload the item
  */
  public void reload(){

  }


}
