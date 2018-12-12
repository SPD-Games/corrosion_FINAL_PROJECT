/** Micheal Metzinger
  * December 11 2018
  * creates the JFrame where the game or main menu will be drawn
  */
package corrosion;

import javax.swing.JFrame;

import corrosion.Drawing;

public class Window extends JFrame {
  /**
   * Main Constructor
  */
  public Window() {
    //sets up the window
    init();
  }

  /**
  * Sets up the window
  */
  public void init() {
    add(new Drawing());//adds jFrame
    //addKeyListener(new Keyboard());//adds Keyboard
   //addMouseListener(new Mouse());//add Mouse
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setUndecorated(true);
    setTitle("Corrosion");
    setVisible(true);
  }
}
