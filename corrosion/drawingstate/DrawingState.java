/** Micheal Metzinger
  * December 11 2018
  * Draws the state that it is set to
  */
package corrosion.drawingstate;

import java.awt.Graphics;

import corrosion.Drawing;

abstract public class DrawingState{
  /*
  * Main Constructor
  */
  public DrawingState(){
    init();
  }

  /**
  * Initiates the drawing state
  */
  abstract public void init();

  /**
  * Draws a new frame of the drawing state
  * @param g the graphics context
  * @param t the time elapsed from the last frame
  */
  abstract public void draw(Graphics g, long t);
}
