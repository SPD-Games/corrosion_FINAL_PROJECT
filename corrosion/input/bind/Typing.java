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
  *
  */
  public void pressed(KeyEvent e){
    int keyCode = e.getKeyCode();
    char c = e.getKeyChar();

    String allowedChar = "1234567890.";

    if (keyCode == 8) {
      ConnectMenuDrawing.getIPInput().backSpace();
    } else if ((allowedChar.indexOf(c) != -1)&& ConnectMenuDrawing.getIPInput().getText().length() < 23) {
      ConnectMenuDrawing.getIPInput().addChar(c);
    }

  }

  /**
  *
  */
  public void released(KeyEvent e){

  }

  public void typed(){}
}
