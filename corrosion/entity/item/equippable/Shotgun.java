//Edward Pei
//January 9, 2019
//shotgun class
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
import corrosion.entity.player.MainPlayer;
import corrosion.entity.item.Bullet;
import corrosion.Sprite;
import corrosion.entity.Entity;
import corrosion.entity.player.Player;
import corrosion.entity.projectile.*;

import corrosion.network.*;
import corrosion.network.protocol.*;


public class Shotgun extends Equippable {
  public void attackOff(Player player){}

  // get the icons and animations for the shotgun
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[3][3];
  private final int[] SHOOT_READY = {0,2};
  private final int[] RELOAD_READY = {1,2};
  private final int[] RELOAD_DONE = {2,2};
  private int ammo = 0;
  private int reloadTo = 0;

  /**
  *Initialize the shotgun object
  */
  public static void init(){

    try{
      //loads icon
      icon = ImageIO.read(new File("sprites/shotgun/icon.png"));
      sprites[0][1] = ImageIO.read(new File("sprites/shotgun/animation/frame" + 2 + ".png"));
      sprites[0][2] = ImageIO.read(new File("sprites/shotgun/animation/frame" + 1 + ".png"));
      sprites[0][0] = sprites[0][2];
      sprites[1][0] = sprites[0][2];
      sprites[1][1] = ImageIO.read(new File("sprites/shotgun/animation/frame" + 3 + ".png"));
      sprites[1][2] = sprites[0][2];
      for (int i = 0; i < 3; ++i){
        sprites[2][i] = sprites[0][2];
      }
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading shotgun Sprite: " + e);
      System.exit(-1);
    }
  }
  public String getInfo(){
    return ammo + "";
  }
  public void fromServer(){
    sprite = new Sprite(icon, state, sprites, new int[]{50,50,1000});
  }
  public BufferedImage getIcon(){
    return icon;
  }
  /**
  * constuctor for the shotgun
  */
  public Shotgun(){
    this(new int[]{0,2});
  }

  /**
  * constuctor for the shotgun
  */
  public Shotgun(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{50,50,1000}));
    stackable = false;
  }

  /**
  * Draw the item
  * @param g the graphics tool used to draw
  */
  public void drawEquipped(Graphics g, Player player){
    if (player == null){return;}
    if (sprite.isState(RELOAD_DONE, false)){
      ammo = reloadTo;
      sprite.setState(SHOOT_READY[0], SHOOT_READY[1]);
    }
    transform = player.getTransform();
    transform.translate(-90, -190);
    transform.scale(1.4,1.4);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }



    //+ (int)(Math.random() * 100 - 50)


  public void attack(Point p, Player player){
    if (sprite.isState(SHOOT_READY, false) && ammo > 0){
      //creates the bullet spread of the shotgun
      BulletProjectile a1 = new BulletProjectile(player, p.getX()+ (int)(Math.random() * 60 -30), p.getY()+ (int)(Math.random() * 60 -30), 2,1000,16,12,150);
      BulletProjectile a2 = new BulletProjectile(player, p.getX() + (int)(Math.random() * 50 - 25), p.getY() +  (int)(Math.random() * 50 - 25), 2,1000,16,12,150);
      BulletProjectile a3 = new BulletProjectile(player, p.getX() + (int)(Math.random() * 50 - 25), p.getY()  + (int)(Math.random() * 50 - 25), 2,1000,16,12,150);
      BulletProjectile a4 = new BulletProjectile(player, p.getX()+ (int)(Math.random() * 100 - 50), p.getY() + (int)(Math.random() * 100 - 50) , 2,1000,16,12,150);
      BulletProjectile a5 = new BulletProjectile(player, p.getX() + (int)(Math.random() * 100 - 50), p.getY() + (int)(Math.random() * 100 - 50), 2,1000,16,12,150);
      //starts shoot animation

      sprite.startAnimation(-ammo+2);
      ammo--;
    }
  }
  public void attack2(Point p, Player player){}

  /**
  * reloads the shotgun
  */
  public void reload(){
    if (!sprite.isState(RELOAD_READY, false) && !sprite.isState(SHOOT_READY, false)){return;}
    if (ammo == 0 || ammo == 1){
      reloadTo = Math.min(MainPlayer.getMainPlayer().getInvetory().getAmount(new Bullet()) + ammo, 2);
      MainPlayer.getMainPlayer().getInvetory().removeItem(new Bullet(reloadTo - ammo));
      sprite.startAnimation(2);
    }
  }
}
