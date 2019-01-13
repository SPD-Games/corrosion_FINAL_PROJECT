/** Edward Pei
  * December 14 2018
  * draws text boxs
  */
package corrosion.drawingstate.menuobjects;

//TODO: when text is added, remove default text, when removed display defualy text, just add new attrubites to object "default text" and "added text" thanks eddie.

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

  private int xPos,yPos, width, height;
  private int[] yBounds = new int[2];
  private int[] xBounds = new int[2];
  private String text;
  private Color c;

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
    this.width = width;
    this.height = height;
    this.text = text;
    this.c = c;
  }

  public void addChar(char c) {
    text += c;
  }

  /** removes a Character from the text box
   */
  public void backSpace() {
    // only remove a character if there is one that can be removed
    if(text.length() > 0) {
      text = text.substring(0, text.length() - 1);
    }
  }

  /* *
  * Method used to get the text from the text box
  *
  */
  public String getText() {
    return text;
  }

  public void draw(Graphics g) {
    // draw the rectangle
    g.setColor(c);
    ((Graphics2D) g).fillRect(xBounds[0], yBounds[0], width,height);

    // set font and color then draw the characters
    g.setColor(Color.black);
    g.setFont(new Font("Ariel", Font.PLAIN, 50));
    g.drawString(text,xPos-width/2,yPos + height/4);

  }



}
