//Henry Lim
//Dec. 19th, 2018
//Inventory Box Drawing Surface
package inventorybox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;



public class DrawingSurface extends JPanel{
    
    int[][] items = new int[6][6];
    
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
        
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}
