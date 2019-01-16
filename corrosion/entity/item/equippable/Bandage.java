//Edward Pei, Henry Lim
//Jan 15, 2019
//Bandage class
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

public class Bandage extends Equippable{

  //Static variables for bandage animation (applies to all instances)
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][];
  private final static int[] LAST_FRAME = {0,0};

  /**
  * Method not in use for Bandage class
  * @param player player class
  */
  public void attackOff(Player player){}

  /**
  *Sprite animation method for bandage
  */
  public void fromServer(){
    sprite = new Sprite(icon, new int[]{0,0}, sprites, new int[]{0});
  }
  /**
  * Return icon
  */
  public BufferedImage getIcon(){
    return icon;
  }

  /**
  * Return bandage stack size
  */
  public String getInfo(){
    return stackSize + "";
  }

  /**
  *Method to initialize bandage class
  */
  public static void init(){
    try{
      //loads icon

      icon = ImageIO.read(new File("sprites/bandage/icon.png"));

      //loads eating animations
      sprites[0] = new BufferedImage[2];
      for (int i = 1; i <= 1; ++i){
        sprites[0][i-1] = ImageIO.read(new File("sprites/bandage/animation/frame" + i + ".png"));
      }
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading bandage Sprite: " + e);
      System.exit(-1);
    }
  }
  /**
  * Constructor
  * @param stack size
  */
  public Bandage(int stack){
    //evoke constructor
    this();
    //Set stack size
    stackSize = stack;
  }
  /**
  * Constructor
  */
  public Bandage(){
    //evoke constructor
    this(new int[]{0,0});
  }
  /**
  * Constructor
  * @param state state of sprite
  */
  public Bandage(int[] state){
    //Evoke consctructor in equippable
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
  public Bandage(double x, double y, double r, long id){
    super(x,y,r,id);
    stackable = true;
  }

  /**
  * Draw the item
  * @param g graphics tool used to draw
  * @param player item owner
  */
  public void drawEquipped(Graphics g, Player player){
    transform = player.getTransform();
    //Scale original image
    transform.scale(1.1,1.1);
    //Shift original image
    transform.translate(-45, -150);
    //Draw sprite
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }

  /**
  * Atack method, but used as animation
  * @param p cursor cooridinate relative to player
  * @param player player using bandage
  */
  public void attack(Point p, Player player){
    //Draw bandage used
    int[] frame = sprite.getState();
    if (frame[0] == LAST_FRAME[0] && frame[1] == LAST_FRAME[1]){
      //Remove bandage after use
      ((MainPlayer)player).getInvetory().removeItem(new Bandage());
      player.setEquipped(null);
    } else {
      sprite.nextFrame();
    }
    //Replenish player health
    ((MainPlayer)player).hit(-25);
  }

  /**
  * Unused method (bandage class cannot attack)
  * @param p the pointer position on the screen relative to the player
  * @param player using bandage
  */
  public void attack2(Point p, Player player){}

  /**
  * Does nothing, you cant reload the item
  */
  public void reload(){}
}
