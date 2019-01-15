/** Edward Pei
  * December 18 2018
  * A button class
  */
package corrosion.drawingstate.menuobjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.geom.AffineTransform;
import java.awt.Graphics;

import corrosion.Drawing;

public class ButtonG {

  private int xPos,yPos, width, height;
  private int[] yBounds = new int[2];
  private int[] xBounds = new int[2];
  private AffineTransform transform = new AffineTransform();
  private BufferedImage image;

  /** stores the information of a button, button constructor
  * @param xPos the x Position of the button
  * @param yPos the y Position of the button
  * @param scale how to scale the Button
  * @param i the image
  */
  public ButtonG(int xPos,int yPos, double scale,BufferedImage i) {
    this.xPos = xPos;
    this.yPos = yPos;
    // get the width and height of the scaled button
    width = (int)(i.getWidth()*scale);
    height = (int)(i.getHeight()*scale);
    image = i;

    // get the edges of the button
    yBounds[0] = yPos - height/2;
    yBounds[1] = yPos + height/2;
    xBounds[0] = xPos - width/2;
    xBounds[1] = xPos + width/2;

    // transform and scale
    transform.translate(xPos -width/2,yPos - height/2);
    transform.scale(scale,scale);
  }

  /**
  * returns the outer x vales of the button
  * @return array of values
  */
  public int[] getXBounds() {
    return xBounds;
  }

  /**
  * returns the outer y vales of the button
  * @return array of values
  */
  public int[] getYBounds() {
    return yBounds;
  }

  /**
  * Draws a button based on its characteristics, x/y pos represent the center of the button
  *@param g the graphics tool
  */
  public void draw(Graphics g) {
    ((Graphics2D) g).drawImage(image, transform, null);
  }

  /**
  * returns the information of the button
  * @return the information
  */
  public String toString() {
    return ("xPos: " + xPos + "," +"yPos: " + yPos);
  }

  /**
  * check if two buttons are equal
  * @@param b the button
  * @return if they are equal
  */
  public boolean equals(ButtonG b){
    if ((xBounds == b.getXBounds() && yBounds == b.getYBounds())) return true;
    return false;
  }


}
