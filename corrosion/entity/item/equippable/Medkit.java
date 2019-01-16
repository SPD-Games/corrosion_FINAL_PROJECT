//Edward Pei
//Jan 16, 2019
//Medkit class
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

public class Medkit extends Equippable{
  //Static variables for medkit (applies to all instances)
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][];
  private final static int[] LAST_FRAME = {0,0};

  /**
  *Method not in use for Medkit class
  *@param player player class
  */
  public void attackOff(Player player){}
  /**
  *Sprite method for medkit
  */
  public void fromServer(){
    sprite = new Sprite(icon, new int[]{0,0}, sprites, new int[]{0});
  }

  /**
  * Method to return icon
  * @return icon
  */
  public BufferedImage getIcon(){
    return icon;
  }

  /**
  * Method to return stack size of medkit
  * @return size of stack
  */
  public String getInfo(){
    return stackSize + "";
  }

  /**
  *Method to initialize medkit class
  */
  public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(Medkit.class.getResourceAsStream("/sprites/medkit/icon.png"));

      //loads eating animations
      sprites[0] = new BufferedImage[2];
      for (int i = 1; i <= 1; ++i){
        sprites[0][i-1] = ImageIO.read(Medkit.class.getResourceAsStream("/sprites/medkit/animation/frame" + i + ".png"));
      }
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading medkit Sprite: " + e);
      System.exit(-1);
    }
  }

  /**
  *Constructor
  */
  public Medkit(){
    //Evoke constructor
    this(new int[]{0,0});
  }

  /**
  * Constructor
  */
  public Medkit(int[] state){
    //Evoke constructor in Equipable
    super(new Sprite(icon, state, sprites, new int[]{0}));
    //Set boolean to true
    stackable = true;
  }

  /**
  * Constuctor
  */
  public Medkit(double x, double y, double r, long id){
    //Evoke constructor in Equipable
    super(x,y,r,id);
    //Set boolean to true
    stackable = true;
  }
  /**
  * constructor
  */
  public Medkit(int stack){
    //Evoke constructor
    this();
    //Set stack size
    stackSize = stack;
  }

  /**
  * Draw the item
  * @param g graphics tool used to draw
  * @param player item owner
  */
  public void drawEquipped(Graphics g, Player player){
    transform = player.getTransform();
    //Scale original image
    transform.scale(.8,.8);
    //Shift orignal image
    transform.translate(-37, -167);
    //Draw sprite
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }

  /**
  * Atack method, but used as use animation
  * @param p cursor cooridinate relative to player
  * @param player player using medkit
  */
  public void attack(Point p, Player player){
    int[] frame = sprite.getState();
    //If ready to use...
    if (frame[0] == LAST_FRAME[0] && frame[1] == LAST_FRAME[1]){
      //Remove medkit after use
      ((MainPlayer)player).getInvetory().removeItem(new Medkit());
      player.setEquipped(null);
    } else {
      sprite.nextFrame();
    }
    //Replensih plaer health
    ((MainPlayer)player).hit(-100);
  }

  /**
  * Does nothing, attack2 method not used by medkit class
  * @param p pointer relative to player
  * @param player player using medkit
  */
  public void attack2(Point p, Player player){

  }

  /**
  * Does nothing, you cant reload the item
  */
  public void reload(){

  }


}
