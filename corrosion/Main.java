/** Micheal Metzinger
  * December 11 2018
  * Runs the game
  */

package corrosion;

import corrosion.Window;

public class Main{
  public static void init(){
    System.setProperty("sun.java2d.opengl", "true");
  }

  /**
   * @param args command line arguments
  */
  public static void main(String[] args){
    init();

    new Window();
  }
}
