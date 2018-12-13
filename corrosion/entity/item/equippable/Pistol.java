//Henry Lim, Edward Pei
//Dec 12, 2018
//Pistol class
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

import corrosion.Sprite;
import corrosion.entity.Entity;
import corrosion.entity.player.Player;
import corrosion.entity.projectile.Arrow;


public class Pistol extends Equippable{
  // get the icons and animations for the pistol
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[2][];
  private final int[] SHOOT_READY = {0,3};
  private final int[] RELOAD_READY = {1,2};
  public Sprite sprite;


    /**
    *Initialize the Pistol object
    */
    public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(new File("sprites/pistol/icon.png"));
      //loads relaod animations
      sprites[0] = new BufferedImage[4];
      for (int i = 1; i <= 4; ++i){
        sprites[0][i-1] = ImageIO.read(new File("sprites/pistol/animation/frame" + i + ".png"));
      }

      //loads shooting animations
      sprites[1] = new BufferedImage[3];
      for (int i = 0; i < 3; ++i){
        sprites[1][i] = sprites[0][2-i];
      }
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Pistol Sprite: " + e);
      System.exit(-1);
    }
  }

  /**
  * constuctor for the pistol
  * @param p player who has the pistol
  */
  public Pistol(Player p){
    super(p);
    this.sprite = new Sprite(icon, new int[]{1,2}, sprites, new int[]{500,50});
  }

  /**
  * Draw the item
  * @param g the graphics tool used to draw
  */
  public void drawEquipped(Graphics g){
    if (player == null){return;}
    transform = player.getTransform();
    transform.translate(-18, -110);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), player.getTransform(), null);
  }

  public void draw(Graphics g, long t){}



  /**
  * reloads the pistol
  */
  public void reload(){
    // only reload if it is ready to reload
    if (sprite.isState(RELOAD_READY, false)){
      sprite.startAnimation(0);
    }
  }


}
