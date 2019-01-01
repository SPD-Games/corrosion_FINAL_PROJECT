//Edward Pei
//Dec 29, 2018
//Apple class
package corrosion.entity.item.equippable;

//TODO: get apples to add health and stuff idk how to do this micheal dad help

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

public class Apple extends Equippable{

  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[2][];
  public Sprite sprite;

  public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(new File("sprites/apple/icon.png"));
      //loads eating animations
      sprites[0] = new BufferedImage[4];
      for (int i = 1; i <= 4; ++i){
        sprites[0][i-1] = ImageIO.read(new File("sprites/apple/animation/frame" + i + ".png"));
      }

      //loads shooting animations
      sprites[1] = new BufferedImage[3];
      for (int i = 0; i < 3; ++i){
        sprites[1][i] = sprites[0][2-i];
      }
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading apple Sprite: " + e);
      System.exit(-1);
    }
  }

  public Apple(Player p){

  }

  public void drawEquipped(Graphics g){
    transform = player.getTransform();
    transform.translate(-18, -110);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), player.getTransform(), null);
  }

  public void draw(Graphics g, long t){}

  ///////

  public void attack(Point p){

  }

  /**
  * Reloads the weapon
  * @param p the pointer position on the screen relative to the player
  */
  public void attack2(Point p){

  }

  /**
  * Does nothing, you cant reload the item
  */
  public void reload(){

  }


}
