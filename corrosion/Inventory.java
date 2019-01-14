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
    hotBar[0] = new CrossBow();
    hotBar[1] = new Pistol();
    hotBar[2] = new Orange();
    hotBar[3] = new Rifle();
    hotBar[4] = new Sniper();
    items[0][0] = new BuildingPlan();
    items[0][1] = new UpgradePlan();
    items[0][3] = new Orange();
    items[0][4] = new Wood(10000);
    items[0][5] = new Stone(10000);
    items[1][0] = new Metal(10000);
    items[1][1] = new Bullet(10000);
    items[1][2] = new Arrow(10000);
    items[1][3] = new Tool();

  }
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

  public int getAmount(Item i){
    int have = 0;
    for(int x = 0; x < 6; x ++){
      for(int y = 0; y < 6; y ++){
        if (items[x][y] != null){
          if (items[x][y].getClass() == i.getClass()){
            have += items[x][y].getStackSize();
          }
        }
      }
    }
    return have;
  }

  public void dropEquipped(){
    hotBar[equipped].setPos(MainPlayer.getMainPlayer().getXPos(),MainPlayer.getMainPlayer().getYPos(),0);
    hotBar[equipped].sendItem();
    hotBar[equipped] = null;
    MainPlayer.getMainPlayer().setEquipped(equipped);
  }

  /**
   *
   * @return
   */
  public int[] getMousePos(){
    Point p = Mouse.getOnScreen();
    int[] out = {(int)((p.x-35)/87.5),(int)((p.y-35)/87.5)};
    if((p.x-35)%87.5 >= 78.75 || p.x-35 <= 0){return null;}
    if((p.y-35)%87.5 >= 78.75 || p.y-35 <= 0){return null;}
    if(out[0] > 6 || out[1] > 6){return null;}
    return out;
  }

  public void dropAll(){
    for (int x = 0; x < 6; ++x){
      for (int y = 0; y < 6; ++y){
        if(items[x][y] == null){continue;}
        items[x][y].setPos(MainPlayer.getMainPlayer().getXPos(),MainPlayer.getMainPlayer().getYPos(),0);
        items[x][y].sendItem();
        items[x][y] = null;
      }
    }

    for (int x = 0; x < 6; ++x){
      if(hotBar[x] == null){continue;}
      hotBar[x].setPos(MainPlayer.getMainPlayer().getXPos(),MainPlayer.getMainPlayer().getYPos(),0);
      hotBar[x].sendItem();
      hotBar[x] = null;
    }
  }

  public void drop(){
    int[] on = getMousePos();
    if (on == null){return;}
    if(items[on[0]][on[1]] == null){return;}
    items[on[0]][on[1]].setPos(MainPlayer.getMainPlayer().getXPos(),MainPlayer.getMainPlayer().getYPos(),0);
    items[on[0]][on[1]].sendItem();
    items[on[0]][on[1]] = null;
  }

  public void swapToHotBar(int swap){
    int[] on = getMousePos();
    if (on == null){return;}
    if(!(items[on[0]][on[1]] instanceof Equippable) && items[on[0]][on[1]] != null){return;}
    Equippable tmp = (Equippable)items[on[0]][on[1]];
    items[on[0]][on[1]] = hotBar[swap];
    hotBar[swap] = tmp;
    if(equipped == swap){
      MainPlayer.getMainPlayer().setEquipped(swap);
    }
  }

  public boolean addItem(Item i){
    if (i.isStackable()){
      for (int x = 0; x < 6; x++){
        if (hotBar[x] != null){
          if (hotBar[x].getClass() == i.getClass()){
            hotBar[x].addStack(i);
            return true;
          }
        }
      }

      for(int x = 0; x < 6; x ++){
        for(int y = 0; y < 6; y ++){
          if (items[x][y] != null){
            if (items[x][y].getClass() == i.getClass()){
              items[x][y].addStack(i);
              return true;
            }
          }
        }
      }
    }
    if (i instanceof Equippable){
      for (int x = 0; x < 6; x++){
        if (hotBar[x] == null){
          hotBar[x] = (Equippable)i;
          return true;
        }
      }
    }
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

  public boolean checkItem(Item i){
    int need = i.getStackSize();
    for(int x = 0; x < 6; x ++){
      for(int y = 0; y < 6; y ++){
        if (items[x][y] != null){
          if (items[x][y].getClass() == i.getClass()){
            need -= items[x][y].getStackSize();
          }
        }
      }
    }
    for(int x = 0; x < 6; x ++){
      if (hotBar[x] != null){
        if (hotBar[x].getClass() == i.getClass()){
          need -= hotBar[x].getStackSize();
        }
      }
    }
    return need <= 0;
  }

  public int haveItem(Item i){
    int have = 0;
    for(int x = 0; x < 6; x ++){
      for(int y = 0; y < 6; y ++){
        if (items[x][y] != null){
          if (items[x][y].getClass() == i.getClass()){
            have += items[x][y].getStackSize();
          }
        }
      }
    }
    return have;
  }

  public boolean removeItem(Item i){
    if (checkItem(i)){
      for(int x = 0; x < 6; x ++){
        for(int y = 0; y < 6; y ++){
          if (items[x][y] != null){
            if (items[x][y].getClass() == i.getClass()){
              items[x][y].removeStack(i);
              if (items[x][y].getStackSize() == 0){items[x][y] = null;}
              if (i.getStackSize() == 0){return true;}
            }
          }
        }
      }
      for (int x = 0; x < 6; x++){
        if (hotBar[x] != null){
          if (hotBar[x].getClass() == i.getClass()){
            hotBar[x].removeStack(i);
            if (hotBar[x].getStackSize() == 0){hotBar[x] = null;}
            if (i.getStackSize() == 0){return true;}
          }
        }
      }
      return true;
    } else {
      return false;
    }
  }

  public void drawHotBar(Graphics g, long time){
    Graphics2D g2d = (Graphics2D) g;
    g2d.setTransform(new AffineTransform());
    g2d.translate(Drawing.width()/2-258, Drawing.height() - 90);
    g2d.scale(1.75,1.75);

    for (int x = 0; x < 6; x ++) {
      g2d.setColor(new Color(20,20,20,50));
      g2d.fillRect(x*50, 0, 45, 45);
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

  public void draw(Graphics g, long time){
      Graphics2D g2d = (Graphics2D) g;
      g2d.setTransform(new AffineTransform());
      g2d.scale(1.75,1.75);
      g2d.setColor(Color.LIGHT_GRAY);
      g2d.fillRect(0, 0, 340, 360);
      g2d.setColor(Color.GRAY);

      for (int x = 20; x <= 270; x += 50) {
          for (int y = 20; y <= 270; y += 50) {
              g2d.fillRect(x, y, 45, 45);

          }
      }
      g2d.setColor(Color.BLACK);

      for(int x = 0; x < 6; x ++){
        for(int y = 0; y < 6; y ++){
          if (items[x][y] == null){
            continue;
          }
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
