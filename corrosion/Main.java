/** Micheal Metzinger
  * December 13 2018
  * Runs the game
  * TODO:
      hit reg
      entity networking
      all the classes for items

      no place on players
      fix placing
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
