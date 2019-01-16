/** Micheal Metzinger
  * December 13 2018
  * Runs the game
  * TODO:
    writing to data File?
    recursion?
    Encapsulate the final program to include an intro screen, application, and credits screen using the JOptionPane function or Multiple Windows.

  */

package corrosion;

import corrosion.Window;

public class Main {
  /**
  * Initializes the graphic settings
  */
  public static void init() {
    System.setProperty("sun.java2d.opengl", "true");
  }

  /**
   * @param args command line arguments
  */
  public static void main(String[] args) {
    init();

    new Window();
  }
}
