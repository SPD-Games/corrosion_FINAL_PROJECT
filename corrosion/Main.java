/** Micheal Metzinger
  * December 13 2018
  * Runs the game
  * TODO:
      REMARKS after corrosion.entity.building.Building
      add spawning of rocks trees etc
      add tool
      reloading
      player random spawn

      fix buggy shotgun when player shots in middle
      corrosion.drawingstate.TextBoxG says in the top
      corrosion.drawingstate.GameDrawing init() load all sprites


      Data File loading rocks and all that stuff
      recursion ???
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
