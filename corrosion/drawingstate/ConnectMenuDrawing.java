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

public class ConnectMenuDrawing extends DrawingState{
  TextArea connectInput;

  /**
  * draw the connection Screen
  * @param g Graphics tool
  * @param t frame count
  */
  public void draw(Graphics g, long t) {
  }

  public void init(){
    drawSwing();
    Drawing.getPanel().add(connectInput);
  }

  public void drawSwing() {
    connectInput = new TextArea("ashdias");
    connectInput.setPreferredSize(new Dimension(100,100));
    connectInput.setBounds(Drawing.getPanel().getWidth()/2 - 100, Drawing.getPanel().getWidth()/2, 200, 75);

  }

}
