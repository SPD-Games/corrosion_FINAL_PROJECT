//Edward Pei
//January 8 2019
//Smg class
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

import corrosion.input.Mouse;
import corrosion.entity.player.MainPlayer;
import corrosion.Sprite;
import corrosion.entity.Entity;
import corrosion.entity.player.Player;
import corrosion.entity.projectile.*;
import corrosion.entity.player.MainPlayer;
import corrosion.entity.item.Bullet;
import corrosion.network.*;
import corrosion.network.protocol.*;


public class Smg extends Equippable implements Serializable{
  public boolean shooting = false;
  public void fromServer(){
    sprite = new Sprite(icon, state, sprites, delay);
  }
  public void attackOff(Player player){
    shooting = false;
  }

  // get the icons and animations for the rifle
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[2][];
  private static final int[] SHOOT_READY = {0,4};
  private int ammo = 0;
  private int MAX_AMMO = 30;
  private static final int[] RELOAD_DONE = {1,2};
  public int reloadTo = 30;

  public String getInfo(){
    return ammo + "";
  }
    /**
    *Initialize the rifle object
    */
    public static void init(){

      try{
        //loads icon
        icon = ImageIO.read(new File("sprites/smg/icon.png"));
        sprites[0] = new BufferedImage[5];
        sprites[0][1] = ImageIO.read(new File("sprites/smg/animation/frame" + 2 + ".png"));
        sprites[0][2] = ImageIO.read(new File("sprites/smg/animation/frame" + 1 + ".png"));
        sprites[0][0] = sprites[0][2];
        sprites[0][3] = sprites[0][2];
        sprites[0][4] = sprites[0][2];
        sprites[1] = new BufferedImage[3];
        sprites[1][0] = sprites[0][2];
        sprites[1][1] = sprites[0][2];
        sprites[1][2] = sprites[0][2];
      }catch(Exception e){
        //exits on error with message
        System.out.println("Reading rifle Sprite: " + e);
        System.exit(-1);
      }
    }
    public BufferedImage getIcon(){
      return icon;
    }
  /**
  * constuctor for the rifle
  */
  public Smg(){
    this(new int[]{0,4});
  }

  /**
  * constuctor for the rifle
  */
  public Smg(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{25,1000}));
    stackable = false;
  }

  /**
  * Draw the item
  * @param g the graphics tool used to draw
  */
  public void drawEquipped(Graphics g, Player player){
    if (player == null){return;}
    if (sprite.isState(SHOOT_READY, false)){
      if(shooting && ammo > 0){
        shoot();
      }
    } else if (sprite.isState(RELOAD_DONE, false)){
      ammo = reloadTo;
      sprite.setState(SHOOT_READY[0], SHOOT_READY[1]);
    }
    transform = player.getTransform();
    transform.translate(-50, -140);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }



  public void shoot(){
    Point p = Mouse.getPosition();
    Player player = MainPlayer.getMainPlayer();
    //creates a new bullet
    BulletProjectile a = new BulletProjectile(player, p.getX(), p.getY(),3,3000,15,14,160);
    //starts shoot animation
    ammo--;
    sprite.startAnimation(0);
  }

  public void attack(Point p, Player player){
    shooting = true;
  }
  public void attack2(Point p, Player player){}

  /**
  * reloads the rifle
  */
  public void reload(){
    if (!sprite.isState(SHOOT_READY, false)){return;}
    if(ammo != MAX_AMMO){
      reloadTo = Math.min(MainPlayer.getMainPlayer().getInvetory().getAmount(new Bullet()) + ammo, MAX_AMMO);
      MainPlayer.getMainPlayer().getInvetory().removeItem(new Bullet(reloadTo-ammo));
      sprite.startAnimation(1);
    }
  }
}
