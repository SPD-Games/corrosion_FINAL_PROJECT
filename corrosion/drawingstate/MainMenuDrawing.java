/**  Edward Pei, Micheal Metzinger
  * January 3 2019
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
import corrosion.drawingstate.menuobjects.ButtonG;
import corrosion.input.*;
import corrosion.input.bind.*;

public class MainMenuDrawing extends DrawingState{
  protected BufferedImage logoImg;
  protected BufferedImage PlayBtnImage;
  protected BufferedImage backImg;
  protected static ButtonG playBtn;
  protected static ButtonG settingBtn;
  protected AffineTransform transform = new AffineTransform();


  /**
   * draw the images and buttons
   * @param g graphics tool
   * @@param t frame count
   */
  public void draw(Graphics g, long t) {
    // format all the image options to fit the screen properly
    transform.setToScale(1, 1);
    ((Graphics2D)(g)).clearRect(0,0 , Drawing.getPanel().getWidth(), Drawing.getPanel().getHeight());
    ((Graphics2D)(g)).drawImage(backImg,transform,null);
    transform.translate(Drawing.getPanel().getWidth()/2 - (logoImg.getWidth()/2), Drawing.getPanel().getHeight()/2 - (logoImg.getHeight()/1.5));
    ((Graphics2D)(g)).drawImage(logoImg,transform,null);

    // draw all buttons
    ((Graphics2D)g).translate((Drawing.width())/2,(Drawing.height())/2);
    playBtn.draw(g);
}

  /** gets the button
  * @return the Button
  */
  public static ButtonG getPlayBtn() {
    return playBtn;
  }

  public void init(){

    // add all the mouse binds
    MouseBindable mouseBinds[] = new MouseBindable[5];
    mouseBinds[1] = new LeftClickMainMenu();//left click
    Mouse.setBinds(mouseBinds);

      try {
        //sets sprite image
        logoImg = ImageIO.read(new File("sprites/menuicons/logoV1.png"));
        PlayBtnImage = ImageIO.read(new File("sprites/menuicons/PlayBtn.png"));
        backImg = ImageIO.read(new File("sprites/menuicons/MenuBackgrounds.jpg"));

      } catch(Exception e) {
        //exits on error with message
        System.out.println("Image load error: " + e);
        System.exit(-1);
      }
      // create a new button
      playBtn = new ButtonG(0,270,0.5,PlayBtnImage);
    }
  }
