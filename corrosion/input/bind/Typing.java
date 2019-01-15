/** Micheal Metzinger, Edward Pei
  * December 20 2018
  * moves the player left
  */

  //22


package corrosion.input.bind;

import corrosion.input.bind.Bindable;
import corrosion.entity.player.MainPlayer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import corrosion.drawingstate.*;

public class Typing extends Bindable{

  /**
  * get the keys pressed
  * @param e the key event
  */
  public void pressed(KeyEvent e){
    // get the keycodes and character
    int keyCode = e.getKeyCode();
    char c = e.getKeyChar();

    // since the typing is only allowed for IP, only allow certain characters
    String allowedChar = "1234567890.";

    // if backspaced pressed, remove a letter
    if (keyCode == 8) {
      ConnectMenuDrawing.getIPInput().backSpace();
    } else if ((allowedChar.indexOf(c) != -1)&& ConnectMenuDrawing.getIPInput().getText().length() < 23) {
      // add the char if its below the character limit, and is a allowed character
      ConnectMenuDrawing.getIPInput().addChar(c);
    }

  }

  /**
  * method not used in this class
  */
  public void released(KeyEvent e){

  }

  /**
  * method not used in this class
  */
  public void typed(){}
}
