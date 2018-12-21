/**
* Michael Metzinger
* Dec 13 2018
* An arrow that is shot
*/

package corrosion.entity.projectile;

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
import corrosion.network.Client;
import corrosion.network.protocol.*; 


public class ArrowProjectile extends Projectile{
  private static BufferedImage img;

  private static final double MAX_VEL = 2;
  private static final double MAX_RANGE = 3000;

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

  public ArrowProjectile(double xPos, double yPos, double xVel, double yVel, double r, long id){
    super(xPos,yPos,xVel,yVel,r,id);
  }


  /**
  * Main Constructor
  * @param mouseX the x position of the mouse cursor relative to the player
  * @param mouseY the y position of the mouse cursor relative to the player
  */
  public ArrowProjectile(Player player, double mouseX, double mouseY){
    super();
    this.player = player;
    id = Client.getId();
    this.rotation = Math.atan2(mouseX, mouseY);
    this.xPos = player.getXPos() + 189 * Math.sin(rotation);
    this.yPos = player.getYPos() - 189 * Math.cos(rotation);
    this.lastXPos = xPos;
    this.lastYPos = yPos;
    this.xVel = MAX_VEL*Math.sin(rotation);
    this.yVel = MAX_VEL*-Math.cos(rotation);
    range = MAX_RANGE;

    Protocol.send(6, this, Client.getConnection());
    Client.addProjectile(this);
  }


  //TODO hit checking
  public void hitCheck(){
    return;
  }

  public void hit(){
    Client.removeProjetile(this);
  }

  public void update(long t){
    xPos += t * xVel;
    yPos += t * yVel;
    range -= MAX_VEL*t;
    if (range < 0){
      hit();
    }
    if (player != null){
      hitCheck();
    }
  }

  /**
  * Draws a Arrow
  * @param g the graphics context to draw to
  * @param t the time passes since the last frame
  */
  public void draw(Graphics g, long t){
    update(t);
    //draws the arrow
    transform.setToTranslation(xPos - 3, yPos);
    transform.rotate(rotation, 3, 0);
    ((Graphics2D)(g)).drawImage(img, transform, null);
    //((Graphics2D)(g)).drawLine((int)xPos, (int)yPos, (int)lastXPos, (int)lastYPos);
    lastXPos = xPos;
    lastYPos = yPos;
  }
}
