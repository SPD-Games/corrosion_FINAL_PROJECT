/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * abstract class which helps with key binds
  */
package corrosion.input.bind;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import corrosion.Drawing;
import corrosion.drawingstate.GameDrawing;
import corrosion.drawingstate.DrawingState;

public class ToggleInvetory extends Bindable{

  /**
  * toggle on and off the inventory if right key is pressed
  * @param e the key event
  */
  public void pressed(KeyEvent e){
    DrawingState d = Drawing.getState();
    if (d instanceof GameDrawing){
      ((GameDrawing)d).showInvetory();
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
  public void typed(){

  }
}
