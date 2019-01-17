/** Micheal Metzinger
  * December 13 2018
  * Runs the game
  */

// GAME REQUIRES JDK V.11

package corrosion;

import corrosion.Window;
import javax.swing.JOptionPane;
public class Main {
  /**
  * Initializes the graphic settings
  */
  public static void init() {
    JOptionPane.showMessageDialog(null, "Corrosion - Made by Michael Metzinger, Eddie Pei, and Henry Lim");
    //System.setProperty("sun.java2d.opengl", "true");
  }

  /**
   * @param args command line arguments
  */
  public static void main(String[] args) {
    init();

    new Window();
  }
}
