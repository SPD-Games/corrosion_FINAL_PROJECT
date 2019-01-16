/** Micheal Metzinger
  * December 13 2018
  * Runs the game
  * TODO:
      REMARKS after corrosion.entity.item

      corrosion.drawingstate.TextBoxG says in the top

      create a jar file

      bind/use.java needs comments ... idk what the heck it does
      server.java needs comments
  */

package corrosion;

import corrosion.Window;

public class Main {
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
