/** Edward Pei
  * December 14 2018
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

/** stores the information of a button, button constructor
* @param xPos the x Position of the button
* @param yPos the y Position of the button
* @param width the width of the Button
* @param height the height of the Button
*/
  public ButtonG(int xPos,int yPos, int width,int height) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.width = width;
    this.height = height;
    System.out.println(xPos);
    System.out.println(yPos);
  }

  public void draw(Graphics g) {
    ((Graphics2D)g).fillRect(xPos,yPos,width ,height);
  }


  public String toString() {
    return ("");
  }


}
