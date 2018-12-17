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
import java.awt.Graphics2D;

import corrosion.Drawing;
import corrosion.drawingstate.menuobjects.ButtonG;
import corrosion.input.*;
import corrosion.input.bind.*;

public class ConnectMenuDrawing extends DrawingState{
  public ButtonG b = new ButtonG((Drawing.width())/2 - 50, (Drawing.height())/2 - 50,100 ,100 );

  /**
  * draw the connection Screen
  * @param g Graphics tool
  * @param t frame count
  */
  public void draw(Graphics g, long t) {
    ((Graphics2D)g).translate((Drawing.width())/2,(Drawing.height())/2 );
    b.draw(g);
  }

  public void init(){
    MouseBindable mouseBinds[] = new MouseBindable[5];
    mouseBinds[1] = new LeftClick();//left click
    Mouse.setBinds(mouseBinds);
  }

}
