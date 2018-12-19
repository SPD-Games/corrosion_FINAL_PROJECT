/** Edward Pei
  * December 18 2018
  * draws buttons
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

  int xPos,yPos, width, height;
  int[] yBounds = new int[2];
  int[] xBounds = new int[2];
  AffineTransform transform = new AffineTransform();
  BufferedImage image;
/** stores the information of a button, button constructor
* @param xPos the x Position of the button
* @param yPos the y Position of the button
* @param width the width of the Button
* @param height the height of the Button
*/
  public ButtonG(int xPos,int yPos, double scale,BufferedImage i) {
    this.xPos = xPos;
    this.yPos = yPos;
    width = (int)(i.getWidth()*scale);
    height = (int)(i.getHeight()*scale);

    image = i;
    yBounds[0] = yPos - height/2;
    yBounds[1] = yPos + height/2;
    xBounds[0] = xPos - width/2;
    xBounds[1] = xPos + width/2;

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


  public String toString() {
    return ("");
  }


}
