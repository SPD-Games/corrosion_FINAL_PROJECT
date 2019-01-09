/**
  * Michael Metzinger
  * December 12 2018
  *
  */

package corrosion.entity.player;

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

public class MainPlayer extends Player{
  private final double SQRT_2 = Math.sqrt(2.0);
    private static MainPlayer mainPlayer;
    private int hp = 100;

    //direction the player is moving (keyboard input)
    private boolean up, down, left, right;

    /**
    * Gets the main player Object
    * @return the main player Object
    */
    public static MainPlayer getMainPlayer(){
      return mainPlayer;
    }

    /**
    * Creates the new MainPlayer
    * @param xPos the x position of the player
    * @param yPos the y position of the
    */
    public static void spawn(double xPos, double yPos, long id){
      mainPlayer = new MainPlayer(xPos, yPos, id);
    }

    /**
     * Main Constructor
     * @param xPos x position of the position
     * @param yPos y position of the position
    */
    public MainPlayer(double xPos, double yPos, long id){
      super(xPos, yPos, 0, id);
      //equipped = new CrossBow();
<<<<<<< HEAD
      //equipped = new BuildingPlan();
=======
      equipped = new BuildingPlan();
>>>>>>> 9a29c6f295ed07eff55d19ebeb1e4a9934125ffc
      //equipped = new UpgradePlan();

      //equipped = new Apple();
      //equipped = new Pistol();
      equipped = new Rifle();
      //equipped = null;
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
        hp = 0;
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
            return;
          }
        }
      }
    }
}
