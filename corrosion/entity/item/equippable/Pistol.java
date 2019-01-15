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
import corrosion.entity.player.MainPlayer;
import corrosion.entity.item.Bullet;
import corrosion.network.*;
import corrosion.network.protocol.*;


public class Pistol extends Equippable implements Serializable{
  public void attackOff(Player player){}
    public void fromServer(){
      sprite = new Sprite(icon, state, sprites, delay);
    }
  // get the icons and animations for the pistol
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[2][3];
  private final int[] SHOOT_READY = {0,2};
  private int ammo = 0;
  private int reloadTo = 8;
  private static final int MAX_AMMO = 8;
  private final int[] RELOAD_DONE = {1,2};
  public String getInfo(){
    return ammo + "";
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

        sprites[1][0] = sprites[0][2];
        sprites[1][1] = sprites[0][2];
        sprites[1][2] = sprites[0][2];
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
    super(new Sprite(icon, state, sprites, new int[]{50, 1000}));
    stackable = false;
  }

  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public Pistol(double xPos, double yPos, double rotation, long id){
    super(xPos,yPos,rotation, id);
    this.sprite = new Sprite(icon, new int[]{0,2}, sprites, new int[]{50,1000});
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
    transform.translate(-47, -110);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }

  public void attack(Point p, Player player){
    if (sprite.isState(SHOOT_READY, false) && ammo > 0){
      //creates a new bullet
      //BulletProjectile a = new BulletProjectile(player, p.getX(), p.getY(),5,2000,10);
      BulletProjectile a = new BulletProjectile(player, p.getX(), p.getY(),2,3000,10,12,140);
      ammo--;
      //starts shoot animation
      sprite.startAnimation(0);
    }
  }
  public void attack2(Point p, Player player){}

  /**
  * reloads the pistol
  */
  public void reload(){
    if (!sprite.isState(SHOOT_READY, false)){return;}
    if (ammo != MAX_AMMO){
      reloadTo = Math.min(MainPlayer.getMainPlayer().getInvetory().getAmount(new Bullet()) + ammo, MAX_AMMO);
      MainPlayer.getMainPlayer().getInvetory().removeItem(new Bullet(reloadTo-ammo));
      sprite.startAnimation(1);
    }
  }
}
