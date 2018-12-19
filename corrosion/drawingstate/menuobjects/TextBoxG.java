/** Edward Pei
  * December 14 2018
  * draws text boxs
  */
package corrosion.drawingstate.menuobject;

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

public class TextBoxG {

  int xPos,yPos, width, height;
  int[] yBounds = new int[2];
  int[] xBounds = new int[2];

  /** stores the information of a button, button constructor
  * @param xPos the x Position of the button
  * @param yPos the y Position of the button
  * @param width the width of the Button
  * @param height the height of the Button
  * @param c the color of the text area
  * @param text the text in the text area
  */
  public TextBoxG(int xPos,int yPos, int width, int height,Color c,String text) {
    this.xPos = xPos;
    this.yPos = yPos;
    yBounds[0] = yPos - height/2;
    yBounds[1] = yPos + height/2;
    xBounds[0] = xPos - width/2;
    xBounds[1] = xPos + width/2;
  }

  public void draw(Graphics g) {
    g.setColor(c);
    ((Graphics2D) g).fillRect(xBounds[0], yBounds[0], width,height);

    g.setColor(Color.black);
    g.drawString(xPos,yPos, text);

  }

  

}
