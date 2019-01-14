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
  private final int[] SHOOT_READY = {0,3};
  public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(new File("sprites/club/icon.png"));
      //loads relaod animations
      sprites[0] = new BufferedImage[5];
      for (int i = 1; i <= 5; ++i){
        sprites[0][i-1] = ImageIO.read(new File("sprites/club/animation/frame" + i + ".png"));
      }
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
    this(new int[]{0,0});
  }

  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public Tool(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{500,50}));
    stackable = false;
  }

  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public Tool(double xPos, double yPos, double rotation, long id){
    super(xPos,yPos,rotation, id);
    this.sprite = new Sprite(icon, new int[]{1,2}, sprites, new int[]{500,50});
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

  }

  public String getInfo(){
    return "";
  }

  @Override
  public BufferedImage getIcon(){
    System.out.println(icon);
    System.exit(-1);
    return icon;
  }

  public void fromServer(){
    this.sprite = new Sprite(icon, new int[]{0,2}, sprites, new int[]{50});
  }
}
