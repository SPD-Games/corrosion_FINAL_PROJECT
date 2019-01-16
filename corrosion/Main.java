/** Micheal Metzinger
  * December 13 2018
  * Runs the game
  * TODO:

      corrosion.drawingstate.TextBoxG says in the top

      create a jar file

      server.java needs comments
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
