//Edward Pei
//January 11, 2019
//Bandage class
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
import corrosion.entity.player.*;

public class Bullet extends Equippable{
  public void attackOff(Player player){}

  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][];
  private final static int[] LAST_FRAME = {0,1};
  public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(new File("sprites/bulletIcon.png"));
      hands = ImageIO.read(new File("sprites/hands.png"));


    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Bullet Sprite: " + e);
      System.exit(-1);
    }
  }
  public BufferedImage getIcon(){
    return icon;
  }
  public Bullet(){
    this(new int[]{0,0});
  }

  public Bullet(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{0}));
  }

  public Bullet(double x, double y, double r, long id){
    super(x,y,r,id);
  }

  public void drawEquipped(Graphics g, Player player){
    transform = player.getTransform();
    transform.scale(.3,.3);
    transform.translate(-18, -65);
    ((Graphics2D)(g)).drawImage(hands, transform, null);
  }

  public void draw(Graphics g, long t){}

  public void attack(Point p, Player player){

  }

  /**
  * Reloads the weapon
  * @param p the pointer position on the screen relative to the player
  */
  public void attack2(Point p, Player player){

  }

  /**
  * Does nothing, you cant reload the item
  */
  public void reload(){

  }


}
