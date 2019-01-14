/** Micheal Metzinger, Edward Pei
  * December 13 2018
  * gets the image for the crossbow, and draws it and allows it to shoot
  */

package corrosion.entity.item.equippable;

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

import corrosion.entity.projectile.*;
import corrosion.Sprite;
import corrosion.entity.Entity;
import corrosion.entity.player.*;
import corrosion.entity.item.Arrow;

public class CrossBow extends Equippable{
  public void attackOff(Player player){}
  public void fromServer(){
    sprite = new Sprite(icon, state, sprites, delay);
  }
  //TODO move all images and draw handling in Usable
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[2][];;
  private final int[] SHOOT_READY = {0,3};
  private final int[] RELOAD_READY = {1,2};

  public BufferedImage getIcon(){
    return icon;
  }

  public String getInfo(){
    if (sprite.isState(SHOOT_READY, false)){
      return "1";
    }
    return "0";
  }

  /**
  * Initializes the player class
  */
  public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(new File("sprites/crossbow/icon.png"));
      //loads relaod animations
      sprites[0] = new BufferedImage[4];
      for (int i = 1; i <= 4; ++i){
        sprites[0][i-1] = ImageIO.read(new File("sprites/crossbow/animation/frame" + i + ".png"));
      }

      //loads shooting animations
      sprites[1] = new BufferedImage[3];
      for (int i = 0; i < 3; ++i){
        sprites[1][i] = sprites[0][2-i];
      }
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading CrossBow Sprite: " + e);
      System.exit(-1);
    }
  }

  /**
   * Main Constructor
  */
  public CrossBow(){
    this(new int[]{1,2});
  }

  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public CrossBow(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{500,50}));
    stackable = false;
  }

  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public CrossBow(double xPos, double yPos, double rotation, long id){
    super(xPos,yPos,rotation, id);
    this.sprite = new Sprite(icon, new int[]{1,2}, sprites, new int[]{500,50});
  }

  /**
  * Draws a crossbow equipped to the player
  * @param g the graphics context
  */
  public void drawEquipped(Graphics g, Player player){
    transform = player.getTransform();
    transform.translate(-18, -110);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }

  /**
  * Shoots an arrow
  * @param p the pointer position on the screen relative to the player
  */
  public void attack(Point p, Player player){
    //checks if crossbow is reloaded
    if (sprite.isState(SHOOT_READY, false)){
      //creates a new arrow
      new ArrowProjectile(player, p.getX(), p.getY());
      //starts shoot animation
      sprite.startAnimation(1);
    }
  }

  /**
  * Reloads the weapon
  * @param p the pointer position on the screen relative to the player
  */
  public void attack2(Point p, Player player){
    reload();
  }

  /**
  * Reloads the weapon
  */
  public void reload(){
    //checks if crossbow is ready to be reloaded
    if (sprite.isState(RELOAD_READY, false)){
      if (!MainPlayer.getMainPlayer().getInvetory().removeItem(new Arrow(1))){return;}
      //starts the reload
      sprite.startAnimation(0);
    }
  }
}
