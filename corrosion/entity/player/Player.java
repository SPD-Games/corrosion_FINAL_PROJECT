/**
  * Michael Metzinger
  * December 12 2018
  *
  */

package corrosion.entity.player;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.File;
import javax.imageio.*;

import corrosion.entity.*;
import corrosion.entity.item.equippable.*;

public class Player extends Entity{
  protected static BufferedImage img;
  protected static BufferedImage hands;

  protected Equippable equipped = null;

  public Equippable getEquipped(){
    return equipped;
  }

  public void setEquipped(Equippable equipped){
    this.equipped = equipped;
  }

  /**
  * Initializes the player class
  */
  public static void init(){
    try{
      //sets sprite image
      img = ImageIO.read(new File("sprites/player.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Player Sprite: " + e);
      System.exit(-1);
    }
    try{
      //sets sprite image
      hands = ImageIO.read(new File("sprites/hands.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Player hands: " + e);
      System.exit(-1);
    }
  }

  /**
   * Main Constructor
   * @param xPos x position of the position
   * @param yPos y position of the position
  */
  public Player(double xPos, double yPos, double rotation){
    super(xPos, yPos, rotation);
  }

  /**
  * Draws the player to the Window
  * @param g the graphics context
  */
  public void draw(Graphics g, long t){
    transform.setToTranslation(xPos-50, yPos-50);
    transform.rotate(rotation, 50, 50);
    ((Graphics2D)(g)).drawImage(img, transform, null);

    //draws the equipped item
    drawEquipped(g);
  }

  /**
  * Draws the item that the player has equipped
  * @param g the graphics context
  */
  public void drawEquipped(Graphics g){
    //check if an item is equipped
    if (equipped == null){
      //draw hands
      transform.translate(-5, -5);
      ((Graphics2D)(g)).drawImage(hands, transform, null);
    } else {
      //draw equipped item
      equipped.drawEquipped(g);
    }
  }

  public void die(){}
  public void hit(double damage){}

}
