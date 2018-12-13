/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * abstract class which helps with key binds
  */
package corrosion.input.bind;

abstract public class Bindable{
  abstract public void pressed();
  abstract public void released();
  abstract public void typed();
}
