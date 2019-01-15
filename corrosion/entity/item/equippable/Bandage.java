//Edward Pei, Henry Lim
//Dec 29, 2018
//Apple class
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
  * 
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
  public Bandage(int stack){
    this();
    stackSize = stack;
  }
  public Bandage(){
    this(new int[]{0,0});
  }

  public Bandage(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{0}));
    stackable = true;
  }

  public Bandage(double x, double y, double r, long id){
    super(x,y,r,id);
    stackable = true;
  }

  public void drawEquipped(Graphics g, Player player){
    transform = player.getTransform();
    transform.scale(1.1,1.1);
    transform.translate(-45, -150);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }


  public void attack(Point p, Player player){
    int[] frame = sprite.getState();
    if (frame[0] == LAST_FRAME[0] && frame[1] == LAST_FRAME[1]){
      ((MainPlayer)player).getInvetory().removeItem(new Bandage());
      player.setEquipped(null);
    } else {
      sprite.nextFrame();
    }
    ((MainPlayer)player).hit(-25);
  }

  /**
  * Reloads the weapon
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
