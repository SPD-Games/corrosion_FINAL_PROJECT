/** Edward Pei
  * December 14 2018
  * draws text boxs
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

  /*
  * add a text character to the text box
  * @param c the character
  */
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
  * @return the text in the textbox
  */
  public String getText() {
    return text;
  }

  /**
  * Draws the textbox and text
  * @param g the graphics contect
  */
  public void draw(Graphics g) {
    // draw the rectangle
    g.setColor(c);
    ((Graphics2D) g).fillRect(xBounds[0], yBounds[0], width,height);

    // set font and color then draw the characters
    g.setColor(Color.black);
    g.setFont(new Font("Ariel", Font.PLAIN, 50));
    g.drawString(text,xPos-width/2,yPos + height/4);

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
  * returns the information about the textbox
  * @return the information
  */
  public String toString() {
    return ("xPos: " + xPos + "," +  "yPos: " + yPos);
  }

  /**
  * check if two text boxes are equal
  * @param t the text box
  * @return if they are equal
  */
  public boolean equals(TextBoxG t){
    if ((xBounds == t.getXBounds() && yBounds == t.getYBounds()) && text == t.getText()) return true;
    return false;
  }

}
