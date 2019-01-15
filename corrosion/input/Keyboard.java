/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * check to see what keys are being pressed
  */
package corrosion.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import corrosion.input.bind.*;

public class Keyboard implements KeyListener {
  private static Bindable binds[] = new Bindable[526];

  /**
  * Sets up all the keyboard binds
  * @param b the bindables
  */
  public static void setBinds(Bindable b[]){
    binds = b;
  }

  /**
  * empty method, but needed for function
  * @param e the key event
  */
  @Override
  public void keyTyped(KeyEvent e) {
  }

  /**
  * Runs a bind when the binded key is pressed
  * @param e the key event
  */
  @Override
  public void keyPressed(KeyEvent e) {
    //gets the key code
    int k = e.getKeyCode();
    //check if it is in the set range
    if (k < 526){
      //check if the key is bound
      if (binds[k] != null){
        //runs the bind
        binds[k].pressed(e);
      }
    }
  }

  /**
  * Runs a bind when the binded key is released
  * @param e the key event
  */
  @Override
  public void keyReleased(KeyEvent e) {
    //gets the key code
    int k = e.getKeyCode();
    //check if it is in the set range
    if (k < 526){
      //check if the key is bound
      if (binds[k] != null){
        //runs the bind
        binds[k].released(e);
      }
    }
  }
}
