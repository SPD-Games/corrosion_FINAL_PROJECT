/** Edward Pei
  * December 18 2018
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
  // create the new button
  public static ButtonG b;
  BufferedImage connectImage;

  /**
  * draw the connection Screen
  * @param g Graphics tool
  * @param t frame count
  */
  public void draw(Graphics g, long t) {
    ((Graphics2D)g).translate((Drawing.width())/2,(Drawing.height())/2 );
    b.draw(g);
    //System.out.println((b.getXBounds())[0] + " " + (b.getXBounds())[1] + ", " + (b.getYBounds())[0] + " " +  (b.getYBounds())[1]);
  }

  /** gets the button
   * @return the Button
   */
  public static ButtonG getB() {
  	return b;
  }

  public void init(){
    // add all the mouse binds
    MouseBindable mouseBinds[] = new MouseBindable[5];
    mouseBinds[1] = new LeftClickConnectMenu();//left click
    Mouse.setBinds(mouseBinds);
      try {
            //sets sprite image
            connectImage = ImageIO.read(new File("sprites/menuicons/ConnectBtn.png"));
          } catch(Exception e) {
            //exits on error with message
            System.out.println("Logo load error: " + e);
            System.exit(-1);
      }
       b = new ButtonG(0, 400 ,1,connectImage);
  }

}
