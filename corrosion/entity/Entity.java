/** Micheal Metzinger
  * December 11 2018
  * Class that defines anything that can be drawn
  */
package corrosion.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.io.Serializable;


abstract public class Entity implements Serializable{
  protected double xPos,yPos,rotation;
  protected long id = -1;
  protected AffineTransform transform = new AffineTransform();
  /**
  * Main Constructor
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  */
  public Entity(double x, double y, double r, long id){
    this();
    xPos = x;
    yPos = y;
    rotation = r;
    this.id = id;
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
  * Gets the hitBox for the entity
  */
  public Shape getHitBox(){
    return null;
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
   * Gets the rotation
   * @return the rotation
  */
  public double getRotaion(){
    return rotation;
  }

  /**
   * Sets the rotation
   * @param r the rotation to set to
  */
  public void setRotation(double r){
    rotation = r;
  }

  /**
   TODO: add a lock to position
   * Gets the y value of position
   * @return the y value of the Position
  */
  public double getYPos(){
    return yPos;
  }

  /**
   * Gets the current player transformation
   * @return the current player transformation
  */
  public AffineTransform getTransform(){
    return transform;
  }

  public void setId(int id){
    this.id = id;
  }

  public long getId(){
    return id;
  }

  public boolean equals(Object o){
    if (o == null) return false;
    if (o == this) return true;
    if (!(o instanceof Entity)) return false;
    if (id == -1) return false;
    return ((Entity)o).id == id;
  }

  abstract public void draw(Graphics g, long t);
}
