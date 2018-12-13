/**
* Michael Metzinger
* Dec 13 2018
* An arrow that is shot
*/

package corrosion.entity.item;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.geom.AffineTransform;

import corrosion.drawingstate.GameDrawing;
import corrosion.entity.player.Player;
import corrosion.drawingstate.*;
import corrosion.entity.item.*;
import corrosion.entity.*;
import corrosion.Projectile;

public class Arrow extends Item implements Projectile{
  private static BufferedImage img;
  private double xVel, yVel;
  private double endXPos, endYPos;
  private double lastXPos, lastYPos;
  private Player player;
  /**
  * Initializes the player class
  */
  public static void init(){
    try{
      //sets sprite image
      img = ImageIO.read(new File("sprites/arrow.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Arrow Sprite: " + e);
      System.exit(-1);
    }
  }

  public Arrow(double xPos, double yPos, double r){
    super(xPos,yPos,r);
  }

  /**
  * Main Constructor
  * @param player the player who created the Arrow
  * @param mouseX the x position of the mouse cursor relative to the player
  * @param mouseY the y position of the mouse cursor relative to the player
  */
  public Arrow(Player player, double mouseX, double mouseY){
    super();
    this.player = player;
    this.rotation = Math.atan2(mouseX, mouseY);
    this.xPos = player.getXPos() + 189 * Math.sin(rotation);
    this.yPos = player.getYPos() - 189 * Math.cos(rotation);
    this.lastXPos = xPos;
    this.lastYPos = yPos;
    this.endXPos = player.getXPos() + mouseX;
    this.endXPos = player.getYPos() + mouseY;
    this.xVel = 1*Math.sin(rotation);
    this.yVel = 1*-Math.cos(rotation);
    this.transform = new AffineTransform();

    //add to active entities
    GameDrawing.addEntity(this);
  }

  //TODO hit checking
  public boolean hitCheck(){
    return false;
  }
  public void hit(){
    GameDrawing.addEntity(new Arrow(xPos, yPos, rotation));
    GameDrawing.removeEntity(this);
  }

  /**
  * Draws a Arrow
  * @param g the graphics context to draw to
  * @param t the time passes since the last frame
  */
  public void draw(Graphics g, long t){
    //checks if the item is shot
    if (player != null){
      //updates the position
      xPos += t * xVel;
      yPos += t * yVel;
    }

    //draws hit line

    //draws the arrow
    transform.setToTranslation(xPos - 3, yPos);
    transform.rotate(rotation, 3, 0);
    ((Graphics2D)(g)).drawImage(img, transform, null);
    ((Graphics2D)(g)).drawLine((int)xPos, (int)yPos, (int)lastXPos, (int)lastYPos);
    lastXPos = xPos;
    lastYPos = yPos;
  }
}
