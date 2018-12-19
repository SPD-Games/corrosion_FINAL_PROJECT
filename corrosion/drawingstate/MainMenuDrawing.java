/**  Edward Pei, Micheal Metzinger
  * December 11 2018
  * Draws the main game
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
import corrosion.drawingstate.*;

public class MainMenuDrawing extends DrawingState{
  protected BufferedImage logoImg;
  protected AffineTransform transform = new AffineTransform();
  public JButton playBtn = new JButton("PLAY");
  public JButton settingsBtn = new JButton("SETTINGS");
  public boolean addable = true;

  /**
   * draw the images and buttons
   * @param g graphics tool
   * @@param t frame count
   */
  public void draw(Graphics g, long t) {

    // format all the image options to fit the screen properly
    //  transform.setToScale(0.4,0.4);
    transform.setToTranslation(Drawing.getPanel().getWidth()/2 - (logoImg.getWidth()/2), Drawing.getPanel().getHeight()/2 - (logoImg.getHeight()/2));
    ((Graphics2D)(g)).clearRect(0,0 , Drawing.getPanel().getWidth(), Drawing.getPanel().getHeight());

    ((Graphics2D)(g)).drawImage(logoImg,transform,null);

    // draw all swing objects
    drawSwing(g);
  }

  /**
  * Draws all the swing objects on the main menu
  * @param g the graphics tool to draw with
  */
  public void drawSwing(Graphics g) {


  }


  public void init(){

      try {
        //sets sprite image
        logoImg = ImageIO.read(new File("sprites/menuicons/logoV1.png"));
      } catch(Exception e) {
        //exits on error with message
        System.out.println("Logo load error: " + e);
        System.exit(-1);
      }
    }
  }
