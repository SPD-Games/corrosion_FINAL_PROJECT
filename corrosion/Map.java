/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * draws the map the players will play on
  */
package corrosion;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
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
      //loads Barrel icon
      map = ImageIO.read(new File("sprites/map.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Map Sprite: " + e);
      System.exit(-1);
    }
    for (int x = 0; x < 10000; x += 100){
      for (int y = 0; y < 10000; y += 100){
        segments[x/100][y/100] = map.getSubimage(x, y, 100, 100);
      }
    }
  }

  public Map(){
    init();
  }

  public void draw(Graphics g, double scale){
    int xPos = (int)MainPlayer.getMainPlayer().getXPos()/100;
    int yPos = (int)MainPlayer.getMainPlayer().getYPos()/100;
    int w = (int)(Drawing.width() / 100 * scale);
    int h = (int)(Drawing.height() / 100 * scale);
    for (int x = -w; x < w; x++){
      if (xPos+x < 0){continue;}
      if (xPos+x >= 100){break;}
      for (int y = -h; y < h; y++){
        if (yPos+y < 0){continue;}
        if (yPos+y >= 100){break;}
        ((Graphics2D)g).drawImage(segments[xPos+x][yPos+y],null,(xPos + x)*100,(yPos + y)*100);
      }
    }
  }
}
