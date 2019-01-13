/**
  * Michael Metzinger
  * December 12 2018
  *
  */

package corrosion.entity.player;

import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.ArrayList;
import corrosion.Drawing;
import corrosion.entity.item.equippable.*;
import corrosion.entity.player.Player;
import corrosion.input.*;
import java.awt.geom.Ellipse2D;
import corrosion.HitDetection;
import corrosion.entity.projectile.*;
import corrosion.entity.*;
import corrosion.network.*;
import corrosion.Inventory;
import corrosion.drawingstate.*;

public class MainPlayer extends Player{
  private final double SQRT_2 = Math.sqrt(2.0);
  private static MainPlayer mainPlayer;
  private int hp = 100;
  private transient Inventory inventory = new Inventory();
  //direction the player is moving (keyboard input)
  private boolean up, down, left, right;
   public void attackOff(){
     if (equipped != null){
       equipped.attackOff(this);
     }
   }

    public Inventory getInvetory(){
      return inventory;
    }

    /**
    * Gets the main player Object
    * @return the main player Object
    */
    public static MainPlayer getMainPlayer(){
      return mainPlayer;
    }

    public void setEquipped(int i){
      equipped = inventory.getHotBar(i);
      inventory.setEquipped(i);
    }

    /**
    * Creates the new MainPlayer
    * @param xPos the x position of the player
    * @param yPos the y position of the
    */
    public static void spawn(double xPos, double yPos, long id){
      mainPlayer = new MainPlayer(xPos, yPos, id);
      mainPlayer.spawn();
    }

    public void spawn(){
      hp = 50;
      int i = ThreadLocalRandom.current().nextInt(0, 4);
      if (i == 0){
        yPos = 750;
        xPos = ThreadLocalRandom.current().nextInt(1500, 18200);
      } else if (i == 1){
        yPos = 19300;
        xPos = ThreadLocalRandom.current().nextInt(1000, 19100);
      } else if (i == 2){
        xPos = 750;
        yPos = ThreadLocalRandom.current().nextInt(1800, 19300);
      } else if (i == 3){
        xPos = 19300;
        yPos = ThreadLocalRandom.current().nextInt(2200, 18600);
      }
      xPos = 500;
      yPos = 500;
      Client.addEntity(new Crate(0,0,0,Client.getId()));
    }

    /**
     * Main Constructor
     * @param xPos x position of the position
     * @param yPos y position of the position
    */
    public MainPlayer(double xPos, double yPos, long id){
      super(xPos, yPos, 0, id);
      hp = 1;
    }

    /**
     * Sets the value of up
     * @param up the up to set
     */
    public void setUp(boolean up) {
    	this.up = up;
    }

    /**
     * Sets the value of left
     * @param left the left to set
     */
    public void setLeft(boolean left) {
    	this.left = left;
    }

    /**
     * Sets the value of down
     * @param down the down to set
     */
    public void setDown(boolean down) {
    	this.down = down;
    }

    /**
      * Sets the value of right
     * @param right the right to set
     */
    public void setRight(boolean right) {
    	this.right = right;
    }

    public int getHp(){
      return hp;
    }

    /**
    * TODO synchronized it
    * Uses the equipped item
    * @param p the point relative to the location of the player that was attacked (mouse input)
    */
    public void attack(Point p){
      if(equipped == null){

      } else {
        equipped.attack(p, this);
      }
    }

    /**
    * Uses the secondary function of the equipped item
    * @param p the point relative to the location of the player that was attacked (mouse input)
    */
    public void attack2(Point p){
      if(equipped == null){

      } else {
        equipped.attack2(p, this);
      }
    }

    /**
    * reloads the equipped item
    */
    public void reload(){
      if(equipped == null){

      } else {
        equipped.reload();
      }
    }

    public void hit(int damage){
      int tmpHp = hp - damage;
      if (tmpHp <= 0){
        //die
        ((GameDrawing)Drawing.getState()).deadScreen();
        inventory.dropAll();
        equipped = null;
        spawn();
      } else if (tmpHp > 100){
        hp = 100;
      } else {
        hp = tmpHp;
      }
    }

    /**
    * Draws the player to the Window
    * @param g the graphics context
    */
    public void draw(Graphics g, long t){

      //get the currsor location for aiming the equipped item
      Point mousePos = Mouse.getPosition();
      transform.setToTranslation(xPos-50, yPos-50);
      rotation = Math.atan2(mousePos.getX(), mousePos.getY());
      transform.rotate(rotation, 50, 50);
      ((Graphics2D)(g)).drawImage(img, transform, null);

      //draws the equipped item
      drawEquipped(g);

      //moves the player
      double yVel = 0;
      double xVel = 0;
      if (up){yVel -= t * 0.5;}
      if (down){yVel += t * 0.5;}
      if (left){xVel -= t * 0.5;}
      if (right){xVel += t * 0.5;}
      if (xVel != 0 && yVel != 0){
        yVel /= SQRT_2;
        xVel /= SQRT_2;
      }

      xPos += xVel;
      yPos += yVel;

      ArrayList<Entity> entities = Client.getEntities();
      for (int iEntities = 0; iEntities < entities.size(); ++iEntities){
        Entity e = entities.get(iEntities);
        if (!(e instanceof Projectile)){
          Shape s = e.getHitBox();
          if (HitDetection.hit(s, getHitBox())){
            xPos -= xVel;
            yPos -= yVel;
            break;
          }
        }
      }
      if (xPos < 0){xPos = 0;}
      else if(xPos > 20000){xPos = 20000;}
      if (yPos < 0){yPos = 0;}
      else if(yPos > 20000){yPos = 20000;}
    }
}
