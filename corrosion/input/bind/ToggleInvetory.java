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
  public void pressed(KeyEvent e){
    DrawingState d = Drawing.getState();
    if (d instanceof GameDrawing){
      ((GameDrawing)d).showInvetory();
    }
  }
  public void released(KeyEvent e){

  }
  public void typed(){

  }
}
