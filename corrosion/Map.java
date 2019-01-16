/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * draws the map the players will play on
  */
package corrosion;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.awt.image.AffineTransformOp;
import corrosion.network.*;
import corrosion.Drawing;
import corrosion.entity.*;
import corrosion.entity.player.*;
import corrosion.entity.item.*;
import corrosion.entity.projectile.*;
import corrosion.entity.item.equippable.*;
import corrosion.input.*;
import corrosion.input.bind.*;
import corrosion.entity.building.*;
import corrosion.entity.building.wall.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
public class Map{
  public BufferedImage[][] segments = new BufferedImage[100][100];
  public void init(){
    BufferedImage map = null;
    try{
      //loads map icon
      map = ImageIO.read(new File("sprites/map.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Map Sprite: " + e);
      System.exit(-1);
    }
    for (int x = 0; x < 10000; x += 100){
      for (int y = 0; y < 10000; y += 100){
        // get the sub images
        segments[x/100][y/100] = map.getSubimage(x, y, 100, 100);
      }
    }
  }

  /*
  * Constructor of the map
  */
  public Map(){
    init();
  }

  /**
  * Draws the player to the Window
  * @param g the graphics context
  * @param t time since last frame
  */
  public void draw(Graphics g, double scale){
    // get the user position
    int xPos = (int)MainPlayer.getMainPlayer().getXPos()/200;
    int yPos = (int)MainPlayer.getMainPlayer().getYPos()/200;
    // get window info
    int w = (int)(Drawing.width() / 400.0 / scale) + 1;
    int h = (int)(Drawing.height() / 400.0 / scale) + 1;
    // draw the map in segments so the user lags less
    for (int x = -w; x <= w; x++){
      if (xPos+x < 0){continue;}
      if (xPos+x >= 100){break;}
      for (int y = -h; y <= h; y++){
        if (yPos+y < 0){continue;}
        if (yPos+y >= 100){break;}
        ((Graphics2D)g).scale(2,2);
        ((Graphics2D)g).drawImage(segments[xPos+x][yPos+y],null,(xPos + x)*100,(yPos + y)*100);
        ((Graphics2D)g).scale(0.5,0.5);
      }
    }
  }
}
