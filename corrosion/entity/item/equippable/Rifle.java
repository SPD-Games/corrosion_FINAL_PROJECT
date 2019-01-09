//Henry Lim,Edward Pei
//January 8, 2018
//Rifle class
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
import corrosion.entity.player.Player;
import corrosion.entity.projectile.BulletProjectile;;

public class Rifle extends Equippable{
    //TODO move all images and draw handling in Usable
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[2][];

  private final int[] SHOOT_READY = {0,3};
  //private final int[] RELOAD_READY = {1,2};
  public Sprite sprite;

  public static void init(){

    try{
      //loads icon
      icon = ImageIO.read(new File("sprites/rifle/icon.png"));
      sprites[0][1] = ImageIO.read(new File("sprites/rifle/animation/frame" + 2 + ".png"));
      sprites[0][2] = ImageIO.read(new File("sprites/rifle/animation/frame" + 1 + ".png"));
      sprites[0][0] = sprites[0][2];
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading rifle Sprite: " + e);
      System.exit(-1);
    }
  }
  /**
  * constuctor for the pistol
  */
  public Rifle(){
    super(new Sprite(icon, new int[]{0,2}, sprites, new int[]{50}));
  }

  /**
  * constuctor for the pistol
  */
  public Rifle(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{50}));
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
      BulletProjectile a = new BulletProjectile(player, p.getX(), p.getY(),0,3000,10);
      //starts shoot animation
      sprite.startAnimation(0);
    }
  }
  public void attack2(Point p, Player player){}

  /**
  * reloads the Rifle
  */
  public void reload(){}

}
