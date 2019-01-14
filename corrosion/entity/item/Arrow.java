//Edward Pei
//January 11, 2019
//Bandage class
package corrosion.entity.item;

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

public class Arrow extends Item{
  public void attackOff(Player player){}

  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][];
  private final static int[] LAST_FRAME = {0,1};
  public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(new File("sprites/arrowIcon.png"));

    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Arrow Sprite: " + e);
      System.exit(-1);
    }
  }
  public BufferedImage getIcon(){
    return icon;
  }
  public Arrow(){
    super();
    sprite = new Sprite(icon, null, null, null);
  }
  public Arrow(int stackSize){
    this();
    this.stackSize = stackSize;
  }

  public Arrow(double x, double y, double r, long id){
    super(x,y,r,id);
    sprite = new Sprite(icon, null, null, null);
  }

  public String getInfo(){
    return  stackSize + "";
  }

  public void fromServer(){
    sprite = new Sprite(icon, null, null, null);
  }


}
