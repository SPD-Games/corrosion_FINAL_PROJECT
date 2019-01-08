//Edward Pei
//Dec 17, 2018
//Pickaxe class
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


public class Pickaxe extends Equippable implements Serializable{
  // get the icons and animations for the axe
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][3]; // what does this one even do?????
  private final int[] SWING_READY = {0,2};
  //private final int[] RELOAD_READY = {1,2};

    /**
    *Initialize the Pistol object
    */
    public static void init(){

      try{
        //loads icon
        icon = ImageIO.read(new File("sprites/pickaxe/icon.png"));
        sprites[0][1] = ImageIO.read(new File("sprites/pickaxe/animation/frame" + 2 + ".png"));
        sprites[0][2] = ImageIO.read(new File("sprites/pickaxe/animation/frame" + 1 + ".png"));
        sprites[0][0] = sprites[0][2];
      }catch(Exception e){
        //exits on error with message
        System.out.println("Reading Pickaxe Sprite: " + e);
        System.exit(-1);
      }
    }

  /**
  * constuctor for the Pickaxe
  */
  public Pickaxe(){
    super(new Sprite(icon, new int[]{0,2}, sprites, new int[]{50}));
  }

  /**
  * constuctor for the Pickaxe
  */
  public Pickaxe(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{50}));
  }

  /**
  * Draw the item
  * @param g the graphics tool used to draw
  */
  public void drawEquipped(Graphics g, Player player){
    // TODO: get the pickaxe to be drawn
    if (player == null){return;}
    transform = player.getTransform();
    transform.translate(-47, -110);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }

  public void draw(Graphics g, long t){}

  public void attack(Point p, Player player){
    if (sprite.isState(SWING_READY, false)){

      sprite.startAnimation(0);
    }
  }
  public void attack2(Point p, Player player){}

  /**
  * reloads the pistol
  */
  public void reload(){}
}
