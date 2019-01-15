/** Micheal Metzinger, Edward Pei
  * December 13 2018
  * Checks to see what mouse buttons are being pressed
  */
package corrosion.input;

import java.awt.event.*;

import java.awt.Point;
import java.awt.MouseInfo;
import javax.swing.SwingUtilities;

import corrosion.Drawing;
import corrosion.input.bind.*;
import corrosion.drawingstate.GameDrawing;
import corrosion.entity.player.*;

public class Mouse implements MouseListener, MouseWheelListener{
  private static MouseBindable binds[] = new MouseBindable[5];

  /**
  * Sets up all the mouse binds
  * @param b a mouse bindable
  */
  public static void setBinds(MouseBindable b[]){
    binds = b;
  }

  /**
  * Gets the location of the mouse relative to the JPanel
  * @return the point of the mouse
  */
  public static Point getPosition(){
    Point p = MouseInfo.getPointerInfo().getLocation();
    SwingUtilities.convertPointFromScreen(p, Drawing.getPanel());
    return relativeToPlayer(p);
  }

  /**
  * Gets the location of the mouse on screen
  * @return the point of the mouse
  */
  public static Point getOnScreen(){
    Point p = MouseInfo.getPointerInfo().getLocation();
    SwingUtilities.convertPointFromScreen(p, Drawing.getPanel());
    return p;
  }

  /**
  * Converts a point on the screen to a point on the screen relative to the player
  * @param p the point
  */
  public static Point relativeToPlayer(Point p){
    p.setLocation(p.getX() - Drawing.getPanel().getWidth()/2, Drawing.getPanel().getHeight()/2 - p.getY());
    return p;
  }

  /**
  * Converts a point on the screen to a point on the map
  * @return a point
  */
  public static Point getPointOnMap(){
    Point p = getPosition();
    p.x /= GameDrawing.getZoom();
    p.y /= -GameDrawing.getZoom();
    p.x += (int)MainPlayer.getMainPlayer().getXPos();
    p.y += (int)MainPlayer.getMainPlayer().getYPos();
    return p;
  }

  /**
  * Converts a point on the screen to a point on the map
  * @param p the point
  * @return a point
  */
  public static Point getPointOnMap(Point p){
    p.x /= GameDrawing.getZoom();
    p.y /= -GameDrawing.getZoom();
    p.x += (int)MainPlayer.getMainPlayer().getXPos();
    p.y += (int)MainPlayer.getMainPlayer().getYPos();
    return p;
  }

  /**
  * does action on mouse click
  * @param e the event that happends to the mouse
  */
  @Override
  public void mouseClicked(MouseEvent e) {
  }

  /**
  * Mouse Clicked listener
  * @param e the mouse event
  */
  @Override
  public void mousePressed(MouseEvent e) {
    //gets the mouse button pressed
    int k = e.getButton();
    //checks if it is in range of the buttons
    if (k < 5){
      //checks if the button is bound
      if (binds[k] != null){
        //runs the bind
        binds[k].pressed(e.getPoint());
      }
    }
  }

  /**
  * does an action on mouse relased
  * @param e the event that happends to the mouse
  */
  @Override
  public void mouseReleased(MouseEvent e) {
    //gets the mouse button pressed
    int k = e.getButton();
    //checks if it is in range of the buttons
    if (k < 5){
      //checks if the button is bound
      if (binds[k] != null){
        //runs the bind
        binds[k].released(e.getPoint());
      }
    }
  }

  /**
  * needed for program to fuction, but empty
  * @param e the event that happends to the mouse
  */
  @Override
  public void mouseEntered(MouseEvent e) {

  }

  /**
  * needed for program to fuction, but empty
  * @param e the event that happends to the mouse
  */
  @Override
  public void mouseExited(MouseEvent e) {
  }

  /**
  * allow the user to scroll to zoom in game
  * @param e the event that happends to the mouse
  */
  public void mouseWheelMoved(MouseWheelEvent e) {
    double zoom = GameDrawing.zoom -= 0.1 * e.getWheelRotation();
    zoom = Math.max(0.3, zoom);
    zoom = Math.min(1, zoom);
    GameDrawing.zoom = zoom;
  }
}
