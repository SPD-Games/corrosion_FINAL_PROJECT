/**
* Michael Metzinger
* Dec 13 2018
* An arrow that is shot
*/

package corrosion.entity.projectile;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.awt.geom.*;

import corrosion.drawingstate.GameDrawing;
import corrosion.entity.player.*;
import corrosion.drawingstate.*;
import corrosion.entity.item.*;
import corrosion.entity.*;
import corrosion.network.Client;
import corrosion.network.protocol.*;
import corrosion.HitDetection;


public class BulletProjectile extends Projectile{
  private static BufferedImage img;

  private double MAX_VEL = 2;
  private double MAX_RANGE = 3000;
  private int damage = 50;
  private int shift;

  /**
  * Initializes the player class
  */
  public static void init(){
    try{
      //sets sprite image
      img = ImageIO.read(BulletProjectile.class.getResourceAsStream("/sprites/bullet.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading bullet Sprite: " + e);
      System.exit(-1);
    }
  }

  /**
  * bullet Constructor
  */
  public BulletProjectile(double xPos, double yPos, double xVel, double yVel, double r, long id){
    super(xPos,yPos,xVel,yVel,r,id);
    range = MAX_RANGE;
  }


  /**
  * Main Constructor
  * @param mouseX the x position of the mouse cursor relative to the player
  * @param mouseY the y position of the mouse cursor relative to the player
  */
  public BulletProjectile(Player player, double mouseX, double mouseY,double vel, double r, int damage,int shift, int gunRad){
    super();
    this.player = player;
    id = Client.getId();
    this.MAX_VEL = vel;
    this.MAX_RANGE = r;
    this.damage = damage;
    this.rotation = Math.atan2(mouseX, mouseY);
    this.xPos = player.getXPos() + gunRad * Math.sin(rotation);
    this.yPos = player.getYPos() - gunRad * Math.cos(rotation);
    this.lastXPos = player.getXPos();
    this.lastYPos = player.getYPos();
    this.shift = shift;
    this.xVel = MAX_VEL*Math.sin(rotation);
    this.yVel = MAX_VEL*-Math.cos(rotation);
    range = MAX_RANGE;
    Protocol.send(6, this, Client.getConnection());
    Client.addProjectile(this);
  }


  /**
  * check if arrow hits anything
  */
  public void hitCheck(){

    // check if a bullet hits an entity
    ArrayList<Entity> entities = Client.getEntities();
    for (int i = 0; i < entities.size(); ++i){

      Entity e = entities.get(i);
      if (e == this){continue;}
      else if (e instanceof Projectile){continue;}
      if (HitDetection.hit(e.getHitBox(), getHitBox())){
        if (!isHit && player != null){
          // deal damage to the entity
          e.hit(damage);
          Protocol.send(8 ,new HitMarker(getXPos(),getYPos(), "-"+damage), Client.getConnection());
        }
        hit();
        return;
      }
    }

    // check if a player is hit by a bullet
    ArrayList<Player> players = Client.getPlayers();
    for (int i = 0; i < players.size(); ++i){
      if (players.get(i).equals(player)){continue;}
      if (HitDetection.hit(players.get(i).getHitBox(), getHitBox())){
        if (!isHit && player != null){
          // damage player that is hit
          ArrayList out = new ArrayList();
          out.add(players.get(i).getId());
          out.add(damage);
          Protocol.send(8 ,new HitMarker(players.get(i).getXPos()+50,players.get(i).getYPos()+50, "-"+damage), Client.getConnection());
          Protocol.send(10, out, Client.getConnection());
        }
        hit();
        return;
      }
    }

    if (MainPlayer.getMainPlayer().equals(player)){return;}
    if (HitDetection.hit(MainPlayer.getMainPlayer().getHitBox(), getHitBox())){
      hit();
    }
  }

  /**
  * remove bullet
  */
  public void hit(){
    isHit = true;
    Client.removeProjetile(this);
  }


  /**
  * upate the position of bullet
  * @param t the time passes since the last frame
  */
  public void update(long t){
    xPos += t * xVel;
    yPos += t * yVel;
    range -= MAX_VEL*t;
    // check if reached max range
    if (range < 0){
      // remove bullet
      hit();
    }
    // check if bullet hits anyone
    hitCheck();
  }

  /**
  * Draws a Arrow
  * @param g the graphics context to draw to
  * @param t the time passes since the last frame
  */
  public void draw(Graphics g, long t){
    update(t);
    //draws the bullet
    transform.setToTranslation(xPos - shift, yPos);
    transform.rotate(rotation, shift, 0);
    transform.scale(0.15,0.15);
    ((Graphics2D)(g)).drawImage(img, transform, null);
    lastXPos = xPos-3;
    lastYPos = yPos;
  }

  @Override
  /**
  * get the hitbox of the bullet
  * @return hit box
  */
  public Shape getHitBox(){
    Path2D  p = new Path2D.Double();
    p.moveTo(xPos, yPos);
    p.lineTo(lastXPos, lastYPos);
    p.lineTo((xPos + lastXPos)/2+0.1, (yPos + lastYPos)/2+0.1);
    return p;
  }
}
