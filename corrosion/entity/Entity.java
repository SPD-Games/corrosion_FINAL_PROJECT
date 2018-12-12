/** Micheal Metzinger
  * December 11 2018
  * Class that defines anything that can be drawn
  */
package corrosion.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;


abstract public class Entity{
  protected double xPos,yPos,rotation;
  protected AffineTransform transform = new AffineTransform();
  /**
  * Main Constructor
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  */
  public Entity(double x, double y, double r){
    this();
    xPos = x;
    yPos = y;
    rotation = r;
  }

  /**
  * Blank Constructor
  */
  public Entity(){
    xPos = 0;
    yPos = 0;
    rotation = 0;
  }

  /**
   * Sets the positon
   * @param x the x value of the Position
   * @param y the y value of the Position
  */
  public void setPos(double x, double y){
    this.xPos = x;
    this.yPos = y;
  }

  /**
   * Sets the positon
   * @param x the x value of the Position
   * @param y the y value of the Position
   * @param y the angle in radians
   *
  */
  public void setPos(double x, double y, double rotation){
    this.xPos = x;
    this.yPos = y;
    this.rotation = rotation;
  }

  /**
   * Gets the x value of position
   * @return the x value of the Position
  */
  public double getXPos(){
    return xPos;
  }

  /**
   TODO: add a lock to position
   * Gets the y value of position
   * @return the y value of the Position
  */
  public double getYPos(){
    return yPos;
  }


  abstract public void draw(Graphics g, long t);
}
