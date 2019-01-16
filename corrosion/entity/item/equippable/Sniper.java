//Edward Pei
//January 8 2019
//Sniper class
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


public class Sniper extends Equippable implements Serializable{
  /**
  * attack Method
  * @param player using shotgun
  */
  public void attackOff(Player player){}
    /**
    * Sprite method for rifle
    */
    public void fromServer(){
      sprite = new Sprite(icon, state, sprites, delay);
    }
  // get the icons and animations for the rifle
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[2][3];
  private final int[] SHOOT_READY = {1,2};
  private final int[] RELOAD_READY = {0,2};

  /**
  * Return shooting state info
  * @return String of state
  */
  @Override
  public String getInfo(){
    if(sprite.isState(SHOOT_READY, false)){
      return "1";
    }
    return "0";
  }
    /**
    *Initialize the rifle object
    */
    public static void init(){

      try{
        //loads icon
        icon = ImageIO.read(new File("sprites/sniper/icon.png"));
        sprites[0][1] = ImageIO.read(new File("sprites/sniper/animation/frame" + 2 + ".png"));
        sprites[0][2] = ImageIO.read(new File("sprites/sniper/animation/frame" + 1 + ".png"));
        sprites[0][0] = sprites[0][2];

        sprites[1][0] = sprites[0][2];
        sprites[1][1] = sprites[0][2];
        sprites[1][2] = sprites[0][2];
      }catch(Exception e){
        //exits on error with message
        System.out.println("Reading sniper Sprite: " + e);
        System.exit(-1);
      }
    }

  /**
  * constuctor for the sniper
  */
  public Sniper(){
    this(new int[]{0,2});
  }
  /**
  * method to return icon
  * @return icon
  */
  public BufferedImage getIcon(){
    return icon;
  }
  /**
  * constuctor for the rifle
  * @param state of sniper
  */
  public Sniper(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{50, 1000}));
    stackable = false;
  }

  /**
  * Constuctor
  * @param x position
  * @param y position
  * @param r rotation applied
  * @param id id number associated with the Equippable
  */
  public Sniper(double xPos, double yPos, double rotation, long id){
    super(xPos,yPos,rotation, id);
    this.sprite = new Sprite(icon, new int[]{0,2}, sprites, new int[]{50,1000});
  }

  /**
  * Draw the item
  * @param g the graphics tool used to draw
  * @param player using sniper
  */
  public void drawEquipped(Graphics g, Player player){
    if (player == null){return;}
    transform = player.getTransform();
    transform.translate(0, -170);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }



  /**
  * method shooting
  * @param p pointer relative to player
  * @param player using sniper
  */
  public void attack(Point p, Player player){
    if (sprite.isState(SHOOT_READY, false)){
      //creates a new bullet
      BulletProjectile a = new BulletProjectile(player, p.getX(), p.getY(),10,5000,80,10,220);
      //starts shoot animation
      sprite.startAnimation(0);
    }
  }

  /**
  * reload method
  * @param p pointer relative to player
  * @param player using sniper
  */
  public void attack2(Point p, Player player){
    reload();
  }

  /**
  * reloads the rifle
  */
  public void reload(){
    if (sprite.isState(RELOAD_READY, false)){
      if (!MainPlayer.getMainPlayer().getInvetory().removeItem(new Bullet(1))){return;}
      sprite.startAnimation(1);
    }
  }
}
