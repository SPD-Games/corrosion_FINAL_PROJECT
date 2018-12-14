/** Micheal Metzinger
  * December 13 2018
  * a class which determines what is drawn and contains a fps counter
  */
package corrosion;

import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.Graphics;

import corrosion.drawingstate.*;

public class Drawing extends JPanel{
  private static Drawing panel = null;
  private static DrawingState state;//TODO: change to main menu or load screen?

  private long lastFrame = 0;
  private int frameCount = 0;
  private int fps = 0;


  //fps counter
  private ActionListener frameCountListener = new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent arg0) {
      fps = frameCount;
      frameCount = 0;
    }
  };
  private Timer fpsTimer = new Timer(1000, frameCountListener);

  //When the timer is active repaints every call
  private ActionListener newFrameListener = new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent arg0) {
      repaint();
    }
  };
  private Timer frameTimer = new Timer(0, newFrameListener);//no fps limit

  public Timer getTimer() {
    return frameTimer;
  }

  /**
  * Gets the current fps
  * @return the currnet fps
  */
  public static int getFps(){
    return panel.fps;
  }

  /**
  * Gets the current width of the window
  * @return the currnet width of the window
  */
  public static int width(){
    return panel.getWidth();
  }

  /**
  * Gets the current height of the window
  * @return the currnet height of the window
  */
  public static int height(){
    return panel.getHeight();
  }

  /**
  * Gets the current Panel
  * @return the current Panel
  */
  public static Drawing getPanel(){
    return panel;
  }

  /**
  * Main Constructor
  */
  public Drawing(){
    panel = this;
    state = new GameDrawing();

    //start drawing and fps timers
    fpsTimer.start();
    frameTimer.start();
  }

  /**
  * Draws the current state
  * @param g current graphics context
  */
  private void draw(Graphics g){
    ++frameCount;
    long newFrame = System.currentTimeMillis();
    state.draw(g, newFrame - lastFrame);
    lastFrame = newFrame;
  }

  /**
  * Redraws the jframe
  * @param g the graphics context
  */
  @Override
  public void paintComponent(Graphics g) {
      super.paintComponent(g);
      draw(g);
  }
}
