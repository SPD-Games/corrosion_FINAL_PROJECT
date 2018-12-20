/**
  * Michael Metzinger
  * December 12 2018
  *
  */

package corrosion.entity.player;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import corrosion.Drawing;
import corrosion.entity.item.equippable.*;
import corrosion.entity.player.Player;
import corrosion.input.*;

public class MainPlayer extends Player{
  private final double SQRT_2 = Math.sqrt(2.0);
    private static MainPlayer mainPlayer;

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
      equipped = new CrossBow(this);
      //equipped = null;

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

    /**
    * TODO synchronized it
    * Uses the equipped item
    * @param p the point relative to the location of the player that was attacked (mouse input)
    */
    public void attack(Point p){
      if(equipped == null){

      } else {
        equipped.attack(p);
      }
    }

    /**
    * TODO synchronized it
    * Uses the secondary function of the equipped item
    * @param p the point relative to the location of the player that was attacked (mouse input)
    */
    public void attack2(Point p){
      if(equipped == null){

      } else {
        equipped.attack2(p);
      }
    }

    /**
    * TODO synchronized it
    * reloads the equipped item
    */
    public void reload(){
      if(equipped == null){

      } else {
        equipped.reload();
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
      //TODO make it so its not faster diagonal
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
    }
}
