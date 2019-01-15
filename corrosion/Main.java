/** Micheal Metzinger
  * December 13 2018
  * Runs the game
  * TODO:
      REMARKS after corrosion.entity.item

      crate and barrel drops

      fix buggy shotgun when player shoots in middle

      corrosion.drawingstate.TextBoxG says in the top

      recursion ??? NOT NEEDED
      create a jar file
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
