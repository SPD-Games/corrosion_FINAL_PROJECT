//Henry Lim
//Jan. 8th, 2019
//Inventory Box
package corrosion;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class InventoryBox extends JFrame{

    public InventoryBox(){
        initUI();
    }
    
    private void initUI() {        
        setTitle("Inventory");
        add(new DrawingSurface());
        setSize(340, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                InventoryBox windowFrame = new InventoryBox();
                windowFrame.setVisible(true);
            }
        });
    }
    
}

