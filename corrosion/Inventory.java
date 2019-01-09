//Henry Lim
//Jan. 8th, 2019
//Inventory Box
package corrosion;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import corrosion.entity.item.*;
import corrosion.entity.item.equippable.*;
import corrosion.entity.player.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public class Inventory{

  private Item[][] items = new Item[6][6];

  /**
  *Constructor
  */
  public Inventory(){
    for(int x = 0; x < 6; x ++){
      for(int y = 0; y < 6; y ++){
        items[x][y] = new Orange();
      }
    }
  }

  public boolean addItem(Item i){
    if (i.isStackable()){
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

    for(int x = 0; x < 6; x ++){
      for(int y = 0; y < 6; y ++){
        if (items[x][y] != null){
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
    return need <= 0;
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
      return true;
    } else {
      return false;
    }
  }

  public void draw(Graphics g, long time){
    items[0][0] = MainPlayer.getMainPlayer().getEquipped();
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
          AffineTransform t = new AffineTransform();
          t.translate(x*50 + 19, y*50 + 20);
          t.scale(45.0/i.getWidth(), 45.0/i.getHeight());
          g2d.drawImage(i, t, null);
          g2d.drawString((items[x][y].getInfo()) + "",x*50 + 20, y*50 + 30);
        }
      }
  }
}
