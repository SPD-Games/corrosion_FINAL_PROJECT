/** Micheal Metzinger
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

public class MainMenuDrawing extends DrawingState{
  protected BufferedImage logoImg;
  protected AffineTransform transform = new AffineTransform();
  public JButton playBtn= new JButton("PLAY");
  public JButton settingsBtn= new JButton("SETTINGS");
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

  public void addListner() {
    if (addable) {
      // add action ActionListener
      playBtn.addActionListener(new Action1());
      //settingsBtn.addActionListener(new Action2());
      addable = false;
    }
  }

  /**
  * Draws all the swing objects on the main menu
  * @param g the graphics tool to draw with
  */
  public void drawSwing(Graphics g) {

    addListner();

    // draw the play button
    playBtn.setPreferredSize(new Dimension(100, 100));
    playBtn.setBounds(Drawing.getPanel().getWidth()/2 - 100, Drawing.getPanel().getWidth()/2, 200, 75);
    playBtn.setFont(new Font("Arial", Font.BOLD, 40));
    playBtn.setForeground(Color.BLACK);
    playBtn.setBackground(new Color(255,140,0));
    Drawing.getPanel().add(playBtn);

    // draw the settings button
    settingsBtn.setPreferredSize(new Dimension(100, 100));
    settingsBtn.setBounds(Drawing.getPanel().getWidth()/2 - 100, Drawing.getPanel().getWidth()/2-100, 200, 75);
    settingsBtn.setFont(new Font("Arial", Font.PLAIN, 27));
    settingsBtn.setForeground(Color.BLACK);
    settingsBtn.setBackground(new Color(255,140,0));
    Drawing.getPanel().add(settingsBtn);

  }

/**
* class which see if play button is pressed
*/
 class Action1 implements ActionListener {

  /**
  * checks to see if button is pressed
  * @param e the ActionEvent
  */
  public void actionPerformed(ActionEvent e) {
        if (playBtn.isEnabled()) {
            System.out.println("Button is pressed");
            // disable the button so is only resgistered once
            playBtn.disable();
        }
    }
  }


  /**
  * class which see if settings button is pressed
  */
  class Action2 implements ActionListener {

    /**
    * checks to see if button is pressed
    * @param e the ActionEvent
    */
    public void actionPerformed(ActionEvent e) {
      if (settingsBtn.isEnabled()) {
          System.out.println("Button 2 is pressed");
          // disable the button so is only resgistered once
          settingsBtn.disable();
      }
    }
  }


  public void init(){


      try{
        //sets sprite image
        logoImg = ImageIO.read(new File("sprites/logoV1.png"));
      }catch(Exception e){
        //exits on error with message
        System.out.println("Logo load error: " + e);
        System.exit(-1);
      }
    }
  }
