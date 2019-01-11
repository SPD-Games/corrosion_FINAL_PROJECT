//Henry Lim, Edward Pei
//Jan. 09, 2018
//SMG class
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


public class Smg extends Equippable implements Serializable{
  public void attackOff(Player player){}

  // get the icons and animations for the smg
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][3];
  private final int[] SHOOT_READY = {0,2};
  //private final int[] RELOAD_READY = {1,2};

    /**
    *Initialize the Smg object
    */
    public static void init(){

      try{
        //loads icon
        icon = ImageIO.read(new File("sprites/smg/icon.png"));
        sprites[0][1] = ImageIO.read(new File("sprites/smg/animation/frame" + 2 + ".png"));
        sprites[0][2] = ImageIO.read(new File("sprites/smg/animation/frame" + 1 + ".png"));
        sprites[0][0] = sprites[0][2];
      }catch(Exception e){
        //exits on error with message
        System.out.println("Reading Smg Sprite: " + e);
        System.exit(-1);
      }
    }
    public BufferedImage getIcon(){
      return icon;
    }
  /**
  * constuctor for the smg
  */
  public Smg(){
    this(new int[]{0,2});
  }

  /**
  * constuctor for the smg
  */
  public Smg(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{50}));
    isStackable = false;
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






  public void attack(Point p, Player player){
    if (sprite.isState(SHOOT_READY, false)){
      //creates a new bullet
      //BulletProjectile a = new BulletProjectile(player, p.getX(), p.getY(),5,2000,10);
      BulletProjectile a = new BulletProjectile(player, p.getX(), p.getY(),2,2000,7,12,140);
      //starts shoot animation
      sprite.startAnimation(0);
    }
  }
  public void attack2(Point p, Player player){}

  /**
  * reloads the smg
  */
  public void reload(){}
}
