//Henry Lim, Edward Pei
//Dec 17, 2018
//Pistol class
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
import java.io.Serializable;

import corrosion.Sprite;
import corrosion.entity.Entity;
import corrosion.entity.player.Player;
import corrosion.entity.projectile.*;

import corrosion.network.*;
import corrosion.network.protocol.*;


public class Pistol extends Equippable implements Serializable{
  // get the icons and animations for the pistol
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][3];
  private final int[] SHOOT_READY = {0,2};
  //private final int[] RELOAD_READY = {1,2};
  public String getInfo(){
    //TODO
    return "TODO";
  }
    /**
    *Initialize the Pistol object
    */
    public static void init(){

      try{
        //loads icon
        icon = ImageIO.read(new File("sprites/pistol/icon.png"));
        sprites[0][1] = ImageIO.read(new File("sprites/pistol/animation/frame" + 2 + ".png"));
        sprites[0][2] = ImageIO.read(new File("sprites/pistol/animation/frame" + 1 + ".png"));
        sprites[0][0] = sprites[0][2];
      }catch(Exception e){
        //exits on error with message
        System.out.println("Reading Pistol Sprite: " + e);
        System.exit(-1);
      }
    }
    public BufferedImage getIcon(){
      return icon;
    }
  /**
  * constuctor for the pistol
  */
  public Pistol(){
    this(new int[]{0,2});
  }

  /**
  * constuctor for the pistol
  */
  public Pistol(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{50}));
    stackable = false;
  }

  /**
  * Draw the item
  * @param g the graphics tool used to draw
  */
  public void drawEquipped(Graphics g, Player player){
    if (player == null){return;}
    transform = player.getTransform();
    transform.translate(-47, -110);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }

  public void draw(Graphics g, long t){}




  public void attack(Point p, Player player){
    if (sprite.isState(SHOOT_READY, false)){
      //creates a new bullet
      //BulletProjectile a = new BulletProjectile(player, p.getX(), p.getY(),5,2000,10);
      BulletProjectile a = new BulletProjectile(player, p.getX(), p.getY(),2,3000,10,12,140);
      //starts shoot animation
      sprite.startAnimation(0);
    }
  }
  public void attack2(Point p, Player player){}

  /**
  * reloads the pistol
  */
  public void reload(){}
}
