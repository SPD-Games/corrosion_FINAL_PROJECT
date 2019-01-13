/** Edward Pei
  * January 7 2019
  * class for crates that drop loot
  */
package corrosion.entity;
import corrosion.entity.item.*;
import corrosion.Sprite;
import corrosion.entity.player.MainPlayer;
import java.awt.*;
import java.awt.geom.*;
import corrosion.entity.*;
import corrosion.network.*;
import corrosion.network.protocol.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.*;

public class Crate extends Entity{

  double scaleSize = 0.5;
  private int hp = 150;
  public static BufferedImage icon;
  public static void init(){
    try{
      //loads Crate icon
      icon = ImageIO.read(new File("sprites/crate.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Crate Sprite: " + e);
      System.exit(-1);
    }
  }

  /**
  * Main Constructor of the Crate
  * @param x the x position of the Entity
  * @param y the y position of the Entity
  * @param r the rotation applied to the Entity
  */
  public Crate (double x, double y, double r, long id) {
    super(x,y,r,id);
    transform.setToTranslation(x-50,y-50);
    transform.scale(scaleSize,scaleSize);
  }
  public Crate () {
    super();
  }


  public void draw(Graphics g, long t) {
    ((Graphics2D)g).drawImage(icon,transform,null);
  }

  public void drop(){

  }

  public Shape getHitBox(){
    return new Rectangle2D.Double(xPos-50, yPos-50,100,100);
  }

  /**
  * if the Crate is hit, decrease its size until it is small then return resources
  */
  public void hit(int damage) {
    if(hp > 0) {
      // reduce the size of the Crate
      hp -= damage;
      Protocol.send(8, this, Client.getConnection());
    } else {
      drop();

      Client.removeEntity(this);
      Protocol.send(12, this, Client.getConnection());
    }
  }


}
