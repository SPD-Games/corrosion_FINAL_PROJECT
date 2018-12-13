/** Micheal Metzinger
  * December 11 2018
  * creates the JFrame where the game or main menu will be drawn
  */
package corrosion;

import javax.swing.JFrame;

import corrosion.Drawing;
import corrosion.input.*;

public class Window extends JFrame {

  private static Window window;

  /**
  * Gets the main Window
  * @return the main window
  */
  public static Window getWindow(){
    return window;
  }

  /**
   * Main Constructor
  */
  public Window() {
    //sets up the window
    init();
    window = this;
  }

  /**
  * Sets up the window
  */
  public void init() {
    add(new Drawing());//adds jFrame
    addKeyListener(new Keyboard());//adds Keyboard
    Mouse m = new Mouse();
    addMouseListener(m);//add Mouse
    addMouseWheelListener(m);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setUndecorated(true);
    setTitle("Corrosion");
    setVisible(true);
  }
}
