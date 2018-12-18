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
  private double range;
  private static final double MAX_VEL = 2;
  private static final double MAX_RANGE = 300;
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
    range = MAX_RANGE;
    this.xVel = MAX_VEL*Math.sin(rotation);
    this.yVel = MAX_VEL*-Math.cos(rotation);
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
    xPos += t * xVel;
    yPos += t * yVel;
    range -= MAX_VEL;
    if (range < 0){
      xVel = 0;
      yVel = 0;
    }

    //draws the arrow
    transform.setToTranslation(xPos - 3, yPos);
    transform.rotate(rotation, 3, 0);
    ((Graphics2D)(g)).drawImage(img, transform, null);
    //((Graphics2D)(g)).drawLine((int)xPos, (int)yPos, (int)lastXPos, (int)lastYPos);
    lastXPos = xPos;
    lastYPos = yPos;
  }
}
