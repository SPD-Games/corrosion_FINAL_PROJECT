//Edward Pei
//Dec. 13, 2018
//SMG class

package corrosion.entity.item.equippable;
//imports
import javax.swing.Timer;
import javax.swing.AbstractAction;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.io.File;

import corrosion.Sprite;
import corrosion.entity.Entity;
import corrosion.entity.player.Player;

public class Rifle extends Equippable{
  //TODO move all images and draw handling in Usable
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[2][];

  private final int[] SHOOT_READY = {0,3};
  private final int[] RELOAD_READY = {1,2};
  public Sprite sprite;
  
  public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(new File("sprites/smg/icon.png"));
      //loads relaod animations
      sprites[0] = new BufferedImage[4];
      for (int i = 1; i <= 4; ++i){
        sprites[0][i-1] = ImageIO.read(new File("sprites/smg/animation/frame" + i + ".png"));
      }

      //loads shooting animations
      sprites[1] = new BufferedImage[3];
      for (int i = 0; i < 3; ++i){
        sprites[1][i] = sprites[0][2-i];
      }
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading smg Sprite: " + e);
      System.exit(-1);
    }
  }
  
  public Rifle(Player p){
    super(p);
    this.sprite = new Sprite(icon, new int[]{1,2}, sprites, new int[]{500,50});
  }
  
  public void drawEquipped(Graphics g){
    transform = player.getTransform();
    transform.translate(-18, -110);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), player.getTransform(), null);
  }

  public void draw(Graphics g, long t){}
  
  
  public void attack(Point p){
    //checks if smg is reloaded
    if (sprite.isState(SHOOT_READY, false)){
      sprite.startAnimation(1);
    }
  }

  /**
  * Reloads the smg
  * @param p the pointer position on the screen relative to the player
  */
  public void attack2(Point p){
    reload();
  }

  /**
  * Reloads the smg
  */
  public void reload(){
    //checks if smg is ready to be reloaded
    if (sprite.isState(RELOAD_READY, false)){
      //starts the reload animation
      sprite.startAnimation(0);
    }
  }
  
  
}
