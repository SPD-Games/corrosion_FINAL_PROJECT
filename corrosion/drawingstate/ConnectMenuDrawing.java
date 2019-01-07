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
import corrosion.drawingstate.menuobjects.*;
import corrosion.input.*;
import corrosion.input.bind.*;

public class ConnectMenuDrawing extends DrawingState{
  // create the new button
  public static ButtonG connectBtn;
  public static TextBoxG ipInput;
  protected BufferedImage backImg;
  protected BufferedImage logoImg;
  BufferedImage connectImage;

  /**
  * draw the connection Screen
  * @param g Graphics tool
  * @param t frame count
  */
  public void draw(Graphics g, long t) {
    ((Graphics2D)(g)).drawImage(backImg,null,null);
    ((Graphics2D)g).translate((Drawing.width())/2,(Drawing.height())/2 );
    g.setFont(new Font("Ariel", Font.PLAIN, 50));
    g.setColor(Color.black);
    ((Graphics2D)g).drawString("*Port not needed",-220,110);

    connectBtn.draw(g);
    ipInput.draw(g);

    ((Graphics2D)(g)).scale(0.5,0.5);
    g.translate(-logoImg.getWidth()/2, -logoImg.getHeight()/2 -530);
    ((Graphics2D)(g)).drawImage(logoImg,null,null);
  }

  /** gets the button
   * @return the Button
   */
  public static ButtonG getB() {
  	return connectBtn;
  }

  /** gets the text box
   * @return the text box
   */
  public static TextBoxG getIPInput() {
    return ipInput;
  }

  public static String getIP() {
    return ipInput.getText();
  }

  public void init(){
    // add all the mouse binds
    MouseBindable mouseBinds[] = new MouseBindable[5];
    mouseBinds[1] = new LeftClickConnectMenu();//left click
    Mouse.setBinds(mouseBinds);

    // create the keyboard binds
    Bindable binds[] = new Bindable[526];
    Typing type = new Typing();
    for(int i = 0; i < 526 ;i++) {
      binds[i] = type;
    }
    Keyboard.setBinds(binds);

      try {
            //sets sprite image
            connectImage = ImageIO.read(new File("sprites/menuicons/ConnectBtn.png"));
            backImg = ImageIO.read(new File("sprites/menuicons/MenuBackgrounds.jpg"));
            logoImg = ImageIO.read(new File("sprites/menuicons/LogoV1.png"));

          } catch(Exception e) {
            //exits on error with message
            System.out.println("Logo load error: " + e);
            System.exit(-1);
      }
      // create the a new textbox and button
       connectBtn = new ButtonG(0, 300 ,1,connectImage);
       ipInput = new TextBoxG(0,0,900,100,new Color(255, 98, 0),"ENTER IP HERE");
  }

}
