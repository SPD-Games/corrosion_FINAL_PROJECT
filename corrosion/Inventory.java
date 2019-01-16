//Henry Lim
//Jan. 10th, 2019
//Inventory Box
package corrosion;

//Imports
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import corrosion.entity.item.*;
import corrosion.entity.item.equippable.*;
import corrosion.entity.player.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.*;
import corrosion.input.Mouse;
import corrosion.network.Client;
import corrosion.network.protocol.Protocol;


//Inventory class
public class Inventory{
  //2d Array for items in inventory
  private Item[][] items = new Item[6][6];
  //Array for items in hotbar
  private Equippable[] hotBar = new Equippable[6];
  private int equipped = 0;
  /**
  *Constructor
  */
  public Inventory(){
    /*
    hotBar[0] = new CrossBow();
    hotBar[1] = new Pistol();
    hotBar[3] = new Rifle();
    hotBar[4] = new Sniper();
    items[0][0] = new BuildingPlan();
    items[0][1] = new UpgradePlan();
    items[0][3] = new Orange(10);
    items[0][4] = new Wood(10000);
    items[0][5] = new Stone(10000);
    items[1][0] = new Metal(10000);
    items[1][1] = new Bullet(1000);
    items[1][2] = new Arrow(1000);
    items[1][3] = new Tool();
    items[1][4] = new Shotgun();
    items[1][5] = new Smg();
    items[2][0] = new Apple(10);
    items[2][1] = new Bandage(10);
    items[2][2] = new Medkit(10);
    */

    hotBar[0] = new Tool();
    hotBar[1] = new BuildingPlan();
    hotBar[2] = new UpgradePlan();
  }

  /**
  * sets the equipped item
  * @param i the index of the item in the hotbar
  */
  public void setEquipped(int i){
    equipped = i;
  }

  /**
   * Method to return item in hotbar
   * @param i index of desired item to be returned
   * @return item
   */
  public Equippable getHotBar(int i){
    return hotBar[i];
  }

  /**
  * Get the amount of a type of item in the invetory
  * @param i the item to check for
  */
  public int getAmount(Item i){
    int have = 0;
    //iterate through the invetory
    for(int x = 0; x < 6; x ++){
      for(int y = 0; y < 6; y ++){
        //check if an item exsist in that slot
        if (items[x][y] != null){
          //check if the item is the same class as inputed
          if (items[x][y].getClass() == i.getClass()){
            //count the amount of item it has
            have += items[x][y].getStackSize();
          }
        }
      }
    }
    //iterate through hotbar
    for(int x = 0; x < 6; x ++){
      //check if an item exsist in that slot
      if (hotBar[x] != null){
        //check if the item is the same class as inputed
        if (hotBar[x].getClass() == i.getClass()){
          //count the amount of item it has
          have += hotBar[x].getStackSize();
        }
      }
    }
    return have;
  }

  /**
  * drops the equipped item on the ground
  */
  public void dropEquipped(){
    if (hotBar[equipped] == null){return;}
    hotBar[equipped].setPos(MainPlayer.getMainPlayer().getXPos(),MainPlayer.getMainPlayer().getYPos(),0);
    hotBar[equipped].sendItem();
    hotBar[equipped] = null;
    MainPlayer.getMainPlayer().setEquipped(equipped);
  }

  /**
   * Gets the square the mouse is over
   * @return the square the mouse is over
   */
  public int[] getMousePos(){
    Point p = Mouse.getOnScreen();
    int[] out = {(int)((p.x-35)/87.5),(int)((p.y-35)/87.5)};
    if((p.x-35)%87.5 >= 78.75 || p.x-35 <= 0){return null;}
    if((p.y-35)%87.5 >= 78.75 || p.y-35 <= 0){return null;}
    if(out[0] > 6 || out[1] > 6){return null;}
    return out;
  }

  /**
  * drops all the items in the invetory
  */
  public void dropAll(){
    //iterates through all the items and drops them
    for (int x = 0; x < 6; ++x){
      for (int y = 0; y < 6; ++y){
        //chceks if item exsist
        if(items[x][y] == null){continue;}
        //drops the item
        items[x][y].setPos(MainPlayer.getMainPlayer().getXPos(),MainPlayer.getMainPlayer().getYPos(),0);
        items[x][y].sendItem();
        items[x][y] = null;
      }
    }
    //iterates through all the items and drops them
    for (int x = 0; x < 6; ++x){
      //chceks if item exsist
      if(hotBar[x] == null){continue;}
      //drops the item
      hotBar[x].setPos(MainPlayer.getMainPlayer().getXPos(),MainPlayer.getMainPlayer().getYPos(),0);
      hotBar[x].sendItem();
      hotBar[x] = null;
    }
  }

  /**
  * drop the currently selected item
  */
  public void drop(){
    //gets the box the mouse is over
    int[] on = getMousePos();
    //if not over any cancel
    if (on == null){return;}
    //check if item exist
    if(items[on[0]][on[1]] == null){return;}
    //drop the item
    items[on[0]][on[1]].setPos(MainPlayer.getMainPlayer().getXPos(),MainPlayer.getMainPlayer().getYPos(),0);
    items[on[0]][on[1]].sendItem();
    items[on[0]][on[1]] = null;
  }

  /**
  * swaps the selected item to the hot bar
  * @param the index of the hotbar to swap to
  */
  public void swapToHotBar(int swap){
    //gets the box the mouse is over
    int[] on = getMousePos();
    //if not over any cancel
    if (on == null){return;}
    //check if item exist
    if(!(items[on[0]][on[1]] instanceof Equippable) && items[on[0]][on[1]] != null){return;}
    //swap items
    Equippable tmp = (Equippable)items[on[0]][on[1]];
    items[on[0]][on[1]] = hotBar[swap];
    hotBar[swap] = tmp;
    if(equipped == swap){
      MainPlayer.getMainPlayer().setEquipped(swap);
    }
  }

  /**
  * Adds an item to the invetory
  * @param i the item to Add
  * @return if the action was successful
  */
  public boolean addItem(Item i){
    //checks if the item is isStackable
    if (i.isStackable()){
      //iterates through the hotbar
      for (int x = 0; x < 6; x++){
        if (hotBar[x] != null){
          //check if the item is the same class as the item
          if (hotBar[x].getClass() == i.getClass()){
            //add together
            hotBar[x].addStack(i);
            return true;
          }
        }
      }
      //iterate through the invetory
      for(int x = 0; x < 6; x ++){
        for(int y = 0; y < 6; y ++){
          if (items[x][y] != null){
            //check if the item is the same as inputed
            if (items[x][y].getClass() == i.getClass()){
              //add to the stack
              items[x][y].addStack(i);
              return true;
            }
          }
        }
      }
    }
    //if the item is equippable add to the hot bar if there is an open slot
    if (i instanceof Equippable){
      for (int x = 0; x < 6; x++){
        if (hotBar[x] == null){
          hotBar[x] = (Equippable)i;
          return true;
        }
      }
    }
    //add to the invetory if there is an open slot
    for(int x = 0; x < 6; x ++){
      for(int y = 0; y < 6; y ++){
        if (items[x][y] == null){
          items[x][y] = i;
          return true;
        }
      }
    }
    return false;
  }

  /**
  * Checks if the invetory has the amount of items
  * @param i the item to Check
  * @return if the invetory has the same or more of that item
  */
  public boolean checkItem(Item i){
    int need = i.getStackSize();
    //iterate through the invetory
    for(int x = 0; x < 6; x ++){
      for(int y = 0; y < 6; y ++){
        if (items[x][y] != null){
          //check if the item is the same as inputed
          if (items[x][y].getClass() == i.getClass()){
            //change the count
            need -= items[x][y].getStackSize();
          }
        }
      }
    }
    //iterate through the hotbar
    for(int x = 0; x < 6; x ++){
      if (hotBar[x] != null){
        //check if the item is the same as inputed
        if (hotBar[x].getClass() == i.getClass()){
          //change the count
          need -= hotBar[x].getStackSize();
        }
      }
    }
    return need <= 0;
  }

  /**
  * Get the amount of an item that the invetory has
  * @param i the item to check For
  * @return the amount of the item in the invetory
  */
  public int haveItem(Item i){
    int have = 0;
    //iterates through the invetory
    for(int x = 0; x < 6; x ++){
      for(int y = 0; y < 6; y ++){
        if (items[x][y] != null){
          //check if the item is the same
          if (items[x][y].getClass() == i.getClass()){
            //change count
            have += items[x][y].getStackSize();
          }
        }
      }
    }
    //iterate through hotbar
    for(int x = 0; x < 6; x ++){
      if (hotBar[x] != null){
        //check if the item is the same
        if (hotBar[x].getClass() == i.getClass()){
          //change count
          have += hotBar[x].getStackSize();
        }
      }
    }
    return have;
  }

  /**
  * removes item
  * @param i the item to Remove
  * @return if the action was successful
  */
  public boolean removeItem(Item i){
    //check if thr item is in the invetory
    if (checkItem(i)){
      //iterate through the invetory
      for(int x = 0; x < 6; x ++){
        for(int y = 0; y < 6; y ++){
          if (items[x][y] != null){
            //check if the item is the same
            if (items[x][y].getClass() == i.getClass()){
              //remove the item from the stack
              items[x][y].removeStack(i);
              if (items[x][y].getStackSize() == 0){items[x][y] = null;}
              //check if enough was removed
              if (i.getStackSize() == 0){return true;}
            }
          }
        }
      }
      //iterate through hot bar
      for (int x = 0; x < 6; x++){
        if (hotBar[x] != null){
          //check if the item is the same
          if (hotBar[x].getClass() == i.getClass()){
            //remove the item
            hotBar[x].removeStack(i);
            if (hotBar[x].getStackSize() == 0){hotBar[x] = null;}
            //check if enough was removed
            if (i.getStackSize() == 0){return true;}
          }
        }
      }
      return true;
    } else {
      return false;
    }
  }

  /**
  * Draws the hot Bar
  * @param g the graphics contect to Use
  * @param time the time since the last frame
  */
  public void drawHotBar(Graphics g, long time){
    Graphics2D g2d = (Graphics2D) g;
    g2d.setTransform(new AffineTransform());
    g2d.translate(Drawing.width()/2-258, Drawing.height() - 90);
    g2d.scale(1.75,1.75);

    //draws each box
    for (int x = 0; x < 6; x ++) {
      g2d.setColor(new Color(20,20,20,50));
      g2d.fillRect(x*50, 0, 45, 45);
      //draws the item in the box
      if (hotBar[x] != null){
        AffineTransform t = new AffineTransform();
        BufferedImage i = hotBar[x].getIcon();
        t.translate(x*50, 0);
        t.scale(45.0/i.getWidth(), 45.0/i.getHeight());
        g2d.drawImage(i,t,null);
        g2d.setColor(Color.BLACK);
        g2d.drawString((hotBar[x].getInfo()) + "", x*50+1, 10);
      }
    }
  }

  /**
  * draws the Inventory
  * @param g the graphic context to Use
  * @param time the amount of time since the last frame
  */
  public void draw(Graphics g, long time){
      //draw the background
      Graphics2D g2d = (Graphics2D) g;
      g2d.setTransform(new AffineTransform());
      g2d.scale(1.75,1.75);
      g2d.setColor(Color.LIGHT_GRAY);
      g2d.fillRect(0, 0, 340, 360);
      g2d.setColor(Color.GRAY);

      //draw every square
      for (int x = 20; x <= 270; x += 50) {
          for (int y = 20; y <= 270; y += 50) {
              g2d.fillRect(x, y, 45, 45);

          }
      }
      g2d.setColor(Color.BLACK);

      //iterate through invetory
      for(int x = 0; x < 6; x ++){
        for(int y = 0; y < 6; y ++){
          if (items[x][y] == null){
            continue;
          }
          //draw item in the correct location
          BufferedImage i = items[x][y].getIcon();
          if (i==null){return;}
          AffineTransform t = new AffineTransform();
          t.translate(x*50 + 19, y*50 + 20);
          t.scale(45.0/i.getWidth(), 45.0/i.getHeight());
          g2d.drawImage(i, t, null);
          g2d.drawString((items[x][y].getInfo()) + "",x*50 + 20, y*50 + 30);
        }
      }
  }
}
