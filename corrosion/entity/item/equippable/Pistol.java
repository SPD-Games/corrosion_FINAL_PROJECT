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
  /**
  *Method not in use for pistol class
  *@param player player class
  */
  public void attackOff(Player player){}
  //Sprite method for pistol
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

  /**
  * Method to ammo held
  * @return ammo held
  */
  public String getInfo(){
    return ammo + "";
  }
    /**
    *Initialize the Pistol object
    */
    public static void init(){

      try{
        //loads icon
        icon = ImageIO.read(Pistol.class.getResourceAsStream("/sprites/pistol/icon.png"));
        sprites[0][1] = ImageIO.read(Pistol.class.getResourceAsStream("/sprites/pistol/animation/frame" + 2 + ".png"));
        sprites[0][2] = ImageIO.read(Pistol.class.getResourceAsStream("/sprites/pistol/animation/frame" + 1 + ".png"));
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
  * @param state of pistol
  */
  public Pistol(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{50, 500}));
    stackable = false;
  }

  /**
  * Constuctor
  * @param x position
  * @param y position
  * @param r rotation applied
  * @param id id number associated with the Equippable
  */
  public Pistol(double xPos, double yPos, double rotation, long id){
    super(xPos,yPos,rotation, id);
    this.sprite = new Sprite(icon, new int[]{0,2}, sprites, new int[]{50,500});
  }

  /**
  * Draw the item
  * @param g the graphics tool used to draw
  * @param player using pistol
  */
  public void drawEquipped(Graphics g, Player player){
    //If ready to use...
    if (player == null){return;}
    if (sprite.isState(RELOAD_DONE, false)){
      ammo = reloadTo;
      sprite.setState(SHOOT_READY[0], SHOOT_READY[1]);
    }
    //Draw sprite
    transform = player.getTransform();
    transform.translate(-47, -110);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }

  /**
  * attack method
  * @param p pointer relative to player
  * @param player using pistol
  */
  public void attack(Point p, Player player){
    if (sprite.isState(SHOOT_READY, false) && ammo > 0){
      //creates a new bullet
      //BulletProjectile a = new BulletProjectile(player, p.getX(), p.getY(),5,2000,10);
      BulletProjectile a = new BulletProjectile(player, p.getX(), p.getY(),2,3000,14,12,140);
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
    //If not fully loaded, reload
    if (ammo != MAX_AMMO){
      reloadTo = Math.min(MainPlayer.getMainPlayer().getInvetory().getAmount(new Bullet()) + ammo, MAX_AMMO);
      MainPlayer.getMainPlayer().getInvetory().removeItem(new Bullet(reloadTo-ammo));
      sprite.startAnimation(1);
    }
  }
}
