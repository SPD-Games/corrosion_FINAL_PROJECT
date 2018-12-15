/** Edward Pei
  * December 14 2018
  * Draws the connection menu screen
  */
package corrosion.drawingstate;

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
import corrosion.drawingstate.menuobjects.*;

public class ConnectMenuDrawing extends DrawingState{

  /**
  * draw the connection Screen
  * @param g Graphics tool
  * @param t frame count
  */
  public void draw(Graphics g, long t) {
    ButtonG b = new ButtonG();
    b.draw(g);
  }

  public void init(){

  }

}
