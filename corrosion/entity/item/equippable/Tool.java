package corrosion.entity.item.equippable;

import corrosion.entity.item.equippable.Equippable;
import java.awt.image.BufferedImage;
import corrosion.entity.Entity;
import java.io.Serializable;
import corrosion.Sprite;
import corrosion.network.Client;
import corrosion.network.protocol.Protocol;
import java.awt.*;
import java.awt.geom.*;
import corrosion.entity.player.*;
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

public class Tool extends Equippable{
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][];;
  private final int[] USE_READY = {0,5};
  public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(new File("sprites/club/icon.png"));
      //loads relaod animations
      sprites[0] = new BufferedImage[6];
      for (int i = 1; i <= 5; ++i){
        sprites[0][i-1] = ImageIO.read(new File("sprites/club/animation/frame" + i + ".png"));
      }
      sprites[0][5] = sprites[0][0];
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Tool Sprite: " + e);
      System.exit(-1);
    }
  }
  /**
   * Main Constructor
  */
  public Tool(){
    this(new int[]{0,5});
  }

  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public Tool(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{100}));
    stackable = false;
  }

  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public Tool(double xPos, double yPos, double rotation, long id){
    super(xPos,yPos,rotation, id);
    this.sprite = new Sprite(icon, new int[]{0,5}, sprites, new int[]{100});
  }

  public void reload(){

  }

  public void attack2(Point p, Player player){

  }

  public void attack(Point p, Player player){

  }

  public void attackOff(Player player){

  }

  public void drawEquipped(Graphics g, Player player){
    transform = player.getTransform();
    transform.translate(0, -120);
    transform.scale(0.25,0.25);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }

  public String getInfo(){
    return "";
  }

  public BufferedImage getIcon(){
    return icon;
  }

  public void fromServer(){
    this.sprite = new Sprite(icon, new int[]{0,5}, sprites, new int[]{100});
  }
}
