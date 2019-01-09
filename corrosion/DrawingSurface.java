//Henry Lim
//Jan. 9th, 2019
//Inventory Box Drawing Surface
package corrosion;
//Imports
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import corrosion.entity.item.Item;
import corrosion.entity.item.equippable.*;

public class DrawingSurface extends JPanel{

private Item[][] items = new Item[6][6];

  /**
  *Constructor
  */
public DrawingSurface(){
  init();
}

/**
*
*/
    private void init(){
      Orange.init();
      for(int x = 0; x < 6; x ++){
        for(int y = 0; y < 6; y ++){
          items[x][y] = new Orange();
        }
      }
    }



    private void doDrawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, 340, 360);
        g2d.setColor(Color.GRAY);

        for (int x = 20; x <= 270; x += 50) {
            for (int y = 20; y <= 270; y += 50) {
                g2d.fillRect(x, y, 45, 45);

            }
        }

        for(int x = 0; x < 6; x ++){
          for(int y = 0; y < 6; y ++){
            AffineTransform t = new AffineTransform();
            BufferedImage i = items[x][y].getIcon();
            t.translate(x*50 + 19, y*50 + 20);
            t.scale(45.0/i.getWidth(), 45.0/i.getHeight());
            g2d.drawImage(i, t, null);

          }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

    }
}
