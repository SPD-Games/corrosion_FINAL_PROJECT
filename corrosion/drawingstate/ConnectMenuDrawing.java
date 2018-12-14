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
    // stop the draw timer stoping the draw and allow the buttons to be drawn properly
    (Drawing.getPanel()).getTimer().stop();

    System.out.print(1);

    g.setColor(new Color(255,69,0));
    g.fillRect(0, 0, Drawing.width(), Drawing.height());
  }

  public void init(){
    connectInput = new TextArea("Enter IP here");
    connectInput.setPreferredSize(new Dimension(800,75));
    connectInput.setFont(new Font("Serif", Font.PLAIN, 40));
    connectInput.setRows(1);
    connectInput.setBounds(Drawing.getPanel().getWidth()/2 - 100, Drawing.getPanel().getWidth()/2, 200, 75);
    (Drawing.getPanel()).add(connectInput);
  }

}
